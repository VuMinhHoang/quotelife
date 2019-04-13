package giavu.hoangvm.hh.activity.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.activity.main.MainActivity
import giavu.hoangvm.hh.databinding.ActivityRegisterAccountBinding
import giavu.hoangvm.hh.dialog.DialogFactory
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.helper.UserSharePreference
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.utils.Status
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
                    is Status.Success -> gotoMainScreen(state.data)
                    is Status.Failure -> DialogFactory().create(this@RegisterActivity, state.throwable)
                }
            })
        }
    }

    private fun gotoMainScreen(response: LoginResponse) {
        response.userToken?.let {
            if (it.isNotEmpty()) {
                UserSharePreference.fromContext(this@RegisterActivity).updateUserPref(response)
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
