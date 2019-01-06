package giavu.hoangvm.hh.activity.dailyquote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import giavu.hoangvm.hh.api.QuotesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/05
 */
class QuoteViewModel(application: Application): AndroidViewModel(application) {

    private val quoteApi: QuotesApi by application.inject()

    private val _quote = MutableLiveData<String>()
    val quote : LiveData<String> = _quote

    fun initialize(){
        quoteApi.getQuoteOfDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { response ->
                            _quote.postValue(response.quote.body)
                },
                        onError = {

                        })
    }

}