package giavu.hoangvm.hh.activity.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.extension.combineLatest
import giavu.hoangvm.hh.model.RegBody
import giavu.hoangvm.hh.model.RegUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
class RegisterAccountViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var navigator: RegisterAccountNavigator
    private val userApi: UserApi by application.inject()
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }
    private val _userName = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _registerButtonEnabled = MutableLiveData<Boolean>()

    val registerButtonEnabled: LiveData<Boolean>
        get() = _registerButtonEnabled

    fun initialize(navigator: RegisterAccountNavigator, owner: LifecycleOwner) {
        this.navigator = navigator
        _registerButtonEnabled.value = false
        _password.value = ""
        checkValidInput(owner)
    }

    fun onUserNameInput(text: CharSequence) {
        Log.d("Test", "blabla")
        _userName.postValue(text.toString())
    }

    fun onEmailInput(text: CharSequence) {
        _email.postValue(text.toString())
    }

    fun onPasswordInput(text: CharSequence) {
        _password.postValue(text.toString())
        Log.d("Test", _password.value)
    }

    fun gotoLogin() {
        navigator.toLogin()
    }

    fun register() {
        val body = RegBody(
                login = _userName.value,
                email = _email.value,
                password = _password.value
        )
        val user = RegUser(
                user = body
        )
        userApi.register(user)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { navigator.showProgress() }
                .doFinally { navigator.hideProgress() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = Timber::w,
                        onSuccess = { navigator.register(it) })
                .addTo(compositeDisposable)
    }

    private fun checkValidInput(owner: LifecycleOwner) {
        combineLatest(
                source1 = _userName.toPublisher(owner),
                source2 = _email.toPublisher(owner),
                source3 = _password.toPublisher(owner))
                .map { triple ->
                    val userName = triple.first
                    val email = triple.second
                    val password = triple.third
                    (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
                }
                .subscribeBy(
                        onError = Timber::w,
                        onNext = { _registerButtonEnabled.postValue(it) }
                )
                .addTo(compositeDisposable)
    }

}