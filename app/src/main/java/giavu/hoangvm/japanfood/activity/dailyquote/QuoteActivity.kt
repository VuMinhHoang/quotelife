package giavu.hoangvm.japanfood.activity.dailyquote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import giavu.hoangvm.japanfood.R
import giavu.hoangvm.japanfood.databinding.ActivityQuoteBinding

class QuoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDataBinding()
        initViewModel()
    }
    val viewModel : QuoteViewModel by lazy {
        ViewModelProviders.of(this@QuoteActivity).get(QuoteViewModel::class.java)
    }
    private lateinit var binding: ActivityQuoteBinding

    private fun initViewModel(){
        viewModel.initialize()
    }
    private fun initializeDataBinding(){
        binding = DataBindingUtil.setContentView<ActivityQuoteBinding>(
                this@QuoteActivity, R.layout.activity_quote)
                .apply {
                    viewModel = this@QuoteActivity.viewModel
                    setLifecycleOwner(this@QuoteActivity)
                }

    }
}
