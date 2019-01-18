package giavu.hoangvm.hh.activity.login

import android.text.TextUtils
import android.widget.EditText
import androidx.databinding.BindingAdapter

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/16
 */

@BindingAdapter("passwordValidator")
fun passwordValidator(editText: EditText, password: String) {
    if (TextUtils.isEmpty(password)) {
        editText.setError(null)
        return
    }
    if (password.length < 5) {
        editText.setError("Password must be minimum 5")
    } else {
        editText.setError(null)
    }
}