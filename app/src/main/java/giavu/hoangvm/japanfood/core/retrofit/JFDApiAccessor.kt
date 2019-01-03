package giavu.hoangvm.japanfood.core.retrofit

import android.content.Context
import giavu.hoangvm.japanfood.R
import giavu.hoangvm.japanfood.core.graphql.ApiHeader

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class JFDApiAccessor(private val context: Context) : ApiAccessor(context = context) {
    override fun getBaseUrl(): String {
        return context.getString(R.string.app_scheme) + "://" + context.getString(R.string.sport_base_url)
    }

    override fun onCreateHeaders(headers: MutableMap<String, String>) {
        headers.put(ApiHeader.X_MASHAPE_KEY, context.getString(R.string.sport_apikey))
        headers.put(ApiHeader.KEY_ACCEPT, ApiHeader.VALUE_ACCEPT_JSON)
    }

    fun from(): ApiAccessor {
        return JFDApiAccessor(context)
    }
}