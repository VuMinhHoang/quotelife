package giavu.hoangvm.hh.core.graphql

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/09
 */
class ApiException(message: String?,
                   var code: String? = null) : Exception(message)