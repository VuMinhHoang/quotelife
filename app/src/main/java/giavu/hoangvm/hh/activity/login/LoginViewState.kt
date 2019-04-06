package giavu.hoangvm.hh.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/06
 */
class LoginViewState(
    private val _userName: LiveData<String>,
    private val _password: LiveData<String>
) {

    val loginBtnEnable: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {

        val observer: Observer<String> = Observer {
            this.value = _userName.value.orEmpty().isNotEmpty() && _password.value.orEmpty().isNotEmpty()
        }
        addSource(_userName, observer)
        addSource(_password, observer)
    }
}