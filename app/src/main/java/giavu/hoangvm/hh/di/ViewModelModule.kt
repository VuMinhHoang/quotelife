package giavu.hoangvm.hh.di

import giavu.hoangvm.hh.activity.login.LoginViewModel
import giavu.hoangvm.hh.activity.main.MainViewModel
import giavu.hoangvm.hh.activity.profile.ProfileViewModel
import giavu.hoangvm.hh.activity.register.RegisterViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
class ViewModelModule {

    val module: Module = org.koin.dsl.module.module {
        viewModel { MainViewModel(quotesApi = get()) }
        viewModel { LoginViewModel(userApi = get(), userSharePreference = get()) }
        viewModel { ProfileViewModel(userApi = get()) }
        viewModel { RegisterViewModel(resourceProvider = get(), userApi = get()) }
    }
}
