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
import giavu.hoangvm.hh.utils.State
import org.koin.android.ext.android.inject

class RegisterAccountActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, RegisterAccountActivity::class.java)
        }
    }

    private val viewModel: RegisterAccountViewModel by inject()
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
                viewModel = this@RegisterAccountActivity.viewModel
                setLifecycleOwner(this@RegisterAccountActivity)
            }

    }

    private fun observeViewModel() {
        with(viewModel) {
            showProgressRequest.observe(this@RegisterAccountActivity, Observer { showProgress() })
            hideProgressRequest.observe(this@RegisterAccountActivity, Observer { hideProgress() })
            gotoLogin.observe(this@RegisterAccountActivity, Observer {
                startActivity(LoginActivity.createIntent(this@RegisterAccountActivity))
            })
            state.observe(this@RegisterAccountActivity, Observer { state ->
                when (state) {
                    is State.Success -> gotoMainScreen(state.data)
                    is State.Failure -> DialogFactory().create(this@RegisterAccountActivity, state.throwable)
                }
            })
        }
    }

    private fun gotoMainScreen(response: LoginResponse) {
        response.userToken?.let {
            if (it.isNotEmpty()) {
                UserSharePreference.fromContext(this@RegisterAccountActivity).updateUserPref(response)
                val intent = Intent(this@RegisterAccountActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
