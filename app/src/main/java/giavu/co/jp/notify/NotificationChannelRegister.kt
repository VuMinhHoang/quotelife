package giavu.co.jp.notify

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.O)
class NotificationChannelRegister(private val context: Context) {

    private val manager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun registerAll() {
        manager.createNotificationChannels(
                NotificationChannelFactory(context).createAll()
        )
    }

}