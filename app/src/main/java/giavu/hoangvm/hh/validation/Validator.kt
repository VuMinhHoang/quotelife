package giavu.hoangvm.hh.validation

import java.io.Serializable

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/19
 */
interface Validator<in T>: Serializable {
    fun verify(value: T): Boolean
}