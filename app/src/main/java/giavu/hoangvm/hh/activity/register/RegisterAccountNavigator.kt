package giavu.hoangvm.hh.activity.register

import giavu.hoangvm.hh.model.RegisterResponse

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
interface RegisterAccountNavigator {
    fun showProgress()
    fun hideProgress()
    fun register(response: RegisterResponse)
}