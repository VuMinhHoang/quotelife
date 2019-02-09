package giavu.hoangvm.hh.activity.splash

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.activity.main.MainActivity
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.dialog.AlertDialogFragment
import giavu.hoangvm.hh.dialog.BaseDialogFragment
import giavu.hoangvm.hh.exception.ResponseError
import giavu.hoangvm.hh.helper.UserSharePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import timber.log.Timber

class SplashActivity : AppCompatActivity(), BaseDialogFragment.OnDialogResult {

    private val userApi: UserApi by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initialize()
    }

    override fun onResume() {
        Timber.d("onResume")
        super.onResume()
    }

    private fun initialize() {
        checkLocalData()
    }

    private fun checkLocalData() {
        val userSession = UserSharePreference.fromContext(this@SplashActivity)
                .getUserSession()

        val email = UserSharePreference.fromContext(this@SplashActivity)
                .getUserEmail()

        if (userSession.isEmpty()) {
            loadActivity(isLogined = false)
            return
        }
        userApi.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { response ->
                            if (response.account_details.email == email) {
                                loadActivity(true)
                            } else {
                                loadActivity(false)
                            }
                        },
                        onError = {
                            onError(it)
                        }
                )
                .addTo(compositeDisposable = compositeDisposable)
    }

    private fun loadActivity(isLogined: Boolean) {
        if (isLogined) {
            startActivity(MainActivity.createIntent(this@SplashActivity))
            this@SplashActivity.finish()
        } else {
            startActivity(LoginActivity.createIntent(this@SplashActivity))
            this@SplashActivity.finish()
        }

    }

    private fun onError(throwable: Throwable) {
        if (throwable is ResponseError) {
            AlertDialogFragment.Builder()
                .setTitle(throwable.errorCode)
                .setMessage(throwable.messageError)
                .setPositiveButtonText("Retry")
                .show(supportFragmentManager)
        }
    }

    override fun onDialogResult(requestCode: Int, whichButton: Int, data: Intent?) {
        when (whichButton) {
            Dialog.BUTTON_POSITIVE -> {
                checkLocalData()
            }
            else -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
