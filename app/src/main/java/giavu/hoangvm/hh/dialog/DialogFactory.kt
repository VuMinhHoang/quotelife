package giavu.hoangvm.hh.dialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import giavu.hoangvm.hh.exception.ResponseError
import giavu.hoangvm.hh.exception.ResponseSuccessErrorCode

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/06
 */
class DialogFactory {

    fun create(activity: AppCompatActivity, throwable: Throwable): AlertDialogFragment {
        return when (throwable) {
            is ResponseError -> {
                AlertDialogFragment.Builder()
                    .setTitle(throwable.errorCode)
                    .setMessage(throwable.messageError)
                    .setPositiveButtonText("Retry")
                    .show(activity.supportFragmentManager)
            }
            is ResponseSuccessErrorCode -> {
                AlertDialogFragment.Builder()
                    .setTitle(throwable.errorCode)
                    .setMessage(throwable.messageError)
                    .setPositiveButtonText("OK")
                    .show(activity.supportFragmentManager)
            }
            else -> {
                AlertDialogFragment.Builder()
                    .setMessage("Unknown error")
                    .setPositiveButtonText("Retry")
                    .show(activity.supportFragmentManager)
            }
        }
    }


    fun <T : BaseDialogFragment> show(
            manager: FragmentManager, clazz: Class<T>): T {
        return show(manager, clazz, null, null)
    }

    fun <T : BaseDialogFragment> show(
            manager: FragmentManager, clazz: Class<T>, args: Bundle): T {
        return show(manager, clazz, args, null)
    }

    fun <T : BaseDialogFragment> show(
            manager: FragmentManager, clazz: Class<T>, args: Bundle?, tag: String?): T {
        try {
            var fragment = getFragment(manager, clazz)

            if (fragment == null) {
                fragment = clazz.newInstance()
                fragment!!.show(manager, tag ?: getTag(clazz))
            }

            if (args != null) {
                fragment.arguments?.putAll(args)
                fragment.updateChanges()
            }

            return fragment
        } catch (e: InstantiationException) {
            throw IllegalArgumentException("Fragment must have default constructor", e)
        } catch (e: IllegalAccessException) {
            throw IllegalArgumentException("Fragment must have default constructor", e)
        }

    }

    fun <T : BaseDialogFragment> show(
            manager: FragmentManager, dialogFragment: T): T {
        return show(manager, dialogFragment, null)
    }

    fun <T : BaseDialogFragment> show(
            manager: FragmentManager, dialogFragment: T, tag: String?): T {
        val fragment = getFragment(manager, dialogFragment)

        if (fragment != null) {
            fragment.arguments?.putAll(dialogFragment.arguments)
            fragment.updateChanges()
            return fragment
        }

        // allow state loss on fragment commit transaction
        val transaction = manager.beginTransaction()
        transaction.add(dialogFragment, tag ?: getTag(dialogFragment.javaClass))
        transaction.commitAllowingStateLoss()

        return dialogFragment
    }

    fun dismiss(manager: FragmentManager,
                clazz: Class<out BaseDialogFragment>) {
        val fragment = getFragment<BaseDialogFragment>(manager, clazz, false)

        fragment?.dismissAllowingStateLoss()
    }

    fun dismiss(manager: FragmentManager, tagName: String) {
        val fragment = manager.findFragmentByTag(tagName) as BaseDialogFragment
        fragment.dismissAllowingStateLoss()
    }

    private fun <T : BaseDialogFragment> getFragment(
            manager: FragmentManager, dialogFragment: T): T? {
        return getFragment(manager, dialogFragment.javaClass)
    }


    private fun <T : BaseDialogFragment> getFragment(
            manager: FragmentManager, clazz: Class<out T>): T? {
        return getFragment(manager, clazz, true)
    }

    private fun <T : BaseDialogFragment> getFragment(
            manager: FragmentManager, clazz: Class<out T>, flashPendingTransactions: Boolean): T? {
        if (flashPendingTransactions) {
            manager.executePendingTransactions()
        }

        return clazz.cast(manager.findFragmentByTag(getTag(clazz)))
    }

    private fun <T : BaseDialogFragment> getTag(dialogFragment: T): String {
        return getTag(dialogFragment.javaClass)
    }

    private fun getTag(clazz: Class<out BaseDialogFragment>): String {
        return clazz.name
    }
}