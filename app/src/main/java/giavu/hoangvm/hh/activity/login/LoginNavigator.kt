package giavu.hoangvm.hh.activity.login

import giavu.hoangvm.hh.exception.ResponseError
import giavu.hoangvm.hh.model.LoginResponse

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
interface LoginNavigator {
    fun toLogin(response: LoginResponse)
    fun toShowError(error: ResponseError)
    fun showProgress()
    fun hideProgress()
}