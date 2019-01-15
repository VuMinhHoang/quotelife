package giavu.hoangvm.hh.di

import giavu.hoangvm.hh.activity.login.MainViewModel
import giavu.hoangvm.hh.activity.register.RegisterAccountViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
class ViewModelModule {

    val module: Module = org.koin.dsl.module.module {
        viewModel {
            MainViewModel(application = androidApplication())
        }
        viewModel {
            RegisterAccountViewModel(application = androidApplication())
        }
    }
}