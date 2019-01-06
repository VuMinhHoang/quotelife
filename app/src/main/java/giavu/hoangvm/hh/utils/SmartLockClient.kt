package giavu.hoangvm.hh.utils

import android.app.Activity
import android.content.Intent
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.auth.api.credentials.CredentialRequestResult
import com.google.android.gms.auth.api.credentials.IdentityProviders
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvingResultCallbacks
import com.google.android.gms.common.api.Status
import giavu.hoangvm.hh.dialog.DialogFactory
import giavu.hoangvm.hh.dialog.ProgressDialogFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class SmartLockClient(activity: FragmentActivity) {

    private val googleApiClient: GoogleApiClient

    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    init {
        googleApiClient = GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, null)
                .addApi(Auth.CREDENTIALS_API)
                .build()
    }

    fun subscribe() {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
    }

    fun unsubscribe() {
        if (compositeDisposable == null) {
            return
        }
        compositeDisposable!!.clear()
        compositeDisposable = null
    }

    fun saveCredential(activity: Activity, email: String, password: String, requestCode: Int, listener: OnSaveSmartLockListener) {
        compositeDisposable!!.add(connectedSingle()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { saveCredentialObservable(activity, email, password, requestCode) }
                .subscribe({ status ->
                    if (status.isSuccess) {
                        listener.onSuccess()
                    } else {
                        listener.onFailure()
                    }
                }) { error -> listener.onFailure() }
        )
    }

    fun deleteCredential(activity: Activity, email: String, password: String, requestCode: Int, listener: OnDeleteSmartLockListener) {
        compositeDisposable!!.add(connectedSingle()
                .subscribeOn(Schedulers.newThread())
                .flatMap { deleteCredentialObservable(activity, email, password, requestCode) }
                .subscribe({ status ->
                    if (status.isSuccess) {
                        listener.onSuccess()
                    } else {
                        listener.onFailure()
                    }
                }) { error -> listener.onFailure() }
        )
    }

    fun requestCredential(fragmentActivity: FragmentActivity, onRequestCredentialListener: OnRequestCredentialListener) {
        ProgressDialogFragment.Builder().show(fragmentActivity.supportFragmentManager)
        requestCredentialSingle(onRequestCredentialListener)
                .onErrorReturn { throwable -> CredentialResult.empty() }
                .subscribe { credentialResult ->
                    DialogFactory().dismiss(fragmentActivity.supportFragmentManager, ProgressDialogFragment::class.java)
                    if (credentialResult.credential != null) {
                        EventBus.getDefault().post(credentialResult)
                    }
                }
    }

    fun onRequestActivityResult(resultCode: Int, data: Intent) {
        EventBus.getDefault().post(CredentialResult.fromActivityResult(data))
    }

    /**
     * 接続が完了するまで、onNextを送らないObservable
     */
    private fun connectedSingle(): Single<Any> {
        return Single.create { e ->
            if (googleApiClient.isConnected) {
                e.onSuccess(Any())
                return@create
            }
            googleApiClient.blockingConnect(5, TimeUnit.SECONDS)
            if (googleApiClient.isConnected) {
                e.onSuccess(Any())
            } else {
                e.onError(OnConnectedFailedException())
            }
        }
    }

    private fun saveCredentialObservable(activity: Activity, userName: String, password: String, requestCode: Int): Single<Status> {
        val credential = Credential.Builder(userName)
                .setPassword(password)
                .build()
        return Single.create { e ->
            Auth.CredentialsApi.save(googleApiClient, credential).setResultCallback(
                    object : ResolvingResultCallbacks<Status>(activity, requestCode) {
                        override fun onSuccess(@NonNull status: Status) {
                            e.onSuccess(status)
                        }

                        override fun onUnresolvableFailure(@NonNull status: Status) {
                            e.onError(OnSaveFailedException(status))
                        }
                    }
            )
        }
    }

    private fun deleteCredentialObservable(activity: Activity, emailAddress: String, password: String, requestCode: Int): Single<Status> {
        val credential = Credential.Builder(emailAddress).setPassword(password).build()
        return Single.create { e ->
            Auth.CredentialsApi.delete(googleApiClient, credential).setResultCallback(object : ResolvingResultCallbacks<Status>(activity, requestCode) {
                override fun onSuccess(@NonNull status: Status) {
                    e.onSuccess(status)
                }

                override fun onUnresolvableFailure(@NonNull status: Status) {
                    e.onError(OnDeleteFailedException(status))
                }
            })
        }
    }

    private fun requestCredentialSingle(onRequestCredentialListener: OnRequestCredentialListener): Single<CredentialResult> {
        return connectedSingle()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {requestCredentialResultSingle() }
                .flatMap({ it ->
                    val status = it.getStatus()
                    if (status.isSuccess()) {
                        val result = CredentialResult.fromCredential(it.getCredential())
                        Single.just(result)
                    }
                    if (status.getStatusCode() == CommonStatusCodes.RESOLUTION_REQUIRED) {
                        onRequestCredentialListener.shouldResoluteAccountSelect(status)
                    }
                    Single.just(CredentialResult.empty())
                })
    }

    private fun requestCredentialResultSingle(): Single<CredentialRequestResult> {
        val request = CredentialRequest.Builder()
                .setPasswordLoginSupported(true)
                .setAccountTypes(IdentityProviders.GOOGLE)
                .build()

        return Single.create {
            e -> Auth.CredentialsApi.request(googleApiClient, request)
                .setResultCallback(e::onSuccess)
        }
    }

    class OnConnectedFailedException : Exception()

    class OnSaveFailedException constructor(val status: Status) : Exception()

    class OnDeleteFailedException constructor(val status: Status) : Exception()

    interface OnRequestCredentialListener {

        fun shouldResoluteAccountSelect(status: Status)
    }

    interface OnSaveSmartLockListener {
        fun onSuccess()

        fun onFailure()
    }

    interface OnDeleteSmartLockListener {
        fun onSuccess()
        fun onFailure()
    }

}
