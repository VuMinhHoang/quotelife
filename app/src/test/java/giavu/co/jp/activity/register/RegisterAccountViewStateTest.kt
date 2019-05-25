package giavu.co.jp.activity.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import giavu.co.jp.R
import giavu.co.jp.helper.ResourceProvider
import giavu.co.jp.mockLiveDataTaskExecutor
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/07
 */
object RegisterAccountViewStateTest : Spek({
    mockLiveDataTaskExecutor()

    Feature(description = "RegisterViewState") {

        val spkUserNameError: Observer<String> = spyk()
        val spkEmailError: Observer<String> = spyk()
        val spkPasswordError: Observer<String> = spyk()
        val spkRegisterBtnEnabled: Observer<Boolean> = spyk()

        val resourceProvider: ResourceProvider = mockk(relaxed = true)

        val username = MutableLiveData<String>()
        val email = MutableLiveData<String>()
        val password = MutableLiveData<String>()

        val viewState = RegisterViewState(
            _userName = username,
            _email = email,
            _password = password,
            _resourceProvider = resourceProvider
        )
        viewState.registerBtnEnabled.observeForever(spkRegisterBtnEnabled)
        viewState.userNameError.observeForever(spkUserNameError)
        viewState.emailError.observeForever(spkEmailError)
        viewState.passwordError.observeForever(spkPasswordError)

        Scenario(description = "Initialize") {
            Given(description = "User Name is empty") {
                username.value = ""
            }
            And(description = "Email is empty") {
                email.value = ""
            }
            And(description = "Password is empty") {
                password.value = ""
            }
            Then(description = "Register button is disabled") {
                verify(timeout = 200L) { spkRegisterBtnEnabled.onChanged(false) }
            }
        }

        Scenario(description = "Enable register button when input fields are valid") {
            Given(description = "User Name is test") {
                username.value = "test"
            }
            And(description = "Email is test@gmail.com") {
                email.value = "test@gmail.com"
            }
            And(description = "Password is test123456") {
                password.value = "test123456"
            }
            Then(description = "Register button is enabled") {
                verify(timeout = 200L) { spkRegisterBtnEnabled.onChanged(true) }
            }
        }

        Scenario(description = "Showing error when input fields are invalid") {
            Given(description = "Input invalid user name") {
                username.value = "@#$%123asd"
            }
            And(description = "Input invalid email") {
                email.value = "test@gmail.c"
            }
            And(description = "Input invalid password") {
                password.value = "123"
            }
            Then(description = "Register button is disabled") {
                verify(timeout = 200L) { spkRegisterBtnEnabled.onChanged(false) }
            }
            And(description = "Show user name error") {
                verify(timeout = 200L) { spkUserNameError.onChanged(resourceProvider.getString(R.string.error_username)) }
            }
            And(description = "Show email error") {
                verify(timeout = 200L) { spkEmailError.onChanged(resourceProvider.getString(R.string.error_email)) }
            }
            And(description = "Show password error") {
                verify(timeout = 200L) { spkPasswordError.onChanged(resourceProvider.getString(R.string.error_password)) }
            }
        }
    }
})
