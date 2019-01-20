package giavu.hoangvm.hh.exeption

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/20
 */
class ResponseError(
        @SerializedName("error_code") val errorCode: String,
        @SerializedName("message") val message: String
): Serializable