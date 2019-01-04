package giavu.hoangvm.japanfood.activity.main

import giavu.hoangvm.japanfood.model.LoginResponse

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
interface LoginNavigator {
    fun toLogin(response: LoginResponse)
}