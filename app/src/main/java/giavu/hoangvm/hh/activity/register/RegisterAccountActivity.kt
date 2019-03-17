package giavu.hoangvm.hh.activity.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.databinding.ActivityRegisterAccountBinding
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.model.RegisterResponse
import giavu.hoangvm.hh.utils.SmartLockClient
import kotlinx.android.synthetic.main.activity_register_account.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class RegisterAccountActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_SMART_LOCK = 1
        fun createIntent(context: Context): Intent {
            return Intent(context, RegisterAccountActivity::class.java)
        }
    }

    private lateinit var smartLockClient: SmartLockClient
    private val viewModel: RegisterAccountViewModel by inject()
    private lateinit var dataBinding: ActivityRegisterAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        smartLockClient = SmartLockClient(this)
        initializeDataBinding()
        initializeViewModel()

    }

    private fun initializeDataBinding() {
        dataBinding = DataBindingUtil.setContentView<ActivityRegisterAccountBinding>(
                this, R.layout.activity_register_account)
                .apply {
                    viewModel = this@RegisterAccountActivity.viewModel
                    setLifecycleOwner(this@RegisterAccountActivity)
                }

    }

    private fun initializeViewModel() {
        viewModel.initialize(
                navigator = navigator,
                owner = this@RegisterAccountActivity
        )
    }


    private val navigator = object : RegisterAccountNavigator {
        override fun showProgress() {
            this@RegisterAccountActivity.showProgress()
        }

        override fun hideProgress() {
            this@RegisterAccountActivity.hideProgress()
        }

        override fun register(response: RegisterResponse) {
            response.userToken?.let {
                if (it.isNotEmpty()) {
                    Log.d("Register", "Done")
                }
            }
        }

        override fun toLogin() {
            startActivity(LoginActivity.createIntent(this@RegisterAccountActivity))
        }

        override fun toError(throwable: Throwable) {
            Timber.d(throwable)
        }
    }

    private fun onCompleteRegisterAccount() {

        smartLockClient.saveCredential(
                this,
                email.text.toString(),
                password.text.toString(),
                REQUEST_CODE_SMART_LOCK,
                object : SmartLockClient.OnSaveSmartLockListener {
                    override fun onSuccess() {

                    }

                    override fun onFailure() {

                    }
                })
    }
}
