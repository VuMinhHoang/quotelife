package giavu.hoangvm.hh.activity.register

import giavu.hoangvm.hh.model.LoginResponse

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
interface RegisterAccountNavigator {
    fun showProgress()
    fun hideProgress()
    fun register(response: LoginResponse)
    fun toLogin()
    fun toError(throwable: Throwable)
}