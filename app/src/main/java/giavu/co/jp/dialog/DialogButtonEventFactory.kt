package giavu.co.jp.dialog

import android.content.Intent
import java.io.Serializable

/**
 * Created by johnbaligod.
 */
interface DialogButtonEventFactory<T : DialogButtonEvent> : Serializable {

    // TODO change Intent type to Bundle type
    fun createEvent(whichButton: Int, data: Intent): T
}
