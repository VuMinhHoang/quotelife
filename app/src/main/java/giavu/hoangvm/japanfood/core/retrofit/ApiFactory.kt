package giavu.hoangvm.japanfood.core.retrofit

import android.util.Log
import giavu.hoangvm.japanfood.core.graphql.TimeoutConfigBuilder
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class ApiFactory(
        private val baseUrl: String,
        private val headerAccessor: HeaderAccessor,
        private val logging: HttpLoggingInterceptor,
        private val connectionPool: ConnectionPool) {

    fun <T> create(klass: Class<T>): T {
        return createRetrofit().create(klass)
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createClient())
                .addConverterFactory(createGsonConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val timeoutConfig = TimeoutConfigBuilder().build()
        return OkHttpClientFactory().createInstance(connectionPool, logging,
                HeaderInterceptor(), timeoutConfig)
    }

    private fun createGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    inner class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = RequestFactory(headerAccessor.get(), chain.request()).create()
            Log.d("TEST", headerAccessor.get().toString())
            Log.d("TEST", request.toString())
            return chain.proceed(request)
        }
    }

    interface HeaderAccessor {
        fun get(): Map<String, String>
    }

}