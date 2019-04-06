package giavu.hoangvm.hh.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.model.LoginBody
import giavu.hoangvm.hh.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/15
 */
class LoginViewModel(private val userApi: UserApi) : ViewModel() {

    private lateinit var navigator: LoginNavigator
    private val compositeDisposable = CompositeDisposable()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val viewState = LoginViewState(
        _userName = username,
        _password = password
    )

    fun initialize(navigator: LoginNavigator) {
        this.navigator = navigator
    }

    fun login() {
        val loginBody = LoginBody(
            email = username.value.toString(),
            password = password.value.toString()
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
                    navigator.toShowError(error)
                }
            )
            .addTo(compositeDisposable = compositeDisposable)
    }

    fun register() {
        navigator.toRegister()
    }
}