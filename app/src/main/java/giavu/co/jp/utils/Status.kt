package giavu.co.jp.utils

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/13
 */
sealed class Status<out T> {
    class Success<T>(val data: T) : Status<T>()
    class Failure(val throwable: Throwable) : Status<Nothing>()
}