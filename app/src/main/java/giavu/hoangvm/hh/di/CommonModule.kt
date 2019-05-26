package giavu.hoangvm.hh.di

import android.content.Context
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import giavu.hoangvm.hh.helper.ResourceProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module

class CommonModule {
    val module: Module = org.koin.dsl.module.module {
        single<ResourceProvider> { ResourceProviderImpl(androidApplication()) }
    }

    private class ResourceProviderImpl(private val context: Context) : ResourceProvider {

        override fun getString(resId: Int): String =
            context.getString(resId)

        override fun getString(resId: Int, vararg formatArgs: Any): String =
            context.getString(resId, *formatArgs)

        override fun getColor(color: Int): Int = ContextCompat.getColor(context, color)

        override fun getDimensionPixelSize(@DimenRes resId: Int): Int =
            context.resources.getDimensionPixelSize(resId)
    }
}
