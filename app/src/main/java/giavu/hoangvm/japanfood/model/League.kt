package giavu.hoangvm.japanfood.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
data class League(
        @SerializedName("league_slug") val league_slug: String,
        @SerializedName("name") val name: String,
        @SerializedName("nation") val nation: String,
        @SerializedName("level") val level: String,
        @SerializedName("federation") val federation: String)