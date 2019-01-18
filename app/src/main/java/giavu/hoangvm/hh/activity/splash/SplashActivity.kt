package giavu.hoangvm.hh.activity.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.MainActivity
import giavu.hoangvm.hh.usecase.CategoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val categoryUseCase: CategoryUseCase by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        //actionBar?.hide()
        //animation_view.playAnimation()
        initialize()
    }

    private fun initialize() {
        // Temporary implement
        checkLocalData()
    }

    private fun checkLocalData() {
        categoryUseCase.getCategory()
                .subscribeOn(Schedulers.io())
                .delay(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { listCategory ->
                            Timber.d("Everything done:%s", listCategory.size.toString())
                            loadActivity()
                        },
                        onError = Timber::w
                )
    }

    private fun loadActivity() {
        startActivity(MainActivity.createIntent(this@SplashActivity))
        this@SplashActivity.finish()
    }
}
