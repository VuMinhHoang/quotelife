package giavu.co.jp.core.retrofit

import androidx.annotation.NonNull
import giavu.co.jp.core.graphql.TimeoutConfig
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class OkHttpClientFactory {
    fun createInstance(@NonNull connectionPool: ConnectionPool,
                       @NonNull loggingInterceptor: HttpLoggingInterceptor,
                       @NonNull headerInterceptor: ApiFactory.HeaderInterceptor,
                       @NonNull timeoutConfig: TimeoutConfig): OkHttpClient {
        return OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(timeoutConfig.connectionTimeoutMillis(), TimeUnit.MILLISECONDS)
                .writeTimeout(timeoutConfig.writeTimeoutMillis(), TimeUnit.MILLISECONDS)
                .readTimeout(timeoutConfig.readTimeoutMillis(), TimeUnit.MILLISECONDS)
                .build()
    }
}