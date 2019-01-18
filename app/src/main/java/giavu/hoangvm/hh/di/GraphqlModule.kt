package giavu.hoangvm.hh.di

import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.api.CategoryApi
import giavu.hoangvm.hh.core.graphql.GraphqlClientFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
class GraphqlModule {

    val module: Module = org.koin.dsl.module.module {

        single(name = GraphqlClientFactory.TAG_GRAPHQL_BASE_URL) {
            androidApplication().getString(R.string.app_scheme) +
                    "://" +
                    androidApplication().getString(R.string.app_base_url)
        }

        single(name = GraphqlClientFactory.CacheConfig.NO_CACHE.rawValue) {
            GraphqlClientFactory(
                    context = androidContext(),
                    serverUrl = get(GraphqlClientFactory.TAG_GRAPHQL_BASE_URL),
                    httpClient = get(GraphqlClientFactory.TAG_GRAPHQL_HTTP_CLIENT),
                    cacheConfig = GraphqlClientFactory.CacheConfig.NO_CACHE).client
        }

        single(name = GraphqlClientFactory.CacheConfig.USE_CACHE.rawValue) {
            GraphqlClientFactory(
                    context = androidContext(),
                    serverUrl = get(GraphqlClientFactory.TAG_GRAPHQL_BASE_URL),
                    httpClient = get(GraphqlClientFactory.TAG_GRAPHQL_HTTP_CLIENT),
                    cacheConfig = GraphqlClientFactory.CacheConfig.USE_CACHE).client
        }

        single { CategoryApi(get(name = GraphqlClientFactory.CacheConfig.NO_CACHE.rawValue)) }

    }
}