package giavu.hoangvm.hh.activity.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import giavu.hoangvm.hh.mockLiveDataTaskExecutor
import io.mockk.mockk
import io.mockk.spyk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

/**
 * @Author: Hoang Vu
 * @Date:   2019/04/07
 */
object RegisterAccountViewStateTest : Spek({
    mockLiveDataTaskExecutor()

    Feature(description = "RegisterAccountViewState") {

        Scenario(description = "Initialize") {

            val spkUserName : Observer<String> = spyk()
            val spkEmail : Observer<String> = spyk()
            val spkPassword : Observer<String> = spyk()
            val spkRegisterBtnEnabled : Observer<Boolean> = spyk()

            val username = MutableLiveData<String>()
            val email = MutableLiveData<String>()
            val password = MutableLiveData<String>()

            val viewState = RegisterAccountViewState(
                _userName = username,
                _email = email,
                _password = password,
                _resourceProvider = mockk(relaxed = true)
            )

            viewState.registerBtnEnabled.observeForever(spkRegisterBtnEnabled)

            Given(description = "") {
                username.value = ""
            }
            And(description = "") {
                email.value = ""
            }
            And(description = "") {
                password.value = ""
            }
            Then(description = "") {
                spkRegisterBtnEnabled.onChanged(false)
            }
        }
    }
})