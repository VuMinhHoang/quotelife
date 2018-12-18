package giavu.hoangvm.japanfood.graphql

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/13
 */
interface TimeoutConfig {
    abstract fun connectionTimeoutMillis(): Long
    abstract fun writeTimeoutMillis(): Long
    abstract fun readTimeoutMillis(): Long
}