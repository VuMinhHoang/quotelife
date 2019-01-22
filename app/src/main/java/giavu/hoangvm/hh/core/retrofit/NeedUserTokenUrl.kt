package giavu.hoangvm.hh.core.retrofit

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/23
 */
enum class NeedUserTokenUrl(val url: String) {
    DELETE_USER("https://favqs.com/api/session"),
    GET_USER("https://favqs.com/api/users")
}