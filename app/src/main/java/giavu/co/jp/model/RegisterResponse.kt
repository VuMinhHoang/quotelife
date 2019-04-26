package giavu.co.jp.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
data class RegisterResponse(
        @SerializedName("User-Token") val userToken: String?,
        @SerializedName("login") val login: String?
)