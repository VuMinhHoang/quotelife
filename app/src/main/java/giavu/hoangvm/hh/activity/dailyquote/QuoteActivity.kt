package giavu.hoangvm.hh.activity.dailyquote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.databinding.ActivityQuoteBinding
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import timber.log.Timber

class QuoteActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, QuoteActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDataBinding()
        initViewModel()
        initializeActionBar()
    }

    private fun initializeActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            actionBar.setTitle("Quote")
        }
    }

    val viewModel: QuoteViewModel by lazy {
        ViewModelProviders.of(this@QuoteActivity).get(QuoteViewModel::class.java)
    }
    private lateinit var binding: ActivityQuoteBinding

    private fun initViewModel() {
        viewModel.initialize(navigator = navigator)
    }

    private fun initializeDataBinding() {
        binding = DataBindingUtil.setContentView<ActivityQuoteBinding>(
                this@QuoteActivity, R.layout.activity_quote)
                .apply {
                    viewModel = this@QuoteActivity.viewModel
                    setLifecycleOwner(this@QuoteActivity)
                }

    }

    private val navigator = object : QuoteNavigator {
        override fun showProgress() {
            this@QuoteActivity.showProgress()
        }

        override fun hideProgress() {
            this@QuoteActivity.hideProgress()
        }

        override fun toLogout(message: String) {
            Timber.d(message)
        }

        override fun toError(e: Throwable) {
            Timber.d(e)
        }
    }
}
