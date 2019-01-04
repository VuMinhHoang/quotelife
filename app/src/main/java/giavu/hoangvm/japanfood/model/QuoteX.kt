package giavu.hoangvm.japanfood.model

import com.google.gson.annotations.SerializedName

data class QuoteX(
        @SerializedName("author") val author: String,
        @SerializedName("author_permalink") val author_permalink: String,
        @SerializedName("body") val body: String,
        @SerializedName("dialogue") val dialogue: Boolean,
        @SerializedName("downvotes_count") val downvotes_count: Int,
        @SerializedName("favorites_count") val favorites_count: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("tags") val tags: List<String>,
        @SerializedName("upvotes_count") val upvotes_count: Int,
        @SerializedName("url") val url: String
)