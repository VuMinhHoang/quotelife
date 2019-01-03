package giavu.hoangvm.japanfood.core.retrofit

import android.content.Context
import giavu.hoangvm.japanfood.core.graphql.ApiHeader

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class JFDApiAccessor(private val context: Context) : ApiAccessor(context = context) {
    override fun getBaseUrl(): String {
        return "https://sportsop-soccer-sports-open-data-v1.p.mashape.com/"
    }

    override fun onCreateHeaders(headers: MutableMap<String, String>) {
        headers.put(ApiHeader.X_MASHAPE_KEY, "ftHoprj6CGmshGTkHN641OnT91qxp1odrEjjsnukrGxRpG2liY")
        headers.put(ApiHeader.KEY_ACCEPT, ApiHeader.VALUE_ACCEPT_JSON)
    }

    fun from(): ApiAccessor {
        return JFDApiAccessor(context)
    }
}