package giavu.hoangvm.hh.hoi

import android.app.Application
import android.util.Log
import android.webkit.WebView
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import giavu.hoangvm.hh.BuildConfig
import giavu.hoangvm.hh.firebase.FcmToken
import giavu.hoangvm.hh.firebase.FcmTokenStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
open class HOIApp : Application() {

    private val fcmTokenStore: FcmTokenStore by inject()

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build())

            WebView.setWebContentsDebuggingEnabled(true)
        }
        KoinInitializer(this).initialize()
        FirebaseApp.initializeApp(this)
        initialize()
    }

    private fun initialize() {
        initFcm()
    }

    private fun initFcm() {
        observeFcmToken()
        FcmToken.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = fcmTokenStore.token::setValue,
                        onError = Timber::w
                )
    }

    private fun observeFcmToken() {
        fcmTokenStore.token.observeForever { token ->
            Log.d("Token", token)
        }
    }
}