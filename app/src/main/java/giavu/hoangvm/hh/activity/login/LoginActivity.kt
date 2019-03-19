package giavu.hoangvm.hh.activity.login

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.Status
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.main.MainActivity
import giavu.hoangvm.hh.activity.register.RegisterAccountActivity
import giavu.hoangvm.hh.databinding.ActivityLoginBinding
import giavu.hoangvm.hh.dialog.AlertDialogFragment
import giavu.hoangvm.hh.dialog.DialogFactory
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.helper.UserSharePreference
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.tracker.Event
import giavu.hoangvm.hh.tracker.FirebaseTracker
import giavu.hoangvm.hh.utils.CredentialResult
import giavu.hoangvm.hh.utils.SmartLockClient
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.android.ext.android.inject


class LoginActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private val TAG = LoginActivity::class.java.simpleName
    private val REQUEST_CODE_SELECT_ACCOUNT = 4
    private val tracker: FirebaseTracker by inject()


    private val compositeDisposable = CompositeDisposable()
    private lateinit var smartLockClient: SmartLockClient

    private val viewModel: LoginViewModel by inject()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        if (savedInstanceState == null) {
            smartLockClient.requestCredential(this, onRequestCredentialListener)
        }
        initializeDataBinding()
        initViewModel()

    }

    private fun initialize() {
        smartLockClient = SmartLockClient(this)
    }

    private fun initViewModel() {
        viewModel.initialize(
            navigator = navigator,
            owner = this@LoginActivity
        )
    }

    private fun saveUserPreference(loginResponse: LoginResponse) {
        UserSharePreference.fromContext(this).updateUserPref(loginResponse)
    }

    private fun initializeDataBinding() {
        binding = DataBindingUtil.setContentView<ActivityLoginBinding>(
            this@LoginActivity, R.layout.activity_login
        )
            .apply {
                viewModel = this@LoginActivity.viewModel
                setLifecycleOwner(this@LoginActivity)
            }

    }

    private val onRequestCredentialListener = object : SmartLockClient.OnRequestCredentialListener {
        override fun shouldResoluteAccountSelect(status: Status) {
            try {
                status.startResolutionForResult(this@LoginActivity, REQUEST_CODE_SELECT_ACCOUNT)
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
            }
        }
    }

    private val navigator = object : LoginNavigator {

        override fun toLogin(response: LoginResponse) {
            if (response.userToken != null) {
                saveUserPreference(response)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                tracker.track(Event.LoginSuccess)

            } else {
                AlertDialogFragment.Builder()
                    .setTitle("Network error")
                    .setMessage("Please check your network connection !")
                    .setPositiveButtonText("OK")
                    .show(supportFragmentManager)
            }
        }

        override fun toRegister() {
            startActivity(RegisterAccountActivity.createIntent(this@LoginActivity))
            finish()
        }

        override fun toShowError(error: Throwable) {
            DialogFactory().create(this@LoginActivity,error)
        }

        override fun showProgress() {
            this@LoginActivity.showProgress()
        }

        override fun hideProgress() {
            this@LoginActivity.hideProgress()
        }
    }

    @Subscribe
    fun onRequestCredentialResult(credentialResult: CredentialResult) {
        viewModel.subscribeCredentialResult(credentialResult = credentialResult)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        smartLockClient.subscribe()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        smartLockClient.unsubscribe()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

}
