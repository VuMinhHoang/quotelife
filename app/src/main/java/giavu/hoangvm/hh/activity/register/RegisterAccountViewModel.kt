package giavu.hoangvm.hh.activity.register

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.helper.ResourceProvider
import giavu.hoangvm.hh.model.RegBody
import giavu.hoangvm.hh.model.RegUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
class RegisterAccountViewModel(private val resourceProvider: ResourceProvider, private val userApi: UserApi) : ViewModel(){

    private lateinit var navigator: RegisterAccountNavigator

    private val compositeDisposable by lazy { CompositeDisposable() }
    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val viewState = RegisterAccountViewState(
        _userName = userName,
        _email = email,
        _password = password,
        _resourceProvider = resourceProvider
    )

    fun initialize(navigator: RegisterAccountNavigator) {
        this.navigator = navigator
    }

    fun gotoLogin() {
        navigator.toLogin()
    }

    fun register() {
        val body = RegBody(
            login = userName.value,
            email = email.value,
            password = password.value
        )
        val user = RegUser(
            user = body
        )
        userApi.register(user)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { navigator.showProgress() }
            .doFinally { navigator.hideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { error ->
                    navigator.toError(error)
                },
                onSuccess = { response ->
                    navigator.register(response)
                }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
