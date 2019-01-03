package giavu.hoangvm.japanfood.core.graphql

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.ApolloStoreOperation
import com.apollographql.apollo.cache.normalized.sql.ApolloSqlHelper
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import okhttp3.OkHttpClient
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/09
 */
class GraphqlClientFactory (private val context: Context,
                            private val serverUrl: String,
                            httpClient: OkHttpClient,
                            cacheConfig: CacheConfig) {

    enum class CacheConfig(val rawValue: String) {
        USE_CACHE("use-cache"),
        NO_CACHE("no-cache"),
    }

    companion object {
        const val TAG_GRAPHQL_BASE_URL = "tag_graphql_base_url"
        const val TAG_GRAPHQL_HTTP_CLIENT = "tag_graphql_http_client"
    }


    private val cacheFactory =
            SqlNormalizedCacheFactory(ApolloSqlHelper.create(context))

    val client: ApolloClient =
            when (cacheConfig) {
                CacheConfig.USE_CACHE -> {
                    ApolloClient.builder()
                            .serverUrl(serverUrl)
                            .normalizedCache(cacheFactory)
                            .okHttpClient(httpClient)
                            .build()
                }
                CacheConfig.NO_CACHE -> {
                    ApolloClient.builder()
                            .serverUrl(serverUrl)
                            .okHttpClient(httpClient)
                            .build()
                }
            }

    fun clearCache() {
        ApolloClient.builder()
                .serverUrl(serverUrl)
                .normalizedCache(SqlNormalizedCacheFactory(ApolloSqlHelper.create(context)))
                .build()
                .clearNormalizedCache(object : ApolloStoreOperation.Callback<Boolean> {
                    override fun onSuccess(result: Boolean?) {
                        Timber.i("graphql cached records delete $result")
                    }

                    override fun onFailure(t: Throwable?) {
                        Timber.i("graphql cached records delete $t")
                    }
                })
    }

}