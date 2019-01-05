package giavu.hoangvm.japanfood.di

import giavu.hoangvm.japanfood.api.QuotesApi
import giavu.hoangvm.japanfood.api.UserApi
import giavu.hoangvm.japanfood.core.retrofit.ApiAccessor
import giavu.hoangvm.japanfood.core.retrofit.JFDApiAccessor
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