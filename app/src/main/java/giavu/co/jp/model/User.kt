package giavu.co.jp.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
data class User(
        @SerializedName("user") val user: LoginBody
)