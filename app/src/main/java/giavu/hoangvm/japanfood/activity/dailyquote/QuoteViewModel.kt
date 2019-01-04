package giavu.hoangvm.japanfood.activity.dailyquote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/05
 */
class QuoteViewModel: ViewModel() {

    private val _quote = MutableLiveData<String>()
    val quote : LiveData<String> = _quote

    

}