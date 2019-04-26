package giavu.co.jp.core.retrofit

import android.content.Context
import giavu.co.jp.R
import giavu.co.jp.core.graphql.ApiHeader
import giavu.co.jp.helper.UserSharePreference

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class JFDApiAccessor(private val context: Context) : ApiAccessor(context = context) {
    override fun getBaseUrl(): String {
        return context.getString(R.string.app_scheme) + "://" + context.getString(R.string.quote_url)
    }

    override fun onCreateHeaders(headers: MutableMap<String, String>) {
        headers.put(ApiHeader.KEY_AUTHORIZATION, context.getString(R.string.quote_api_key))
        headers.put(ApiHeader.CONTENT_TYPE, ApiHeader.VALUE_ACCEPT_JSON)
        headers.put(ApiHeader.KEY_USER_AGENT, UserSharePreference.fromContext(context).getUserSession())
    }

    fun from(): ApiAccessor {
        return JFDApiAccessor(context)
    }
}