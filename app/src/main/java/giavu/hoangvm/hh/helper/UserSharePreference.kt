package giavu.hoangvm.hh.helper

import android.content.Context
import android.content.SharedPreferences
import giavu.hoangvm.hh.model.LoginResponse

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
class UserSharePreference(private val context: Context) {

    private val BASE_PREFERENCE_NAME = "giavu_share_preferences"
    private val KEY_USER_NAME = BASE_PREFERENCE_NAME + "username"
    private val KEY_EMAIL = BASE_PREFERENCE_NAME + "email"
    private val KEY_SESSION = BASE_PREFERENCE_NAME + "session"

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(BASE_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        fun fromContext(context: Context): UserSharePreference {
            return UserSharePreference(context)
        }
    }

    fun updateUserPref(loginResponse: LoginResponse) {
        prefs.edit().apply {
            putString(KEY_USER_NAME, loginResponse.login)
            putString(KEY_EMAIL, loginResponse.email)
            putString(KEY_SESSION, loginResponse.userToken)
        }.apply()
    }


}