package giavu.hoangvm.hh.exeption

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/22
 */
enum class ErrorCode(val errorCode: Int) {
    LOGIN_INVALID(21),
    LOGIN_DEACTIVE(22),
    LOGIN_FAILURE(23),

    USER_SESSION_EXIST(31),
    USER_INVALID(32)
}