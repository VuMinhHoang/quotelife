package giavu.co.jp.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import giavu.co.jp.api.QuotesApi
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

    private val _quote = MutableLiveData<String>()
    val quote: LiveData<String> = _quote
    private lateinit var navigator: MainNavigator
    private val compositeDisposable = CompositeDisposable()

    fun initialize(navigator: MainNavigator) {
        this.navigator = navigator
        quotesApi.getQuoteOfDay()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { navigator.showProgress() }
            .doFinally { navigator.hideProgress() }
            .subscribeBy(
                onSuccess = { response ->
                    _quote.postValue(response.quote.body)
                },
                onError = {
                    navigator.toError(it)
                })
            .addTo(compositeDisposable)
    }

}