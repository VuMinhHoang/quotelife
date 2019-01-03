package giavu.hoangvm.japanfood.api

import giavu.hoangvm.japanfood.model.League
import io.reactivex.Single
import retrofit2.http.GET

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
interface LeaguesApi {
    @GET("v1/leagues")
    fun getLeaguesList(): Single<List<League>>
}