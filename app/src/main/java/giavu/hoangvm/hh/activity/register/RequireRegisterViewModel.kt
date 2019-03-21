package giavu.hoangvm.hh.activity.register

import androidx.lifecycle.*
import giavu.hoangvm.hh.extension.combineTripleLatest
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class RequireRegisterViewModel: ViewModel() {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val _userName = MutableLiveData<String>()
    private val _userNameError = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    val userNameError: LiveData<String>
        get() = _userNameError


    fun initialize(owner: LifecycleOwner) {
        _userName.value = ""
        _password.value = ""
        _email.value = ""
        _userNameError.value = null
        checkValidInput(owner)
    }

    fun onUserNameInput(text: CharSequence) {
        _userName.value = text.toString()
        if(text.toString().length > 10) {
            _userNameError.value = "Please input user name"
        }else{
            _userNameError.value = null
        }
    }

    fun onEmailInput(text: CharSequence) {
        _email.value = text.toString()
    }

    fun onPasswordInput(text: CharSequence) {
        _password.value = text.toString()
    }

    private fun checkValidInput(owner: LifecycleOwner) {
        combineTripleLatest(
            source1 = _userName.toPublisher(owner),
            source2 = _email.toPublisher(owner),
            source3 = _password.toPublisher(owner))
            .map { triple ->
                val userName = triple.first
                val email = triple.second
                val password = triple.third
                userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
            }
            .subscribeBy(
                onError = Timber::w,
                onNext = { Timber.d("Check Ok") }
            )
            .addTo(compositeDisposable)
    }

}
