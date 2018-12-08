package giavu.hoangvm.japanfood.jfd

import android.app.Application
import giavu.hoangvm.japanfood.BuildConfig
import giavu.hoangvm.japanfood.graphql.Modules
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger
import org.koin.log.EmptyLogger

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/08
 */
class KoinInitializer(private val application: Application) {

    fun initialize() {
        application.startKoin(
                androidContext = application,
                modules = Modules().get(),
                logger = if (BuildConfig.DEBUG) {
                    AndroidLogger()
                } else {
                    EmptyLogger()
                }
        )
    }
}