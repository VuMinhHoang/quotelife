package giavu.hoangvm.japanfood.core.graphql

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/14
 */
class ApiHeader {
    companion object {
        val KEY_USER_AGENT = "User-agent"
        val KEY_ACCEPT_LANGUAGE = "Accept-Language"
        val KEY_ACCEPT = "Accept"
        val CONTENT_TYPE="Content-Type"
        val VALUE_ACCEPT_JSON = "application/json"
        val KEY_AUTHORIZATION = "Authorization"
        val VALUE_AUTHORIZATION_BEARER_PREFIX = "Bearer "
        val KEY_API_KEY = "APIKey"
        val X_MASHAPE_KEY = "X-Mashape-Key"
        val KEY_LANGUAGE = "Language"
        val CLIENT_ID = "clientID"
    }
}