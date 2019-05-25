package giavu.co.jp.binding

import android.view.View
import androidx.databinding.BindingAdapter
import giavu.co.jp.extension.setOnProtectBarrageClickListener

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/18
 */

@BindingAdapter("onClick")
fun setClick(view: View, listener: View.OnClickListener) {
    view.setOnProtectBarrageClickListener(listener)
}