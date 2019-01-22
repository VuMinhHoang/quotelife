package giavu.hoangvm.hh.activity.dailyquote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import giavu.hoangvm.hh.api.QuotesApi
import giavu.hoangvm.hh.api.UserApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/05
 */
class QuoteViewModel(application: Application) : AndroidViewModel(application) {

    private val quoteApi: QuotesApi by application.inject()
    private val userApi: UserApi by application.inject()

    private val _quote = MutableLiveData<String>()
    val quote: LiveData<String> = _quote
    private lateinit var navigator: QuoteNavigator

    fun initialize(navigator: QuoteNavigator) {
        this.navigator = navigator

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

    fun logout() {
        userApi.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { navigator.showProgress() }
                .doFinally { navigator.hideProgress() }
                .subscribeBy(onSuccess = {
                    navigator.toLogout(it)
                },
                        onError = {
                            navigator.toError(it)
                        })

    }

}