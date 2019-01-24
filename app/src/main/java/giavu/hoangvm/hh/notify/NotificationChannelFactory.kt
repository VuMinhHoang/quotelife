package giavu.hoangvm.hh.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import jp.co.nikko_data.japantaxi.R

@RequiresApi(api = Build.VERSION_CODES.O)
class NotificationChannelFactory(private val context: Context) {

    fun create(channel: NotificationChannels): NotificationChannel = when (channel) {
        NotificationChannels.SERVICE -> {
            NotificationChannel(
                    context.getString(R.string.notification_channel_id_service),
                    context.getString(R.string.notification_channel_name_service),
                    NotificationManager.IMPORTANCE_DEFAULT
            )
        }
        NotificationChannels.DISPATCH -> {
            NotificationChannel(
                    context.getString(R.string.notification_channel_id_dispatch),
                    context.getString(R.string.notification_channel_name_dispatch),
                    NotificationManager.IMPORTANCE_HIGH
            )
        }
    }

    fun createAll(): List<NotificationChannel> = NotificationChannels.values().map { create(it) }

}