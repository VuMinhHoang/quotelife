package giavu.hoangvm.hh.activity.quotelist

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import giavu.hoangvm.hh.api.QuotesApi
import giavu.hoangvm.hh.model.Response
import io.reactivex.disposables.CompositeDisposable

/**
 * @Author: Hoang Vu
 * @Date:   2019/02/26
 */
class QuoteDataSourceFactory(
    private val quotesApi: QuotesApi,
    private val compositeDisposable: CompositeDisposable
): DataSource.Factory<String, Response>() {

    private val quoteDataSourceLiveData = MutableLiveData<QuoteDataSource>()
    override fun create(): DataSource<String, Response> {
        val quoteDataSource = QuoteDataSource(quotesApi = quotesApi, compositeDisposable = compositeDisposable)
        quoteDataSourceLiveData.postValue(quoteDataSource)
        return quoteDataSource
    }
}