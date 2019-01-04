package giavu.hoangvm.japanfood.api

import giavu.hoangvm.japanfood.model.QuoteOfDay
import giavu.hoangvm.japanfood.model.Response
import io.reactivex.Single
import retrofit2.http.GET

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
interface QuotesApi {

    @GET("quotes")
    fun getQuotes(): Single<Response>

    @GET("qotd")
    fun getQuoteOfDay(): Single<QuoteOfDay>

}