package giavu.hoangvm.japanfood.di

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
    }
}