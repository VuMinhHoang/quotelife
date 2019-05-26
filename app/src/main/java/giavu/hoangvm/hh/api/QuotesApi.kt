package giavu.hoangvm.hh.api

import giavu.hoangvm.hh.model.QuoteOfDay
import giavu.hoangvm.hh.model.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
interface QuotesApi {

    @GET("quotes")
    fun getQuotes(@Query("page") page: Int): Single<Response>

    @GET("qotd")
    fun getQuoteOfDay(): Single<QuoteOfDay>

}