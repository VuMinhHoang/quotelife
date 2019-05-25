package giavu.co.jp.firebase

import androidx.lifecycle.MutableLiveData

class FcmTokenStore {
    val token: MutableLiveData<String> = MutableLiveData()
}