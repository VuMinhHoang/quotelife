package giavu.co.jp.tracker

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber

class FirebaseTracker(private val context: Context) {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun track(event: Event) {
        Timber.d("track:%s", event.eventName)
        firebaseAnalytics.logEvent(event.eventName, Bundle())
    }
}
