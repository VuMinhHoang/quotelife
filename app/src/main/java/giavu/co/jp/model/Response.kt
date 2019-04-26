package giavu.co.jp.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
data class Response(
        @SerializedName("page") val page: Int,
        @SerializedName("last_page") val last_page: Boolean,
        @SerializedName("quotes") val quotes: List<Quote>)