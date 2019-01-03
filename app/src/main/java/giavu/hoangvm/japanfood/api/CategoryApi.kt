package giavu.hoangvm.japanfood.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import giavu.hoangvm.japanfood.core.graphql.ApiHandler
import io.reactivex.Single
import query.CategoryQuery

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/09
 */
class CategoryApi(private val client: ApolloClient) {

    fun get() : Single<Response<CategoryQuery.Data>> {
        return CategoryQuery().let { query ->
            client.query(query)
        }.let { call ->
            Rx2Apollo.from(call)
        }.let { observable ->
            Single.fromObservable(observable)
        }.map {
            ApiHandler.handleResponse(it)
        }
    }
}