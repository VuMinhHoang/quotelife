package giavu.hoangvm.hh.activity.register

import android.util.Log
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
class RegisterAccountViewModel: ViewModel() {

    private lateinit var navigator: RegisterAccountNavigator
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
        Log.d("Test","blabla")
        _userName.postValue(text.toString())
    }

    fun onEmailInput(text: CharSequence) {
        _email.postValue(text.toString())
    }

    fun onPasswordInput(text: CharSequence) {
        _password.postValue(text.toString())
        Log.d("Test",_password.value)
    }

    private fun checkValidInput(owner: LifecycleOwner){
        combineLatest(source1 = _userName.toPublisher(owner),
                source2 = _email.toPublisher(owner),
                source3 = _password.toPublisher(owner))
                .map { triple ->
                    val userName = triple.first
                    val email = triple.second
                    val password = triple.third
                    (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() )
                }
                .subscribeBy(
                        onError = Timber::w,
                        onNext = {_registerButtonEnabled.postValue(it)}
                )
                .addTo(compositeDisposable)
    }

}