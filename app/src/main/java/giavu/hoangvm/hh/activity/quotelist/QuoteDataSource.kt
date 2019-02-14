package giavu.hoangvm.hh.activity.quotelist

import androidx.paging.PageKeyedDataSource
import giavu.hoangvm.hh.api.QuotesApi
import giavu.hoangvm.hh.model.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
): PageKeyedDataSource<String, Response>() {
    
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Response>) {
        quotesApi.getQuotes(params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {},
                onError = Timber::w)
            .addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Response>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Response>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}