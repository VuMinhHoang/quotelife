package giavu.hoangvm.hh.model

import giavu.hoangvm.hh.R
import java.util.*

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/21
 */
object BackgroundImages {
    enum class Image(val value: Int) {
        YELLOW(R.drawable.enum_yellow),
        BROWN(R.drawable.enum_brown),
        BLACK(R.drawable.enum_black),
        GREEN(R.drawable.enum_green),
        PURPLE(R.drawable.enum_purple)
    }

    private val values = Image.values()
    private val random = Random()

    fun randomBackground(): Image {
        return values[random.nextInt(values.size)]
    }
}