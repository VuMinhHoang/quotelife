package giavu.co.jp.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import giavu.co.jp.R
import giavu.co.jp.utils.StringUtils


class AlertDialogFragment : BaseDialogFragment() {

    private var mMessage: TextView? = null

    private var mCheckBox: CheckBox? = null

    private var mPositiveBtn: TextView? = null

    private var mNegativeBtn: TextView? = null

    private var mNeutralBtn: TextView? = null

    private val layoutResourceId: Int
        get() {
            val defaultLayout = R.layout.dialog_alert

            val args = arguments ?: return defaultLayout

            val requestLayout = args.getInt(LAYOUT_RESOURCE, 0)

            return if (requestLayout == 0) defaultLayout else requestLayout
        }

    private val mOnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.positive_btn -> dismissNotifyResult(Dialog.BUTTON_POSITIVE)
            R.id.neutral_btn -> dismissNotifyResult(Dialog.BUTTON_NEUTRAL)
            R.id.negative_btn -> dismissNotifyResult(Dialog.BUTTON_NEGATIVE)
            else -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_TITLE, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = layoutResourceId
        val view = inflater.inflate(layoutId, container, false)

        mMessage = view.findViewById<View>(R.id.message) as TextView

        mCheckBox = view.findViewById<View>(R.id.alert_checkbox) as CheckBox

        mPositiveBtn = view.findViewById<View>(R.id.positive_btn) as TextView
        mPositiveBtn!!.setOnClickListener(mOnClickListener)

        mNegativeBtn = view.findViewById<View>(R.id.negative_btn) as TextView
        mNegativeBtn!!.setOnClickListener(mOnClickListener)

        mNeutralBtn = view.findViewById<View>(R.id.neutral_btn) as TextView
        mNeutralBtn!!.setOnClickListener(mOnClickListener)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayArguments()
    }

    override fun onArgumentChanged() {
        displayArguments()
    }

    private fun displayArguments() {
        val args = arguments ?: return

        isCancelable = args.getBoolean(CANCELABLE, true)

        setTitle(args.getString(TITLE))
        mMessage!!.text = args.getString(MESSAGE)

        val checkBoxLabel = args.getString(CHECKBOX_LABEL)
        if (!StringUtils.isNullOrEmpty(checkBoxLabel)) {
            mCheckBox!!.text = checkBoxLabel
            mCheckBox!!.visibility = View.VISIBLE
        }

        var hasButton = false

        val positiveText = args.getString(BUTTON_TEXT_POSITIVE)
        if (StringUtils.isNullOrEmpty(positiveText)) {
            mPositiveBtn!!.visibility = View.GONE
        } else {
            mPositiveBtn!!.text = positiveText
            hasButton = true
        }

        val negativeText = args.getString(BUTTON_TEXT_NEGATIVE)
        if (StringUtils.isNullOrEmpty(negativeText)) {
            mNegativeBtn!!.visibility = View.GONE
        } else {
            mNegativeBtn!!.text = negativeText
            mNegativeBtn!!.visibility = View.VISIBLE
            hasButton = true
        }

        val neutralText = args.getString(BUTTON_TEXT_NEUTRAL)
        if (StringUtils.isNullOrEmpty(neutralText)) {
            mNeutralBtn!!.visibility = View.GONE
        } else {
            mNeutralBtn!!.text = neutralText
            mNeutralBtn!!.visibility = View.VISIBLE
            hasButton = true
        }

        if (!hasButton) {
            mPositiveBtn!!.visibility = View.VISIBLE
            mPositiveBtn!!.text = getString(R.string.dialog_ok)
        }
    }

    private fun dismissNotifyResult(whichButton: Int) {
        val data: Intent?

        if (mCheckBox != null && mCheckBox!!.isShown) {
            data = Intent()
            data.putExtra(CHECKBOX_VALUE, mCheckBox!!.isChecked)
        } else {
            data = null
        }

        dismissNotifyResult(whichButton, data)
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        dismissNotifyResult(DialogInterface.BUTTON_NEGATIVE)
    }

    class Builder : BaseDialogFragment.AbsBuilder<Builder, AlertDialogFragment>(AlertDialogFragment::class.java) {

        fun setTitle(title: String): Builder {
            args.putString(TITLE, title)
            return this
        }

        fun setMessage(message: String): Builder {
            args.putString(MESSAGE, message)
            return this
        }

        fun setCheckBoxLabel(checkBoxLabel: String): Builder {
            args.putString(CHECKBOX_LABEL, checkBoxLabel)
            return this
        }

        fun setPositiveButtonText(text: String): Builder {
            args.putString(BUTTON_TEXT_POSITIVE, text)
            return this
        }

        fun setNegativeButtonText(text: String): Builder {
            args.putString(BUTTON_TEXT_NEGATIVE, text)
            return this
        }

        fun setNeutralButtonText(text: String): Builder {
            args.putString(BUTTON_TEXT_NEUTRAL, text)
            return this
        }

        fun setLayoutResourceId(layoutResourceId: Int): Builder {
            args.putInt(LAYOUT_RESOURCE, layoutResourceId)
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            args.putBoolean(CANCELABLE, cancelable)
            return this
        }

    }

    companion object {

        val CHECKBOX_VALUE = "checkbox_value"

        private val TITLE = "title"
        private val MESSAGE = "message"
        private val CHECKBOX_LABEL = "checkbox_label"
        private val BUTTON_TEXT_POSITIVE = "positive_button_text"
        private val BUTTON_TEXT_NEGATIVE = "negative_button_text"
        private val BUTTON_TEXT_NEUTRAL = "neutral_button_text"
        private val LAYOUT_RESOURCE = "layout_resource"
        private val CANCELABLE = "cancelable"
    }

}
