package giavu.hoangvm.hh.di

import giavu.hoangvm.hh.activity.login.LoginViewModel
import giavu.hoangvm.hh.activity.main.MainViewModel
import giavu.hoangvm.hh.activity.register.RegisterAccountViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
class ViewModelModule {

    val module: Module = org.koin.dsl.module.module {
        viewModel { MainViewModel(quotesApi = get()) }
        viewModel { LoginViewModel(userApi = get()) }
        viewModel { RegisterAccountViewModel(resourceProvider = get(), userApi = get()) }
    }
}
