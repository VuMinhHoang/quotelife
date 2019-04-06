package giavu.hoangvm.hh.activity.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.activity.main.MainActivity
import giavu.hoangvm.hh.databinding.ActivityRegisterAccountBinding
import giavu.hoangvm.hh.dialog.DialogFactory
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.helper.UserSharePreference
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.utils.SmartLockClient
import org.koin.android.ext.android.inject
import timber.log.Timber

class RegisterAccountActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_SMART_LOCK = 1
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
        initializeViewModel()

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

    private fun initializeViewModel() {
        viewModel.initialize(navigator = navigator)
    }


    private val navigator = object : RegisterAccountNavigator {
        override fun showProgress() {
            this@RegisterAccountActivity.showProgress()
        }

        override fun hideProgress() {
            this@RegisterAccountActivity.hideProgress()
        }

        override fun register(response: LoginResponse) {
            response.userToken?.let {
                if (it.isNotEmpty()) {
                    UserSharePreference.fromContext(this@RegisterAccountActivity).updateUserPref(response)
                    val intent = Intent(this@RegisterAccountActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        override fun toLogin() {
            startActivity(LoginActivity.createIntent(this@RegisterAccountActivity))
        }

        override fun toError(throwable: Throwable) {
            Timber.d(throwable)
            DialogFactory().create(this@RegisterAccountActivity, throwable)
        }
    }
}
