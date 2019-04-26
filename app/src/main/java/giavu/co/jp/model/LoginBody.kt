package giavu.co.jp.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
data class LoginBody(
        @SerializedName("login") val email: String,
        @SerializedName("password") val password: String
)