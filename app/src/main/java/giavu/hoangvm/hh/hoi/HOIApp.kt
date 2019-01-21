package giavu.hoangvm.hh.hoi

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.facebook.stetho.Stetho
import com.github.arturogutierrez.Badges
import com.github.arturogutierrez.BadgesNotSupportedException
import com.google.firebase.FirebaseApp
import giavu.hoangvm.hh.BuildConfig
import giavu.hoangvm.hh.firebase.FcmToken
import giavu.hoangvm.hh.firebase.FcmTokenStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.leolin.shortcutbadger.ShortcutBadger
import org.koin.android.ext.android.inject
import timber.log.Timber



/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
open class HOIApp : Application() {

    private val TAG = HOIApp::class.java.simpleName
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
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        KoinInitializer(this).initialize()
        FirebaseApp.initializeApp(this)
        initialize()
    }

    private fun initialize() {
        initFcm()
        initBadge()
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

    private fun initBadge() {
        Log.d(TAG, ShortcutBadger.isBadgeCounterSupported(this).toString())
        ShortcutBadger.applyCount(this, 17)
    }

    private fun observeFcmToken() {
        fcmTokenStore.token.observeForever { token ->
            Log.d("Token", token)
        }
    }

    private val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks{
        override fun onActivityPaused(activity: Activity?) {
            Log.d(TAG, "onActivityPaused")
        }

        override fun onActivityResumed(activity: Activity?) {
            Log.d(TAG, "onActivityResumed")
        }

        override fun onActivityStarted(activity: Activity?) {
            Log.d(TAG, "onActivityStarted")
        }

        override fun onActivityDestroyed(activity: Activity?) {
            Log.d(TAG, "onActivityDestroyed")
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            Log.d(TAG, "onActivitySaveInstanceState")
        }

        override fun onActivityStopped(activity: Activity?) {
            Log.d(TAG, "onActivityStopped")
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            Log.d(TAG, "onActivityCreated")
        }
    }
}