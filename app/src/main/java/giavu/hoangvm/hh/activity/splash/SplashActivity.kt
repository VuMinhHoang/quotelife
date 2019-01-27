package giavu.hoangvm.hh.activity.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.dailyquote.QuoteActivity
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.api.UserApi
import giavu.hoangvm.hh.helper.UserSharePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val userApi: UserApi by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        initialize()
    }

    private fun initialize() {
        checkLocalData()
    }

    private fun checkLocalData() {
        val userSession = UserSharePreference.fromContext(this@SplashActivity)
                .getUserSession()

        val email = UserSharePreference.fromContext(this@SplashActivity)
                .getUserEmail()

        if (userSession.isEmpty()) {
            loadActivity(isLogined = false)
            return
        }
        userApi.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { response ->
                            if (response.account_details.email == email) {
                                loadActivity(true)
                            } else {
                                loadActivity(false)
                            }
                        },
                        onError = {
                            loadActivity(false)
                        }
                )
                .addTo(compositeDisposable = compositeDisposable)
    }

    private fun loadActivity(isLogined: Boolean) {
        if (isLogined) {
            startActivity(QuoteActivity.createIntent(this@SplashActivity))
            this@SplashActivity.finish()
        } else {
            startActivity(LoginActivity.createIntent(this@SplashActivity))
            this@SplashActivity.finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
