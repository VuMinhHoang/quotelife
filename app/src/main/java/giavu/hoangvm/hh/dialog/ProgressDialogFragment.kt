package giavu.hoangvm.hh.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.DialogFragment
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.utils.StringUtils

class ProgressDialogFragment : BaseDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_TITLE, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawableResource(R.color.colorTransparent)
        dialog?.setCanceledOnTouchOutside(false)
        val args = arguments
        if (args != null) {
            isCancelable = args.getBoolean(CANCELABLE, true)
        }
        return inflater.inflate(R.layout.dialog_progress, container, false)
    }

    override fun onArgumentChanged() {
        displayArguments()
    }

    private fun displayArguments() {
        val args = arguments ?: return

        val networkRequestTag = args.getString(NETWORK_REQUEST_TAG)

        if (!StringUtils.isNullOrEmpty(networkRequestTag)) {
            dialog?.setOnCancelListener { dismissNotifyResult(DialogInterface.BUTTON_NEGATIVE, null, false) }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismissNotifyResult(DialogInterface.BUTTON_NEGATIVE, null, false)
    }

    class Builder : BaseDialogFragment.AbsBuilder<Builder, ProgressDialogFragment>(ProgressDialogFragment::class.java) {

        fun setCancelable(cancelable: Boolean): Builder {
            args.putBoolean(CANCELABLE, cancelable)
            return this
        }
    }

    companion object {

        val CANCELABLE = "cancelable"

        val NETWORK_REQUEST_TAG = "network_request_tag"
    }
}
