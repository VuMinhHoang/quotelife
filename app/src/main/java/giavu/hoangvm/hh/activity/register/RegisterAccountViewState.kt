package giavu.hoangvm.hh.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.helper.ResourceProvider
import giavu.hoangvm.hh.validation.ValidationPattern
import java.util.regex.Pattern

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/06
 */
class RegisterAccountViewState(
    private val _userName: LiveData<String>,
    private val _email: LiveData<String>,
    private val _password: LiveData<String>,
    private val _resourceProvider: ResourceProvider
) {
    private val EMPTY = ""
    private val userNameValidator = RegisterFieldValidator(ValidationPattern.USERNAME)
    private val emailValidator = RegisterFieldValidator(ValidationPattern.EMAIL)
    private val passwordValidator = RegisterFieldValidator(ValidationPattern.PASSWORD)

    private val isUserValid: LiveData<ValidationStatus> = Transformations.map(_userName) {
        userNameValidator.verify(it)
    }

    private val isEmailValid: LiveData<ValidationStatus> = Transformations.map(_email) {
        emailValidator.verify(it)
    }

    private val isPasswordValid: LiveData<ValidationStatus> = Transformations.map(_password) {
        passwordValidator.verify(it)
    }

    val userNameError: LiveData<String> = Transformations.map(isUserValid) {
        when (it) {
            ValidationStatus.Ignore,
            ValidationStatus.Valid -> EMPTY
            ValidationStatus.InValid -> _resourceProvider.getString(R.string.error_username)
        }
    }

    val emailError: LiveData<String> = Transformations.map(isEmailValid) {
        when (it) {
            ValidationStatus.Ignore,
            ValidationStatus.Valid -> EMPTY
            ValidationStatus.InValid ->  _resourceProvider.getString(R.string.error_email)
        }
    }

    val passwordError: LiveData<String> = Transformations.map(isPasswordValid) {
        when (it) {
            ValidationStatus.Ignore,
            ValidationStatus.Valid -> EMPTY
            ValidationStatus.InValid -> _resourceProvider.getString(R.string.error_password)
        }
    }

    val registerBtnEnabled: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {

        val observer: Observer<ValidationStatus> = Observer {
            this.value = isEmailValid.value is ValidationStatus.Valid
                    && isUserValid.value is ValidationStatus.Valid
                    && isPasswordValid.value is ValidationStatus.Valid
        }
        addSource(isUserValid, observer)
        addSource(isEmailValid, observer)
        addSource(isPasswordValid, observer)
    }

    private class RegisterFieldValidator(val pattern: Pattern) {
        fun verify(charSequence: CharSequence?): ValidationStatus {
            if (charSequence.isNullOrEmpty()) return ValidationStatus.Ignore
            return when (pattern.matcher(charSequence).matches()) {
                true -> ValidationStatus.Valid
                false -> ValidationStatus.InValid
            }
        }
    }

    private sealed class ValidationStatus {
        object Valid : ValidationStatus()
        object InValid : ValidationStatus()
        object Ignore : ValidationStatus()
    }


}