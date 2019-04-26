package giavu.co.jp.dialog

import androidx.appcompat.app.AppCompatActivity

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/06
 */

@JvmOverloads
fun AppCompatActivity.showProgress(tag: String? = null) {
    ProgressDialogFragment.Builder().show(supportFragmentManager, tag)
}

@JvmOverloads
fun AppCompatActivity.hideProgress(tag: String? = null) {
    if (tag == null) {
        DialogFactory().dismiss(supportFragmentManager, ProgressDialogFragment::class.java)
    } else {
        DialogFactory().dismiss(supportFragmentManager, tag)
    }
}
/*

private const val TAG_LOADING_FAILED_FRAGMENT = "tag_loading_failed_fragment"

fun AppCompatActivity.addLoadingFailedFragment(@IdRes containerViewId: Int) {
    with(supportFragmentManager) {
        if (findFragmentByTag(TAG_LOADING_FAILED_FRAGMENT) == null) {
            beginTransaction()
                    .add(containerViewId, LoadingFailedFragment.newInstance(), TAG_LOADING_FAILED_FRAGMENT)
                    .commitNow()
        }
    }
}

fun AppCompatActivity.removeLoadingFailedFragment() {
    with(supportFragmentManager) {
        findFragmentByTag(TAG_LOADING_FAILED_FRAGMENT)?.also { fragment ->
            beginTransaction()
                    .remove(fragment)
                    .commitNow()
        }
    }
}*/
