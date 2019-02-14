package giavu.hoangvm.hh.activity.quotelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.api.QuotesApi
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.model.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import timber.log.Timber

class QuoteListActivity : AppCompatActivity() {

    private val quoteApi: QuotesApi by inject()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, QuoteListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_list)
        fetchQuoteList()
    }

    private fun fetchQuoteList() {
        quoteApi.getQuotes(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{showProgress()}
            .doFinally { hideProgress() }
            .subscribeBy(
                onSuccess = ::onSuccess,
                onError = ::onError
            )
            .addTo(compositeDisposable)
    }

    private fun onSuccess(quoteList: Response) {
        Timber.d("List quote :%s", quoteList.quotes.size.toString())
    }

    private fun onError(throwable: Throwable) {
        Timber.d(throwable)
    }
}
