package giavu.hoangvm.hh.validation

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/27
 */
class UserNameValidator: Validator<CharSequence?> {
    override fun verify(value: CharSequence?): Boolean {
        value?: return false
        return ValidationPattern.USERNAME.matcher(value).matches()
    }
}