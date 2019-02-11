package giavu.hoangvm.hh.activity.login

import giavu.hoangvm.hh.model.LoginResponse

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
interface LoginNavigator {
    fun toLogin(response: LoginResponse)
    fun toRegister()
    fun toShowError(error: Throwable)
    fun showProgress()
    fun hideProgress()
}