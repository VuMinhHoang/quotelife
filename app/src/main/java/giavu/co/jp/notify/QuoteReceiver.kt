package giavu.co.jp.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class QuoteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("QuoteReceiver", "Received")
        throw UnsupportedOperationException("Not yet implemented")
    }
}
