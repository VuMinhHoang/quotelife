package giavu.hoangvm.hh.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class WakefulReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.let {action ->
            if (action.equals(Intent.ACTION_BOOT_COMPLETED)){
                Timber.d("Boot completed !")
            }
        }

    }
}