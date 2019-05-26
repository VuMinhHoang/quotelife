package giavu.hoangvm.hh.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/07
 */

fun <T> LiveData<T>.debounce(duration: Long = 200L, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) =
    MediatorLiveData<T>().also { mediatorLiveData ->
        var disposable: Disposable? = null

        mediatorLiveData.addSource(this) {
            disposable?.dispose()
            disposable = Completable.timer(duration, timeUnit)
                .onErrorComplete()
                .subscribeBy(onComplete = { mediatorLiveData.postValue(it) })
        }
    }
