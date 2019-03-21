package giavu.hoangvm.hh.activity.register

import android.app.Application
import androidx.lifecycle.*
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.extension.combineTripleLatest
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.model.RegBody
import giavu.hoangvm.hh.model.RegUser
import giavu.hoangvm.hh.model.Response
import giavu.hoangvm.hh.validation.EmailAddressValidator
import giavu.hoangvm.hh.validation.PasswordValidator
import giavu.hoangvm.hh.validation.UserNameValidator
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

    private val passwordValidator = PasswordValidator()
    private val emailValidator = EmailAddressValidator()
    private val userNameValidator = UserNameValidator()

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val _registerButtonEnabled = MutableLiveData<Boolean>()

    val registerButtonEnabled: LiveData<Boolean>
        get() = _registerButtonEnabled



    fun initialize(navigator: RegisterAccountNavigator, owner: LifecycleOwner) {
        this.navigator = navigator
        _registerButtonEnabled.value = true
    }



    fun gotoLogin() {
        navigator.toLogin()
    }

    fun register() {
        navigator.register(LoginResponse("fdfad","",""))
        /*val body = RegBody(
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
                .subscribeBy(
                        onError = { error ->
                            navigator.toError(error)
                        },
                        onSuccess = { response ->
                            navigator.register(response)
                        }
                )
                .addTo(compositeDisposable)*/
    }


}
