package giavu.hoangvm.hh.di

import giavu.hoangvm.hh.core.graphql.GraphqlHttpClientModule
import org.koin.dsl.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
class Modules {
    fun get(): List<Module> = listOf(
            GraphqlHttpClientModule().module,
            GraphqlModule().module,
            UseCaseModule().module,
            ApiModule().module,
            ViewModelModule().module,
            LocalDataStoreModule().module
    )
}