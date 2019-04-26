package giavu.co.jp.activity.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import giavu.co.jp.R
import giavu.co.jp.databinding.ActivityProfileBinding
import giavu.co.jp.dialog.hideProgress
import giavu.co.jp.dialog.showProgress
import org.koin.android.ext.android.inject

class ProfileActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }

    private val viewModel: ProfileViewModel by inject()
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            showProgressRequest.observe(this@ProfileActivity, Observer { showProgress() })
            hideProgressRequest.observe(this@ProfileActivity, Observer { hideProgress() })
        }
    }

    private fun initialize() {
        initializeActionBar()
        initializeDataBinding()
        initializeViewModel()
    }

    private fun initializeViewModel() {
        viewModel.initialize()
    }

    private fun initializeDataBinding() {
        binding = DataBindingUtil.setContentView<ActivityProfileBinding>(
            this@ProfileActivity, R.layout.activity_profile
        )
            .apply {
                viewModel = this@ProfileActivity.viewModel
                setLifecycleOwner(this@ProfileActivity)
            }
    }

    private fun initializeActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            actionBar.title = "QUOTE LIST"
            setHomeAsUpIndicator(R.drawable.ic_clear)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
