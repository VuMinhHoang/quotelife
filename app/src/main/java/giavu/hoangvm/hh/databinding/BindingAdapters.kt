package giavu.hoangvm.hh.databinding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

/**
 * @Author: Hoang Vu
 * @Date:   2019/03/21
 */
@BindingAdapter("app:errorText")
fun setErrorText(view: TextInputLayout, text: String?) {
    view.error = text
}