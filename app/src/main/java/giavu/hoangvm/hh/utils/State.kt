package giavu.hoangvm.hh.utils

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/13
 */
sealed class State<out T> {
    class Success<T>(val data: T) : State<T>()
    class Failure(val throwable: Throwable) : State<Nothing>()
}