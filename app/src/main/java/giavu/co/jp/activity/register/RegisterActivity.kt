package giavu.co.jp.activity.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import giavu.co.jp.R
import giavu.co.jp.activity.login.LoginActivity
import giavu.co.jp.activity.main.MainActivity
import giavu.co.jp.databinding.ActivityRegisterAccountBinding
import giavu.co.jp.dialog.DialogFactory
import giavu.co.jp.dialog.hideProgress
import giavu.co.jp.dialog.showProgress
import giavu.co.jp.utils.Status
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    private val viewModel: RegisterViewModel by inject()
    private lateinit var dataBinding: ActivityRegisterAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        initializeDataBinding()
        observeViewModel()
    }

    private fun initializeDataBinding() {
        dataBinding = DataBindingUtil.setContentView<ActivityRegisterAccountBinding>(
            this, R.layout.activity_register_account
        )
            .apply {
                viewModel = this@RegisterActivity.viewModel
                setLifecycleOwner(this@RegisterActivity)
            }

    }

    private fun observeViewModel() {
        with(viewModel) {
            showProgressRequest.observe(this@RegisterActivity, Observer { showProgress() })
            hideProgressRequest.observe(this@RegisterActivity, Observer { hideProgress() })
            gotoLogin.observe(this@RegisterActivity, Observer {
                startActivity(LoginActivity.createIntent(this@RegisterActivity))
            })
            status.observe(this@RegisterActivity, Observer { state ->
                when (state) {
                    is Status.Success -> gotoMainScreen()
                    is Status.Failure -> DialogFactory().create(this@RegisterActivity, state.throwable)
                }
            })
        }
    }

    private fun gotoMainScreen() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
