package giavu.hoangvm.hh.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.activity.quotelist.QuoteListActivity
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.databinding.ActivityQuoteBinding
import giavu.hoangvm.hh.dialog.AlertDialogFragment
import giavu.hoangvm.hh.dialog.DialogFactory
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.exception.ResponseError
import giavu.hoangvm.hh.tracker.Event
import giavu.hoangvm.hh.tracker.FirebaseTracker
import giavu.hoangvm.hh.utils.ResultState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    val viewModel: MainViewModel by inject()
    private lateinit var viewDataBinding: ActivityQuoteBinding

    private val userApi: UserApi by inject()
    private val tracker: FirebaseTracker by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation_view.setNavigationItemSelectedListener(nav)
        initialize()
        observeViewModel()
    }

    private fun initialize() {
        initializeActionBar()
        initDataBinding()
        initViewModel()
    }

    private fun initDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView<ActivityQuoteBinding>(
            this, R.layout.activity_quote
        ).apply {
            viewModel = this@MainActivity.viewModel
            setLifecycleOwner(this@MainActivity)
        }
    }

    private fun initViewModel() {
        viewModel.initialize()
    }

    private fun initializeActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Quote"
            setHomeAsUpIndicator(R.drawable.ic_menu_white)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            showProgressRequest.observe(this@MainActivity, Observer { showProgress() })
            hideProgressRequest.observe(this@MainActivity, Observer { hideProgress() })
            resultRequest.observe(this@MainActivity, Observer { state ->
                when (state) {
                    is ResultState.Success -> { }
                    is ResultState.Failure -> DialogFactory().create(this@MainActivity, state.throwable)
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                Timber.d("Menu is click")
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val nav = object : NavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.nav_account -> {
                    Timber.d("Open profile screen")
                }
                R.id.nav_dailyquote -> {
                    Timber.d("Daily quote")
                    startActivity(QuoteListActivity.createIntent(this@MainActivity))
                }
                R.id.nav_setting -> {
                    Timber.d("Setting")
                }
                R.id.nav_logout -> {
                    Timber.d("Logout")
                    tracker.track(Event.TapLogout)
                    userApi.logout()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { showProgress() }
                        .doFinally { hideProgress() }
                        .subscribeBy(
                            onSuccess = {
                                startActivity(LoginActivity.createIntent(this@MainActivity))
                                this@MainActivity.finish()
                            },
                            onError = { error ->
                                if (error is ResponseError) {
                                    AlertDialogFragment.Builder()
                                        .setTitle(error.errorCode)
                                        .setMessage(error.messageError)
                                        .setPositiveButtonText("OK")
                                        .show(supportFragmentManager)
                                }
                            })
                }
            }
            return true
        }
    }

}
