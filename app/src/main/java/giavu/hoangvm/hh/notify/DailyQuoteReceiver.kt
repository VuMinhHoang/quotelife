package giavu.hoangvm.hh.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DailyQuoteReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        intent.action?.let {action ->
            if (action.equals(Intent.ACTION_BOOT_COMPLETED)){
                val service = Intent(context, NotificationDailyQuoteService::class.java).apply {
                    putExtra("Hello", intent.getStringExtra("Hello"))
                }
                context.startService(service)
            }
        }
    }
}