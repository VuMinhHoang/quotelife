package giavu.hoangvm.japanfood.di

import giavu.hoangvm.japanfood.usecase.CategoryUseCase
import org.koin.dsl.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/09
 */
class UseCaseModule {

    val module: Module = org.koin.dsl.module.module {
        single{CategoryUseCase(get())}
    }
}