package giavu.hoangvm.hh.activity.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.model.UserResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/21
 */
class ProfileViewModel(private val userApi: UserApi) : ViewModel() {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private val _showProgressRequest: MutableLiveData<Unit> = MutableLiveData()
    private val _hideProgressRequest: MutableLiveData<Unit> = MutableLiveData()
    private val _userName: MutableLiveData<String> = MutableLiveData()
    private val _follower: MutableLiveData<String> = MutableLiveData()
    private val _following: MutableLiveData<String> = MutableLiveData()

    val showProgressRequest: LiveData<Unit>
        get() = _showProgressRequest

    val hideProgressRequest: LiveData<Unit>
        get() = _hideProgressRequest

    val userName: LiveData<String>
        get() = _userName

    val follower: LiveData<String>
        get() = _follower

    val following: LiveData<String>
        get() = _following

    fun initialize() {
        userApi.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _showProgressRequest.value = Unit }
            .doFinally { _hideProgressRequest.value = Unit }
            .subscribeBy(
                onSuccess = ::onSuccess,
                onError = Timber::w
            )
            .addTo(compositeDisposable)
    }

    private fun onSuccess(userResponse: UserResponse) {
        _userName.value = userResponse.login
        _follower.value = userResponse.followers.toString()
        _following.value = userResponse.following.toString()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}