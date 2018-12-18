package giavu.hoangvm.japanfood.activity.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/15
 */
class MainViewModel: ViewModel() {

    private val _password = MutableLiveData<String>()
    val password : LiveData<String>
        get() = _password

    private val _passwordConfirm = MutableLiveData<String>()
    val passwordConfirm : LiveData<String>
        get() = _passwordConfirm

    var passwordInput = String()

    fun onPasswordTextChanged(text: CharSequence){
        Log.d("Hoang", text.toString())
        _password.postValue(text.toString())
    }

    fun onPasswordTextConfirmChanged(text: CharSequence){
        _passwordConfirm.postValue(text.toString())
    }


}