package giavu.hoangvm.hh.model

data class UserResponse(
        val account_details: AccountDetails,
        val followers: Int,
        val following: Int,
        val login: String,
        val pic_url: String,
        val pro: Boolean,
        val public_favorites_count: Int
)