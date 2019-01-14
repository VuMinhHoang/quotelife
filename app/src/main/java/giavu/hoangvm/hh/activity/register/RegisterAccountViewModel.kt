package giavu.hoangvm.hh.activity.register

import androidx.lifecycle.ViewModel

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
class RegisterAccountViewModel: ViewModel() {

    private lateinit var navigator: RegisterAccountNavigator

    fun initialize(navigator: RegisterAccountNavigator) {
        this.navigator = navigator
    }

}