package giavu.hoangvm.hh.firebase

import androidx.lifecycle.MutableLiveData

class FcmTokenStore {
    val token: MutableLiveData<String> = MutableLiveData()
}