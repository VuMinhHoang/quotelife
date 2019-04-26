package giavu.hoangvm.hh.hoi

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import giavu.hoangvm.hh.BuildConfig
import giavu.hoangvm.hh.firebase.FcmToken
import giavu.hoangvm.hh.firebase.FcmTokenStore
import giavu.hoangvm.hh.notify.NotificationChannelRegister
import giavu.hoangvm.hh.tracker.Event
import giavu.hoangvm.hh.tracker.FirebaseTracker
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

    private val TAG = HOIApp::class.java.simpleName
    private val fcmTokenStore: FcmTokenStore by inject()
    private val tracker: FirebaseTracker by inject()

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        KoinInitializer(this).initialize()
        FirebaseApp.initializeApp(this)
        registerNotificationChannelIfNeeds()
        initialize()
        tracker.track(Event.FirstOpen)
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

    private fun registerNotificationChannelIfNeeds() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannelRegister(this).registerAll()
        }
    }

    private val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks{
        override fun onActivityPaused(activity: Activity?) {
            Timber.d("onActivityPaused")
        }

        override fun onActivityResumed(activity: Activity?) {
            Timber.d("onActivityResumed")
        }

        override fun onActivityStarted(activity: Activity?) {
            Timber.d("onActivityStarted")
        }

        override fun onActivityDestroyed(activity: Activity?) {
            Timber.d("onActivityDestroyed")
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            Timber.d("onActivitySaveInstanceState")
        }

        override fun onActivityStopped(activity: Activity?) {
            Timber.d("onActivityStopped")
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            Timber.d("onActivityCreated")
        }
    }
}
