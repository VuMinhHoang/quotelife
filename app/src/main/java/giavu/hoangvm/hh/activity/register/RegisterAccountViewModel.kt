package giavu.hoangvm.hh.activity.register

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.helper.ResourceProvider
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.model.RegBody
import giavu.hoangvm.hh.model.RegUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
class RegisterAccountViewModel(
    resourceProvider: ResourceProvider,
    private val userApi: UserApi
) : ViewModel() {

    private val _successResult: MutableLiveData<LoginResponse> = MutableLiveData()
    private val _failureResult: MutableLiveData<Throwable> = MutableLiveData()
    private val _showProgressRequest: MutableLiveData<Unit> = MutableLiveData()
    private val _hideProgressRequest: MutableLiveData<Unit> = MutableLiveData()
    private val _gotoLogin: MutableLiveData<Unit> = MutableLiveData()


    val successResult: LiveData<LoginResponse>
        get() = _successResult

    val failureResult: LiveData<Throwable>
        get() = _failureResult

    val showProgressRequest: LiveData<Unit>
        get() = _showProgressRequest

    val hideProgressRequest: LiveData<Unit>
        get() = _hideProgressRequest

    val gotoLogin: LiveData<Unit>
        get() = _gotoLogin

    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val viewState = RegisterAccountViewState(
        _userName = userName,
        _email = email,
        _password = password,
        _resourceProvider = resourceProvider
    )

    fun gotoLogin() {
        _gotoLogin.value = Unit
    }

    @SuppressLint("CheckResult")
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
            .doOnSubscribe { _showProgressRequest.value = Unit }
            .doFinally { _hideProgressRequest.value = Unit }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { error ->
                    _failureResult.value = error
                },
                onSuccess = { response ->
                    _successResult.value = response
                }
            )
    }

}
