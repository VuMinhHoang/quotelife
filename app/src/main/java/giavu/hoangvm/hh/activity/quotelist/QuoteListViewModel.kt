package giavu.hoangvm.hh.activity.quotelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import giavu.hoangvm.hh.api.QuotesApi
import giavu.hoangvm.hh.model.Quote
import giavu.hoangvm.hh.utils.State
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

/**
 * @Author: Hoang Vu
 * @Date:   2019/02/14
 */
class QuoteListViewModel(application: Application): AndroidViewModel(application){
    private val quotesApi: QuotesApi by application.inject()
    private val compositeDisposable = CompositeDisposable()
    private val quoteDataSourceFactory: QuoteDataSourceFactory
    private val pageSize = 25
    val quoteList: LiveData<PagedList<Quote>>

    init {
        quoteDataSourceFactory = QuoteDataSourceFactory(quotesApi, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .build()
        quoteList = LivePagedListBuilder<Int, Quote>(quoteDataSourceFactory,config).build()
    }

    fun retry() {
        quoteDataSourceFactory.quoteDataSourceLiveData.value?.retry()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<QuoteDataSource,
            State>(quoteDataSourceFactory.quoteDataSourceLiveData, QuoteDataSource::state)

    fun listIsEmpty(): Boolean {
        return quoteList.value?.isEmpty() ?: true
    }
}