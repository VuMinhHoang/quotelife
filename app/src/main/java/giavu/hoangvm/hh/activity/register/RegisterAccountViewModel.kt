package giavu.hoangvm.hh.activity.register

import android.app.Application
import androidx.lifecycle.*
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.extension.combineTripleLatest
import giavu.hoangvm.hh.helper.ResourceProvider
import giavu.hoangvm.hh.model.RegBody
import giavu.hoangvm.hh.model.RegUser
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
class RegisterAccountViewModel(private val resourceProvider: ResourceProvider, application: Application) :
    AndroidViewModel(application) {

    private lateinit var navigator: RegisterAccountNavigator
    private val userApi: UserApi by application.inject()

    private val passwordValidator = PasswordValidator()
    private val emailValidator = EmailAddressValidator()
    private val userNameValidator = UserNameValidator()

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }
    private val _userName = MutableLiveData<String>()
    private val _userNameError = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _emailError = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _passwordError = MutableLiveData<String>()
    private val _registerButtonEnabled = MutableLiveData<Boolean>()

    val registerButtonEnabled: LiveData<Boolean>
        get() = _registerButtonEnabled

    val userNameError: LiveData<String>
        get() = _userNameError
    val emailError: LiveData<String>
        get() = _emailError
    val passwordError: LiveData<String>
        get() = _passwordError

    fun initialize(navigator: RegisterAccountNavigator, owner: LifecycleOwner) {
        this.navigator = navigator
        _registerButtonEnabled.value = false
        _userName.value = ""
        _password.value = ""
        _email.value = ""
        _userNameError.value = null
        checkValidInput(owner)
    }

    fun onUserNameInput(text: CharSequence) {
        _userName.value = text.toString()
        if (text.isNotEmpty() && !userNameValidator.verify(text)) {
            _userNameError.value = resourceProvider.getString(R.string.error_username)
        } else {
            _userNameError.value = null
        }
    }

    fun onEmailInput(text: CharSequence) {
        _email.value = text.toString()
        if (text.isNotEmpty() && !emailValidator.verify(text)) {
            _emailError.value = resourceProvider.getString(R.string.error_email)
        } else {
            _emailError.value = null
        }
    }

    fun onPasswordInput(text: CharSequence) {
        _password.value = text.toString()
        if (text.isNotEmpty() && !passwordValidator.verify(text)) {
            _passwordError.value = resourceProvider.getString(R.string.error_password)
        } else {
            _passwordError.value = null
        }
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

    private fun checkValidInput(owner: LifecycleOwner) {
        combineTripleLatest(
            source1 = _userName.toPublisher(owner),
            source2 = _email.toPublisher(owner),
            source3 = _password.toPublisher(owner)
        )
            .map { triple ->
                val userName = triple.first
                val email = triple.second
                val password = triple.third
                userNameValidator.verify(userName)
                        && emailValidator.verify(email)
                        && passwordValidator.verify(password)

            }
            .subscribeBy(
                onError = Timber::w,
                onNext = { _registerButtonEnabled.postValue(it) }
            )
            .addTo(compositeDisposable)
    }

}
