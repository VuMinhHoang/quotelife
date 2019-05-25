package giavu.co.jp.activity.register

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import giavu.co.jp.api.UserApi
import giavu.co.jp.helper.ResourceProvider
import giavu.co.jp.helper.UserSharePreference
import giavu.co.jp.model.LoginResponse
import giavu.co.jp.model.RegBody
import giavu.co.jp.model.RegUser
import giavu.co.jp.utils.Status
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
class RegisterViewModel(
    resourceProvider: ResourceProvider,
    private val userApi: UserApi,
    private val userSharePreference: UserSharePreference
) : ViewModel() {

    private val _status: MutableLiveData<Status<Unit>> = MutableLiveData()
    private val _showProgressRequest: MutableLiveData<Unit> = MutableLiveData()
    private val _hideProgressRequest: MutableLiveData<Unit> = MutableLiveData()
    private val _gotoLogin: MutableLiveData<Unit> = MutableLiveData()

    val status: LiveData<Status<Unit>>
        get() = _status

    val showProgressRequest: LiveData<Unit>
        get() = _showProgressRequest

    val hideProgressRequest: LiveData<Unit>
        get() = _hideProgressRequest

    val gotoLogin: LiveData<Unit>
        get() = _gotoLogin

    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val viewState = RegisterViewState(
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

}
