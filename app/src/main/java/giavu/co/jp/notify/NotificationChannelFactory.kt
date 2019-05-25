package giavu.co.jp.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.O)
class NotificationChannelFactory(private val context: Context) {

    fun create(channel: NotificationChannels): NotificationChannel = when (channel) {
        NotificationChannels.SERVICE -> {
            NotificationChannel(
                    "CHANEL_ID_SERVICE",
                    "CHANEL_ID_SERVICE_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
        }
    }

    fun createAll(): List<NotificationChannel> = NotificationChannels.values().map { create(it) }

}