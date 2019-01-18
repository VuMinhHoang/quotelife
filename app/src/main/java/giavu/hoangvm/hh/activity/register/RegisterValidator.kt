package giavu.hoangvm.hh.activity.register

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
sealed class RegisterValidator {
    object Valid : RegisterValidator()
    object InValid : RegisterValidator()
}