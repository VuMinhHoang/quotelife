package giavu.hoangvm.hh.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/14
 */
data class RegBody(
        @SerializedName("login") val login: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?)