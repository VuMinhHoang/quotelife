package giavu.hoangvm.hh.activity.dailyquote

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/23
 */
interface QuoteNavigator {
    fun showProgress()
    fun hideProgress()
    fun toLogout(message: String)
    fun toError(e: Throwable)
}