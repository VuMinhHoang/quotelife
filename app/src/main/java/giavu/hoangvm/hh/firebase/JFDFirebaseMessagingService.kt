package giavu.hoangvm.hh.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/18
 */
class JFDFirebaseMessagingService : FirebaseMessagingService() {

    private val fcmTokenStore: FcmTokenStore by inject()

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Log.d("Token", "onNewToken")
        fcmTokenStore.token.postValue(p0)

    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        Log.d("Token", "onMessageReceived")
    }
}