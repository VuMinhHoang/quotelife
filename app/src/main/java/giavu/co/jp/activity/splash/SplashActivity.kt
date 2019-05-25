package giavu.co.jp.activity.splash

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import giavu.co.jp.R
import giavu.co.jp.activity.main.MainActivity
import giavu.co.jp.api.UserApi
import giavu.co.jp.dialog.AlertDialogFragment
import giavu.co.jp.dialog.BaseDialogFragment
import giavu.co.jp.exception.ResponseError
import giavu.co.jp.exception.ResponseSuccessErrorCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity(), BaseDialogFragment.OnDialogResult {

    private val userApi: UserApi by inject()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        const val TAG_RETRY_DIALOG = 1
        const val TAG_NOT_RETRY_DIALOG = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initialize()
    }

    private fun initialize() {
        checkLocalData()
    }

    private fun checkLocalData() {
        userApi.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    loadActivity()
                },
                onError = {
                    onError(it)
                }
            )
            .addTo(compositeDisposable = compositeDisposable)
    }

    private fun loadActivity() {
        startActivity(MainActivity.createIntent(this))
        this.finish()
    }

    private fun onError(throwable: Throwable) {
        when (throwable) {
            is ResponseError -> {
                AlertDialogFragment.Builder()
                    .setTitle(throwable.errorCode)
                    .setMessage(throwable.messageError)
                    .setPositiveButtonText("Retry")
                    .show(supportFragmentManager)
                    .setTarget(this, TAG_RETRY_DIALOG)
            }
            is ResponseSuccessErrorCode -> {
                loadActivity()
            }
        }
    }

    override fun onDialogResult(requestCode: Int, whichButton: Int, data: Intent?) {
        when (whichButton) {
            Dialog.BUTTON_POSITIVE -> {
                if (requestCode == TAG_RETRY_DIALOG) {
                    checkLocalData()
                }
            }
            else -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
