package giavu.hoangvm.hh.activity.login

import android.annotation.SuppressLint
import androidx.lifecycle.*
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.helper.UserSharePreference
import giavu.hoangvm.hh.model.LoginBody
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.model.User
import giavu.hoangvm.hh.utils.Status
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/15
 */
class LoginViewModel(
    private val userApi: UserApi,
    private val userSharePreference: UserSharePreference
) : ViewModel() {

    private val _status: MutableLiveData<Status<Unit>> = MutableLiveData()
    private val _showProgressRequest: MutableLiveData<Unit> = MutableLiveData()
    private val _hideProgressRequest: MutableLiveData<Unit> = MutableLiveData()
    private val _registerEvent: MutableLiveData<Unit> = MutableLiveData()
    private val _loginByGuestEvent: MutableLiveData<Unit> = MutableLiveData()

    val status: LiveData<Status<Unit>>
        get() = _status

    val showProgressRequest: LiveData<Unit>
        get() = _showProgressRequest

    val hideProgressRequest: LiveData<Unit>
        get() = _hideProgressRequest

    val registerEvent: LiveData<Unit>
        get() = _registerEvent

    val loginByGuestEvent: LiveData<Unit>
        get() = _loginByGuestEvent

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val loginBtnEnable: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {

        val observer: Observer<String> = Observer {
            this.value = username.value.orEmpty().isNotEmpty()
                    && password.value.orEmpty().isNotEmpty()
        }
        addSource(username, observer)
        addSource(password, observer)
    }

    @SuppressLint("CheckResult")
    fun login() {
        val loginBody = LoginBody(
            email = username.value.toString(),
            password = password.value.toString()
        )
        val body = User(
            user = loginBody
        )
        userApi.login(body)
            .flatMapCompletable { saveApiToken(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _showProgressRequest.value = Unit }
            .doFinally { _hideProgressRequest.value = Unit }
            .subscribeBy(
                onComplete = {
                    _status.value = Status.Success(Unit)
                },
                onError = { error ->
                    _status.value = Status.Failure(error)
                }
            )
    }

    private fun saveApiToken(loginResponse: LoginResponse): Completable {
        return Completable.fromAction {
            userSharePreference.updateUserPref(loginResponse)
        }
    }

    fun register() {
        _registerEvent.value = Unit
    }

    fun loginByGuest() {
        _loginByGuestEvent.value = Unit
    }
}
