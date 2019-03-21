package giavu.hoangvm.hh.activity.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.activity.main.MainActivity
import giavu.hoangvm.hh.databinding.ActivityRegisterAccountBinding
import giavu.hoangvm.hh.dialog.DialogFactory
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.helper.UserSharePreference
import giavu.hoangvm.hh.model.LoginResponse
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*

class RegisterAccountActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, RegisterAccountActivity::class.java)
        }
    }

    private val mode: Boolean by lazy {
        when(Locale.getDefault().language) {
            "en" -> true
            else -> false
        }
    }

    private val viewModel: RegisterAccountViewModel by inject()
    private lateinit var dataBinding: ActivityRegisterAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        initializeDataBinding()
        initializeViewModel()
        initFragment()

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

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.register_container,
                RequireRegisterFragment()
            ).commitNow()
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
                    if(mode) {
                        replaceFragment()
                    }else{
                        val intent = Intent(this@RegisterAccountActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
            }
        }


        override fun toLogin() {
            startActivity(LoginActivity.createIntent(this@RegisterAccountActivity))
        }

        override fun toError(throwable: Throwable) {
            Timber.d(throwable)
            DialogFactory().create(this@RegisterAccountActivity,throwable)
        }
    }
    private fun replaceFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.register_container, OptionalRegisterFragment())
        fragmentTransaction.commit()
    }
}
