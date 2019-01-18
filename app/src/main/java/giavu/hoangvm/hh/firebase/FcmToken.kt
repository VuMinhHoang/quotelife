package giavu.hoangvm.hh.firebase

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.lang.IllegalStateException

object FcmToken {
    fun get(): Single<String> {
        return Single.create<String> { emitter: SingleEmitter<String> ->
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult: InstanceIdResult? ->
                if( emitter.isDisposed) {
                   return@addOnSuccessListener
                }
                val token = instanceIdResult?.token
                if (token != null) {
                    emitter.onSuccess(token)
                }else {
                    emitter.onError(IllegalStateException("Can not get FCM token"))
                }
            }

        }
    }
}