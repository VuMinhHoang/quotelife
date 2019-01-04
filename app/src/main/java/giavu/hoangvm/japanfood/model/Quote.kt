package giavu.hoangvm.japanfood.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
data class Quote(
        @SerializedName("tags") val tags: List<String>,
        @SerializedName("id") val id: Int,
        @SerializedName("favorite") val favorite: Boolean,
        @SerializedName("body") val body: String,
        @SerializedName("author") val author: String
)