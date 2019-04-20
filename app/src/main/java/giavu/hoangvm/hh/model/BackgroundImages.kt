package giavu.hoangvm.hh.model

import giavu.hoangvm.hh.R
import java.util.*

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/21
 */
object BackgroundImages {
    enum class Image(val value: Int) {
        BROWN_PHONE(R.drawable.brown_phone),
        BROWN_WINDOWS(R.drawable.brown_window),
        BLACK_ROAD(R.drawable.black_road),
        GREEN_WALL(R.drawable.green_wall),
        YELLOW_PENCIL(R.drawable.yellow_pencil)
    }

    private val values = Image.values()
    private val random = Random()

    fun randomBackground(): Image {
        return values[random.nextInt(values.size)]
    }
}