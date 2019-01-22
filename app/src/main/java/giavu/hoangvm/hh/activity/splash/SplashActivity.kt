package giavu.hoangvm.hh.activity.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.activity.login.LoginActivity
import giavu.hoangvm.hh.usecase.CategoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
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
        testing()
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
        startActivity(LoginActivity.createIntent(this@SplashActivity))
        this@SplashActivity.finish()
    }

    private fun testing() {
        val request = Request.Builder()
        try {
            val httpUrl = HttpUrl.parse("http://pic1.nipic.com|/2008-12-30/200812308231244_2.jpg")?.uri()
            httpUrl?.let {
                request.url(it.toURL())
            }
        }catch (e: Exception) {
            Log.d("Testing", e.toString())
            return
        }


        val client = OkHttpClient.Builder().build()

        client.newCall(request.build()).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Testing", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Testing", response.body().toString())
            }
        })
    }
}
