package giavu.hoangvm.hh.core.graphql

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/13
 */
interface TimeoutConfig {
    fun connectionTimeoutMillis(): Long
    fun writeTimeoutMillis(): Long
    fun readTimeoutMillis(): Long
}