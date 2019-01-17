package giavu.hoangvm.hh.jfd

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
open class JFDApp: Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(this).initialize()
        FirebaseApp.initializeApp(this)
        //val intent = Intent(applicationContext, MainActivity::class.java)
        //startActivity(intent)
    }
}