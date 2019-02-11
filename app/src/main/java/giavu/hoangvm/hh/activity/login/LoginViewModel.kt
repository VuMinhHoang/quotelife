package giavu.hoangvm.hh.activity.login

import android.app.Application
import androidx.lifecycle.*
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.exception.ResponseError
import giavu.hoangvm.hh.model.LoginBody
import giavu.hoangvm.hh.model.User
import giavu.hoangvm.hh.utils.CredentialResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/15
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var navigator: LoginNavigator
    private val compositeDisposable = CompositeDisposable()

    private val userApi: UserApi by application.inject()
    private val _username = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _loginEnabled = MutableLiveData<Boolean>()

    val loginEnabled: LiveData<Boolean>
        get() = _loginEnabled

    fun initialize(navigator: LoginNavigator, owner: LifecycleOwner) {
        this.navigator = navigator
    }

    fun onUsernameTextChanged(text: CharSequence) {
        _username.postValue(text.toString())
    }

    fun onPasswordTextChanged(text: CharSequence) {
        _password.postValue(text.toString())
    }

    fun login() {
        val loginBody = LoginBody(
                email = _username.value.toString(),
                password = _password.value.toString()
        )
        val body = User(
                user = loginBody
        )
        userApi.login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { navigator.showProgress() }
                .doFinally { navigator.hideProgress() }
                .subscribeBy(
                        onSuccess = { response ->
                            if (response != null) {
                                navigator.toLogin(response)
                            } else {
                            }

                        },
                        onError = { error ->
                            if (error is ResponseError) {
                                navigator.toShowError(error)
                            }
                        }
                )
                .addTo(compositeDisposable = compositeDisposable)
    }

    fun register() {
        navigator.toRegister()
    }

    fun subscribeCredentialResult(credentialResult: CredentialResult) {
        _username.value = credentialResult.id
        _password.value = credentialResult.password
    }

}