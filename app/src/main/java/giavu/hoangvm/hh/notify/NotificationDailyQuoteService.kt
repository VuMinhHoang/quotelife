package giavu.hoangvm.hh.notify

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import giavu.hoangvm.hh.R
import java.util.*

class NotificationDailyQuoteService : IntentService("NotificationDailyQuoteService") {

    companion object {
        val BOOT_COMPLETED = "boot_completed"
    }

    override fun onHandleIntent(intent: Intent?) {

        val data = intent?.extras?.getString(BOOT_COMPLETED)
        data?.let {
            if(it == this.getString(R.string.boot_completed)){
                setAlarm()
            }
        }
    }

    private fun setAlarm() {
        val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance().apply {
            set(Calendar.SECOND, 10)
        }
        alarmMgr.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                PendingIntent.getBroadcast(
                        this,
                        0,
                        Intent(this, QuoteReceiver::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT
                )
        )

    }
}