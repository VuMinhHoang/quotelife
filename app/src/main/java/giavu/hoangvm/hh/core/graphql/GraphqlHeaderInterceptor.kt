package giavu.hoangvm.hh.core.graphql

import android.content.Context
import giavu.hoangvm.hh.R
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
class GraphqlHeaderInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
            with(chain.request().newBuilder()) {
                createHeaders().entries.forEach { header ->
                    addHeader(header.key, header.value)
                }
                build()
            }.let { request ->
                chain.proceed(request)
            }


    private fun createHeaders(): Map<String, String> {
        return HashMap<String, String>().apply {
            put(ApiHeader.KEY_AUTHORIZATION, ApiHeader.VALUE_AUTHORIZATION_BEARER_PREFIX + context.getString(R.string.app_api_key))
            //put(ApiHeader.CLIENT_ID, context.getString(R.string.client_id))
        }
    }
}