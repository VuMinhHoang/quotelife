package giavu.hoangvm.hh.utils

sealed class ResultState<out T> {
    class Success<T>(val data: T) : ResultState<T>()
    class Failure(val throwable: Throwable) : ResultState<Nothing>()
}
