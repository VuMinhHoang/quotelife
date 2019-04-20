package giavu.hoangvm.hh.activity.main

import giavu.hoangvm.hh.model.LoginResponse
import io.reactivex.functions.Consumer

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/20
 */
class NavHeaderBinding(
    private val userNameText: Consumer<in CharSequence>,
    private val emailText: Consumer<in CharSequence>,
    private val userNameVisibility: Consumer<in Boolean>,
    private val emailVisibility: Consumer<in Boolean>
) {
    fun apply(loginResponse: LoginResponse) {
        userNameVisibility.accept(true)
        emailVisibility.accept(true)
        userNameText.accept(loginResponse.login)
        emailText.accept(loginResponse.email)
    }
}