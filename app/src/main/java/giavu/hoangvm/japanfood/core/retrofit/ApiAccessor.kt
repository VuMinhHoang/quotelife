package giavu.hoangvm.japanfood.core.retrofit

import android.content.Context
import okhttp3.ConnectionPool
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.*

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
abstract class ApiAccessor(private val context: Context) : ApiFactory.HeaderAccessor {
    private val connectionPool = ConnectionPool()
    private val interceptor = createLoggingInterceptor()


    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor(LoggerImpl())
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    internal abstract fun getBaseUrl(): String

    internal abstract fun onCreateHeaders(headers: MutableMap<String, String>)

    fun <T> using(klass: Class<T>): T {
        val factory = ApiFactory(getBaseUrl(),
                this,
                interceptor,
                connectionPool)
        return factory.create(klass)
    }

    private class LoggerImpl : HttpLoggingInterceptor.Logger {

        override fun log(message: String) {
            Timber.tag(TAG).d(message)
        }

        companion object {
            private val TAG = "ApiAccessor"
        }
    }

    override fun get(): MutableMap<String, String> {
        val headers = HashMap<String, String>()
        //headers.put(ApiHeader.KEY_USER_AGENT, "")
        //headers.put(ApiHeader.KEY_ACCEPT_LANGUAGE, context.getString(R.string.language))
        onCreateHeaders(headers)
        return headers
    }
}