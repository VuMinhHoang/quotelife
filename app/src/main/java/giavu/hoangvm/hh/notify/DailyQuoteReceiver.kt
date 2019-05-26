package giavu.hoangvm.hh.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import giavu.hoangvm.hh.R

class DailyQuoteReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let {action ->
            if (action.equals(Intent.ACTION_BOOT_COMPLETED)){
                Log.d("DailyQuote", "Boot completed")
                val service = Intent(context, NotificationDailyQuoteService::class.java).apply {
                    putExtra(NotificationDailyQuoteService.BOOT_COMPLETED, intent.getStringExtra(context.getString(R.string.boot_completed)))
                }
                context.startService(service)
            }
        }
    }
}