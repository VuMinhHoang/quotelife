package giavu.hoangvm.hh.exception

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/02/10
 */
data class ResponseSuccessErrorCode(
    @SerializedName("error_code") val errorCode: String,
    @SerializedName("message") val messageError: String
) : Exception()