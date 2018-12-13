package giavu.hoangvm.japanfood.jfd

import android.app.Application
import android.content.Intent
import giavu.hoangvm.japanfood.activity.MainActivity

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
open class JFDApp: Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(this).initialize()

        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}