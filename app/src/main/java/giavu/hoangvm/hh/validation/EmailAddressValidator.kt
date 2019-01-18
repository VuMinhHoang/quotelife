package giavu.hoangvm.hh.validation

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/19
 */
class EmailAddressValidator: Validator<CharSequence?> {

    override fun verify(value: CharSequence?): Boolean {
        value ?: return false
        return ValidationPattern.EMAIL_ADDRESS.matcher(value).matches()
    }
}