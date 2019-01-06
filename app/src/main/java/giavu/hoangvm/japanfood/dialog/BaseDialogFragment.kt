package giavu.hoangvm.japanfood.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import giavu.hoangvm.japanfood.R
import giavu.hoangvm.japanfood.utils.StringUtils
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/06
 */
open class BaseDialogFragment : DialogFragment(), ArgumentChangedListener {

    protected var mOnResultListener: OnDialogResult? = null

    @Nullable
    protected var viewModel: DialogViewModel? = null

    private var mTitlePanel: View? = null

    private var mTitle: TextView? = null

    private var mBack: View? = null

    protected val titleHeight: Int
        get() = if (mTitlePanel == null) {
            0
        } else mTitlePanel!!.height

    init {
        // Set an empty argument to be used later
        arguments = Bundle()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnDialogResult) {
            mOnResultListener = context
        }

        val activity = activity
        val tag = tag
        if (activity != null && tag != null) {
            viewModel = ViewModelProviders.of(activity).get(tag, DialogViewModel::class.java)
        }
    }

    override fun onDetach() {
        super.onDetach()

        mOnResultListener = null
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTitlePanel = view.findViewById(R.id.dialog_title_panel)

        mTitle = view.findViewById(R.id.dialog_title)

        val close = view.findViewById<View>(R.id.dialog_title_close)
        close?.setOnClickListener { dismissNotifyResult(Dialog.BUTTON_NEGATIVE, null) }

        mBack = view.findViewById(R.id.dialog_title_back)
        if (mBack != null) {
            mBack!!.setOnClickListener { onBackClicked() }
        }
    }

    override fun setTargetFragment(fragment: Fragment?, requestCode: Int) {
        // Passed fragment must be an instance of OnDialogResult
        // so result could be returned to the calling fragment
        if (fragment != null && fragment !is OnDialogResult) {
            throw IllegalArgumentException("Fragment must implement OnDialogResult")
        }

        super.setTargetFragment(fragment, requestCode)
    }

    override fun updateChanges() {
        if (!isDetached && activity != null) {
            onArgumentChanged()
        }
    }

    override fun onArgumentChanged() {

    }

    fun setTarget(onDialogResult: OnDialogResult, requestCode: Int) {
        mOnResultListener = onDialogResult
        setTargetFragment(null, requestCode)
    }

    protected fun setTitle(resId: Int) {
        if (mTitle == null) {
            return
        }

        mTitle!!.setText(resId)
    }

    protected fun setTitle(title: String) {
        if (mTitle == null) {
            return
        }

        if (StringUtils.isNullOrEmpty(title)) {
            mTitle!!.visibility = View.GONE
            return
        }

        mTitle!!.text = title
        mTitle!!.minEms = title.length
    }

    protected fun showTitleBackButton(show: Boolean) {
        if (mBack == null) {
            return
        }

        mBack!!.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    protected open fun onBackClicked() {

    }

    protected fun dismissNotifyResult(whichButton: Int, data: Intent?) {
        dismissNotifyResult(whichButton, data, true)
    }

    protected open fun dismissNotifyResult(whichButton: Int, data: Intent?, dismiss: Boolean) {
        if (dismiss) {
            dismiss()
        }

        if (viewModel != null && viewModel!!.result.hasObservers()) {
            val action: DialogViewModel.Action?
            when (whichButton) {
                Dialog.BUTTON_POSITIVE -> action = DialogViewModel.Action.POSITIVE
                Dialog.BUTTON_NEUTRAL -> action = DialogViewModel.Action.NEUTRAL
                Dialog.BUTTON_NEGATIVE -> action = DialogViewModel.Action.NEGATIVE
                else -> action = null
            }
            val result = DialogViewModel.Result(action!!, data)
            viewModel!!.result.postValue(result)

        }
        try {
            val eventFactory = arguments?.getSerializable(BUTTON_EVENT_FACTORY) as DialogButtonEventFactory<*>
            appendExtraData(data)?.let { itent ->
                val event = eventFactory.createEvent(whichButton, itent)
                EventBus.getDefault().post(event)
                return
            }
        }catch (e: Throwable){
            throw e
        }
        // Fragment listener passed to setTargetFragment() will take priority
        // over activity or context listener
        if (targetFragment is OnDialogResult) {
            mOnResultListener = targetFragment as OnDialogResult
        }

        if (mOnResultListener == null) {
            return
        }

        mOnResultListener!!.onDialogResult(targetRequestCode, whichButton, appendExtraData(data))
    }

    protected fun appendExtraData(data: Intent?): Intent? {
        var dataValue = data
        val args = arguments

        if (args == null || !args.containsKey(EXTRA_DATA)) {
            return dataValue
        }

        if (dataValue == null) {
            dataValue = Intent()
        }

        dataValue.putExtra(EXTRA_DATA, args.getBundle(EXTRA_DATA))

        return dataValue
    }

    abstract class AbsBuilder<T : AbsBuilder<T, E>, E : BaseDialogFragment>(private val type: Class<E>) {

        protected val args: Bundle

        init {
            args = Bundle()
        }

        fun setExtraData(extraData: Bundle): T {
            args.putBundle(EXTRA_DATA, extraData)
            return this as T
        }

        fun setButtonEventFactory(eventFactory: DialogButtonEventFactory<*>): T {
            args.putSerializable(BUTTON_EVENT_FACTORY, eventFactory)
            return this as T
        }

        fun show(manager: FragmentManager): E {
            return DialogFactory().show(manager, create())
        }

        fun show(manager: FragmentManager, tag: String?): E {
            return DialogFactory().show(manager, create(), tag)
        }

        fun create(): E {
            try {
                val fragment = type.newInstance()
                fragment.arguments = args
                return fragment
            } catch (e: Exception) {
                throw IllegalArgumentException("Fragment must have default constructor", e)
            }

        }

    }

    interface OnDialogResult : EventListener {

        fun onDialogResult(requestCode: Int, whichButton: Int, data: Intent?)

    }

    companion object {

        val BUTTON_EVENT_FACTORY = "button_event_factory"

        val EXTRA_DATA = "extra_data"

        fun getExtra(data: Intent): Bundle {
            return data.getBundleExtra(EXTRA_DATA)
        }
    }

}
