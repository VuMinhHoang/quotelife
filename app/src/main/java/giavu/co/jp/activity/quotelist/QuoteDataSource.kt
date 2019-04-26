package giavu.co.jp.activity.quotelist

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import giavu.co.jp.api.QuotesApi
import giavu.co.jp.model.Quote
import giavu.co.jp.utils.State
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2019/02/14
 */
class QuoteDataSource(
    private val quotesApi: QuotesApi,
    private val compositeDisposable: CompositeDisposable
): PageKeyedDataSource<Int, Quote>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Quote>) {
        updateState(State.LOADING)
        quotesApi.getQuotes(params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    updateState(State.DONE)
                    callback.onResult(it.quotes, null, 2)

                },
                onError = {
                    updateState(State.ERROR)
                    setRetry(Action { loadInitial(params, callback) })
                    Timber.d(it)
                })
            .addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Quote>) {
        updateState(State.LOADING)
        quotesApi.getQuotes(params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    updateState(State.DONE)
                    callback.onResult(it.quotes, params.key + 1)

                },
                onError = {
                    updateState(State.ERROR)
                    setRetry(Action { loadAfter(params, callback) })
                    Timber.d(it)
                })
            .addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Quote>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}