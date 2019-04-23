package giavu.hoangvm.hh.core.graphql

import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import java.util.concurrent.TimeUnit

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/13
 */
class GraphqlHttpClientModule {
    val module: Module = org.koin.dsl.module.module {

        single(GraphqlClientFactory.TAG_GRAPHQL_HTTP_CLIENT) {
            val timeoutConfig = TimeoutConfigBuilder().build()
            OkHttpClient.Builder()
                    .addInterceptor(GraphqlHeaderInterceptor(androidApplication()))
                    .connectTimeout(timeoutConfig.connectionTimeoutMillis(), TimeUnit.MILLISECONDS)
                    .writeTimeout(timeoutConfig.writeTimeoutMillis(), TimeUnit.MILLISECONDS)
                    .readTimeout(timeoutConfig.readTimeoutMillis(), TimeUnit.MILLISECONDS)
                    .build()
        }

    }
}