package giavu.hoangvm.hh.di

import giavu.hoangvm.hh.firebase.FcmTokenStore
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

class LocalDataStoreModule {

    val module: Module = module {
        single { FcmTokenStore() }
    }
}