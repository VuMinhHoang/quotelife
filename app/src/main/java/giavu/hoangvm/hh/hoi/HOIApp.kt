package giavu.hoangvm.hh.hoi

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import giavu.hoangvm.hh.firebase.FcmToken
import giavu.hoangvm.hh.firebase.FcmTokenStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
open class HOIApp : Application() {

    private val fcmTokenStore: FcmTokenStore by inject()

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(this).initialize()
        FirebaseApp.initializeApp(this)
        //val intent = Intent(applicationContext, MainActivity::class.java)
        //startActivity(intent)
        initFcm()
    }

    private fun initFcm() {
        observeFcmToken()
        FcmToken.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = fcmTokenStore.token::setValue,
                        onError = Timber::w
                )
    }

    private fun observeFcmToken() {
        fcmTokenStore.token.observeForever { token ->
            Log.d("Token", token)
        }
    }
}