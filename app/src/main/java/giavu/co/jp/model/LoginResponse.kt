package giavu.co.jp.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
data class LoginResponse(
        @SerializedName("User-Token") val userToken: String?,
        @SerializedName("login") val login: String?,
        @SerializedName("email") val email: String?
)