package giavu.hoangvm.hh.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
class RegisterAccountViewModel: ViewModel() {

    private lateinit var navigator: RegisterAccountNavigator

    private val _userName = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _registerButtonEnabled = MutableLiveData<Boolean>()

    val registerButtonEnabled: LiveData<Boolean>
        get() = _registerButtonEnabled

    fun initialize(navigator: RegisterAccountNavigator) {
        this.navigator = navigator
    }

    fun onUserNameInput(text: CharSequence) {
        _userName.postValue(text.toString())
    }

    fun onEmailInput(text: CharSequence) {
        _email.postValue(text.toString())
    }

    fun onPasswordInput(text: CharSequence) {
        _password.postValue(text.toString())
    }

}