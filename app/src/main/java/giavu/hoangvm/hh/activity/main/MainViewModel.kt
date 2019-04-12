package giavu.hoangvm.hh.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import giavu.hoangvm.hh.api.QuotesApi
import giavu.hoangvm.hh.utils.ResultState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/05
 */
class MainViewModel(private val quotesApi: QuotesApi) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _resultRequest: MutableLiveData<ResultState<Unit>> = MutableLiveData()
    private val _showProgressRequest: MutableLiveData<Unit> = MutableLiveData()
    private val _hideProgressRequest: MutableLiveData<Unit> = MutableLiveData()

    val quoteTextBody: MutableLiveData<String> = MutableLiveData()

    val resultRequest: LiveData<ResultState<Unit>>
        get() = _resultRequest

    val showProgressRequest: LiveData<Unit>
        get() = _showProgressRequest

    val hideProgressRequest: LiveData<Unit>
        get() = _hideProgressRequest

    fun initialize() {
        quotesApi.getQuoteOfDay()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _showProgressRequest.value = Unit }
            .doFinally { _hideProgressRequest.value = Unit }
            .subscribeBy(
                onSuccess = { response ->
                    _resultRequest.value = ResultState.Success(Unit)
                    quoteTextBody.value = response.quote.body
                },
                onError = {
                    _resultRequest.value = ResultState.Failure(it)
                })
            .addTo(compositeDisposable)
    }

}
