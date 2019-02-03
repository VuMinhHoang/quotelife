package giavu.hoangvm.hh.activity.main

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/23
 */
interface MainNavigator {
    fun showProgress()
    fun hideProgress()
    fun toLogout(message: String)
    fun toError(e: Throwable)
}