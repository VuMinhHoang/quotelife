package giavu.hoangvm.hh

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import io.mockk.every
import io.mockk.mockkStatic
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun mockLiveDataTaskExecutor() {
    ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
        override fun executeOnDiskIO(runnable: Runnable) {
            runnable.run()
        }

        override fun isMainThread(): Boolean {
            return true
        }

        override fun postToMainThread(runnable: Runnable) {
            runnable.run()
        }
    })
}

fun mockRxScheduler() {
    mockkStatic(Schedulers::class)
    every { Schedulers.io() } returns Schedulers.trampoline()

    mockkStatic(AndroidSchedulers::class)
    every { AndroidSchedulers.mainThread() } returns Schedulers.trampoline()
}
