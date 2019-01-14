package giavu.hoangvm.hh.activity.register

import java.util.function.Consumer

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
class RegisterAccountBinding(private val buttonVisibility: Consumer<in Boolean>) {

    fun apply(validator: RegisterValidator){
        when(validator) {
            is RegisterValidator.InValid -> enableRegister()
            is RegisterValidator.Valid -> disableRegister()
        }
    }

    private fun enableRegister(){

    }

    private fun disableRegister(){

    }
}