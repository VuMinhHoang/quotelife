package giavu.hoangvm.hh.activity.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import giavu.hoangvm.hh.helper.ResourceProvider
import giavu.hoangvm.hh.mockLiveDataTaskExecutor
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

    Feature(description = "RegisterAccountViewState") {

        val spkUserNameError: Observer<String> = spyk()
        val spkEmailError: Observer<String> = spyk()
        val spkPasswordError: Observer<String> = spyk()
        val spkRegisterBtnEnabled: Observer<Boolean> = spyk()

        val resourceProvider: ResourceProvider = mockk(relaxed = true)

        val username = MutableLiveData<String>()
        val email = MutableLiveData<String>()
        val password = MutableLiveData<String>()

        val viewState = RegisterAccountViewState(
            _userName = username,
            _email = email,
            _password = password,
            _resourceProvider = resourceProvider
        )
        viewState.registerBtnEnabled.observeForever(spkRegisterBtnEnabled)
        viewState.userNameError.observeForever(spkUserNameError)
        viewState.emailError.observeForever(spkEmailError)

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

        Scenario(description = "Valid input fields") {
            Given(description = "User Name is test") {
                username.value = "test"
            }
            And(description = "Email is test@gmail.com") {
                email.value = "test@gmail.com"
            }
            And(description = "Password is test123456") {
                password.value = "test123456"
            }
            Then(description = "Register button is disabled") {
                verify(timeout = 200L) { spkRegisterBtnEnabled.onChanged(true) }
            }
        }
    }
})