package giavu.hoangvm.hh.validation

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/27
 */
class PasswordValidator: Validator<CharSequence?> {
    override fun verify(value: CharSequence?): Boolean {
        value ?: return false
        return ValidationPattern.PASSWORD.matcher(value).matches()
    }
}