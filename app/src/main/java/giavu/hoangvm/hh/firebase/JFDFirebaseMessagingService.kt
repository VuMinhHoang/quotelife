package giavu.hoangvm.hh.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/18
 */
class JFDFirebaseMessagingService: FirebaseMessagingService() {
    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
    }
}