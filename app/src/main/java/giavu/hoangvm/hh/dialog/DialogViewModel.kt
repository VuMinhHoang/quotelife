package giavu.hoangvm.hh.dialog

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/06
 */
class DialogViewModel : ViewModel() {

    enum class Action {
        POSITIVE,
        NEGATIVE,
        NEUTRAL,
    }

    val result = MutableLiveData<Result>()

    class Result(val action: Action, val data: Intent? = null)

}