package giavu.hoangvm.hh.activity.login

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.api.Status
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.dailyquote.QuoteActivity
import giavu.hoangvm.hh.api.QuotesApi
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.core.retrofit.JFDApiAccessor
import giavu.hoangvm.hh.databinding.ActivityMainBinding
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.model.Category
import giavu.hoangvm.hh.model.LoginBody
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.model.User
import giavu.hoangvm.hh.usecase.CategoryUseCase
import giavu.hoangvm.hh.utils.SmartLockClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(){

    private val TAG = MainActivity::class.java.simpleName
    private val REQUEST_CODE_SELECT_ACCOUNT = 4


    private val categoryUseCase: CategoryUseCase by inject()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var smartLockClient : SmartLockClient

    val viewModel : MainViewModel by lazy {
        ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)
    }


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        initialize()
        if (savedInstanceState == null) {
            smartLockClient.requestCredential(this, onRequestCredentialListener)
        }
        initializeDataBinding()
        initViewModel()
        Log.d("Print", "Call oncreate")
        //fetchCategoris()
        //fetchQuotes()
        //getQuoteOfDay()
        login()
    }

    private fun initialize(){
        smartLockClient = SmartLockClient(this)
    }

    private fun initViewModel(){
        viewModel.apply(
                navigator = navigator
        )
    }


    private fun login() {
        val loginBody = LoginBody(
                email = "hoangvmh@gmail.com",
                password = "Hoanghuong2012"
        )
        val body = User(
                user = loginBody
        )
        JFDApiAccessor(this@MainActivity).from().using(UserApi::class.java)
                .login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Log.d("Test Retrofit", it.toString())
                        },
                        onError = {Log.d("Test Retrofit", it.toString())}
                )
                .addTo(compositeDisposable)
    }

    private fun getQuoteOfDay(){
        JFDApiAccessor(this@MainActivity).from().using(QuotesApi::class.java)
                .getQuoteOfDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Log.d("Test Retrofit", it.toString())
                        },
                        onError = {Log.d("Test Retrofit", it.toString())}
                )
                .addTo(compositeDisposable)
    }

    private fun fetchQuotes(){
        JFDApiAccessor(this@MainActivity).from().using(QuotesApi::class.java)
                .getQuotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Log.d("Test Retrofit", it.toString())
                        },
                        onError = {Log.d("Test Retrofit", it.toString())}
                )
                .addTo(compositeDisposable)
    }

    private fun initializeDataBinding(){
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this@MainActivity, R.layout.activity_main)
                .apply {
                    viewModel = this@MainActivity.viewModel
                    setLifecycleOwner(this@MainActivity)
                }

    }
    private fun onSuccess(result: List<Category>) {
        Log.d("Print string:%s", result.toString())
    }

    private fun fetchCategoris(){
        categoryUseCase.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    onSuccess(it)
                })
                .addTo(compositeDisposable)
    }

    private val navigator = object : LoginNavigator{
        override fun toLogin(response: LoginResponse) {
            if(response.userToken != null){
                Log.d(TAG, "Login")
                val intent = Intent(this@MainActivity, QuoteActivity::class.java)
                //startActivity(intent)
            }
        }

        override fun showProgress() {
            //this@MainActivity.showProgress()
            Log.d(TAG, "showProgress")
            this@MainActivity.showProgress()
        }

        override fun hideProgress() {
            //this@MainActivity.hideProgress()
            Log.d(TAG, "hideProgress")
            this@MainActivity.hideProgress()
        }
    }

    private val onRequestCredentialListener = object : SmartLockClient.OnRequestCredentialListener {
        override fun shouldResoluteAccountSelect(status: Status) {
            try {
                status.startResolutionForResult(this@MainActivity, REQUEST_CODE_SELECT_ACCOUNT)
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        smartLockClient.subscribe()
    }

    override fun onStop() {
        super.onStop()
        smartLockClient.unsubscribe()
    }
}
