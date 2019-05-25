package giavu.co.jp.di

import giavu.co.jp.firebase.FcmTokenStore
import giavu.co.jp.helper.UserSharePreference
import giavu.co.jp.tracker.FirebaseTracker
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

class LocalDataStoreModule {

    val module: Module = module {
        single { FcmTokenStore() }
        single { FirebaseTracker(context = androidApplication()) }
        single { UserSharePreference(context = androidApplication()) }
    }
}
