package giavu.co.jp.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

/**
 * @Author: Hoang Vu
 * @Date:   2019/03/21
 */
class FixedBgColorTextInputLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): TextInputLayout(context, attrs, defStyleAttr) {
    override fun setError(errorText: CharSequence?) {
        super.setError(errorText)
        this.editText?.background?.clearColorFilter()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        this.editText?.background?.clearColorFilter()
    }


}