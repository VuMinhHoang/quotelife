package giavu.hoangvm.hh.core.graphql

import com.apollographql.apollo.api.Response
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/09
 */
object ApiHandler {
    private const val KEY_EXTENSION = "extension"
    private const val KEY_CODE = "code"

    fun <T> handleResponse(response: Response<T>): Response<T> {
        if (response.hasErrors()) {
            val message: String? = response.errors().firstOrNull()?.message()
            val extensions = response.errors().firstOrNull()?.customAttributes()?.get(KEY_EXTENSION)
            val code = if (extensions is Map<*, *>) {
                extensions[KEY_CODE] as? String?
            } else null

            Timber.w("code:$code")
            Timber.w("message:$message")

            throw ApiException(code = code, message = message)
        } else {
            return response
        }
    }
}