package giavu.hoangvm.hh.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.activity.quotelist.QuoteListActivity
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.dialog.AlertDialogFragment
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import giavu.hoangvm.hh.exception.ResponseError
import giavu.hoangvm.hh.helper.UserSharePreference
import giavu.hoangvm.hh.model.BackgroundImages
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.tracker.Event
import giavu.hoangvm.hh.tracker.FirebaseTracker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quote.*
import kotlinx.android.synthetic.main.nav_header_menu.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    val viewModel: MainViewModel by inject()
    val userSharePreference: UserSharePreference by inject()
    private val userApi: UserApi by inject()
    private val tracker: FirebaseTracker by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation_view.setNavigationItemSelectedListener(nav)
        initBackground()
        initActionBar()
        initViewModel()
        observeQuote()
    }

    private fun initBackground() {
        val drawableId = BackgroundImages.randomBackground().value
        val textColor = when(drawableId) {
            R.drawable.yellow_pencil -> R.color.white
            R.drawable.green_wall -> R.color.white
            R.drawable.black_road -> R.color.white
            else -> R.color.white
        }
        quote.setTextColor(ContextCompat.getColor(this, textColor))
        quote_main.background = getDrawable(drawableId)
    }

    private fun initMenuHeader() {
        val binding = NavHeaderBinding(
            userNameText = RxTextView.text(username),
            userNameVisibility = RxView.visibility(username),
            emailText = RxTextView.text(email),
            emailVisibility = RxView.visibility(email)
        )
        binding.apply(
            loginResponse = LoginResponse(
                userToken = userSharePreference.getUserSession(),
                login = userSharePreference.getUserName(),
                email = userSharePreference.getEmail()
            )
        )
    }

    private fun observeQuote() {
        viewModel.quote.observe(this, Observer {
            quote.text = it
        })
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            actionBar.title = "DAILY QUOTE"
            setHomeAsUpIndicator(R.drawable.ic_menu_white)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                initMenuHeader()
                openDrawer()
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
            closeDrawer()
            return true
        }
    }

    private fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    private fun openDrawer() {
        drawer_layout.openDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer()
            return
        }
        super.onBackPressed()
    }
}
