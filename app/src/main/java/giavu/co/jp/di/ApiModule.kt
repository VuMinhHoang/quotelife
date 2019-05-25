package giavu.co.jp.di

import giavu.co.jp.api.QuotesApi
import giavu.co.jp.api.UserApi
import giavu.co.jp.core.retrofit.ApiAccessor
import giavu.co.jp.core.retrofit.JFDApiAccessor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class ApiModule {

    val module: Module = org.koin.dsl.module.module {
        single<ApiAccessor> { JFDApiAccessor(androidApplication()).from() }
        single<UserApi> { get<ApiAccessor>().using(UserApi::class.java) }
        single<QuotesApi> { get<ApiAccessor>().using(QuotesApi::class.java) }

    }
}