package giavu.hoangvm.hh.api

import giavu.hoangvm.hh.model.QuoteOfDay
import giavu.hoangvm.hh.model.Response
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