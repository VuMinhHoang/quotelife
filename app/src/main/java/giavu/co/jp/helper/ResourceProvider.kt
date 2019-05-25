package giavu.co.jp.helper

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String

    fun getColor(@ColorRes color: Int): Int

    fun getDimensionPixelSize(@DimenRes resId: Int): Int
}
