package giavu.hoangvm.hh.di

import giavu.hoangvm.hh.firebase.FcmTokenStore
import giavu.hoangvm.hh.tracker.FirebaseTracker
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

class LocalDataStoreModule {

    val module: Module = module {
        single { FcmTokenStore() }
        single { FirebaseTracker(context = androidApplication()) }
    }
}
