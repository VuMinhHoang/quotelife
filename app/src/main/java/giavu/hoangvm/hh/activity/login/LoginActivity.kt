package giavu.hoangvm.hh.activity.login

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.Status
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.dailyquote.QuoteActivity
import giavu.hoangvm.hh.activity.register.RegisterAccountActivity
import giavu.hoangvm.hh.api.QuotesApi
import giavu.hoangvm.hh.core.retrofit.JFDApiAccessor
import giavu.hoangvm.hh.databinding.ActivityLoginBinding
import giavu.hoangvm.hh.dialog.AlertDialogFragment
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.exception.ResponseError
import giavu.hoangvm.hh.helper.UserSharePreference
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.usecase.CategoryUseCase
import giavu.hoangvm.hh.utils.CredentialResult
import giavu.hoangvm.hh.utils.SmartLockClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
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


    private val categoryUseCase: CategoryUseCase by inject()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var smartLockClient: SmartLockClient

    /*    val viewModel : LoginViewModel by lazy {
            ViewModelProviders.of(this@LoginActivity).get(LoginViewModel::class.java)
        }*/
    private val viewModel: LoginViewModel by inject()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
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
        viewModel.apply(
                navigator = navigator
        )
    }

    private fun saveUserPreference(loginResponse: LoginResponse) {
        UserSharePreference.fromContext(this).updateUserPref(loginResponse)
    }

    private fun getQuoteOfDay() {
        JFDApiAccessor(this@LoginActivity).from().using(QuotesApi::class.java)
                .getQuoteOfDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Log.d("Test Retrofit", it.toString())
                        },
                        onError = { Log.d("Test Retrofit", it.toString()) }
                )
                .addTo(compositeDisposable)
    }


    private fun fetchQuotes() {
        JFDApiAccessor(this@LoginActivity).from().using(QuotesApi::class.java)
                .getQuotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Log.d("Test Retrofit", it.toString())
                        },
                        onError = { Log.d("Test Retrofit", it.toString()) }
                )
                .addTo(compositeDisposable)
    }

    private fun initializeDataBinding() {
        binding = DataBindingUtil.setContentView<ActivityLoginBinding>(
                this@LoginActivity, R.layout.activity_login)
                .apply {
                    viewModel = this@LoginActivity.viewModel
                    setLifecycleOwner(this@LoginActivity)
                }

    }

    private val navigator = object : LoginNavigator {
        override fun toLogin(response: LoginResponse) {
            if (response.userToken != null) {
                UserSharePreference.fromContext(this@LoginActivity).updateUserPref(response)
                Log.d(TAG, "Login")
                val intent = Intent(this@LoginActivity, QuoteActivity::class.java)
                startActivity(intent)
            } else {
                startActivity(RegisterAccountActivity.createIntent(this@LoginActivity))
            }
        }

        override fun toShowError(error: ResponseError) {
            AlertDialogFragment.Builder()
                    .setTitle(error.errorCode)
                    .setMessage(error.messageError)
                    .setPositiveButtonText("OK")
                    .show(supportFragmentManager)
        }

        override fun showProgress() {
            Log.d(TAG, "showProgress")
            this@LoginActivity.showProgress()
        }

        override fun hideProgress() {
            Log.d(TAG, "hideProgress")
            this@LoginActivity.hideProgress()
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
