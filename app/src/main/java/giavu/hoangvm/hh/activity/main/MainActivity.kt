package giavu.hoangvm.hh.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.activity.quotelist.QuoteListActivity
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.dialog.AlertDialogFragment
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.exception.ResponseError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quote.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)
    }

    private val userApi: UserApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation_view.setNavigationItemSelectedListener(nav)
        initViewModel()
        initializeActionBar()
        observerQuote()
    }

    private fun observerQuote() {
        viewModel.quote.observe(this, Observer {
            quote.setText(it)
        })
    }


    private fun initializeActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("Quote")
            setHomeAsUpIndicator(R.drawable.ic_menu_white)
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


    private fun initViewModel() {
        viewModel.initialize(navigator = navigator)
    }

    private val navigator = object : MainNavigator {
        override fun showProgress() {
            this@MainActivity.showProgress()
        }

        override fun hideProgress() {
            this@MainActivity.hideProgress()
        }

        override fun toLogout(message: String) {
            Timber.d(message)
        }

        override fun toError(e: Throwable) {
            Timber.d(e)
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
