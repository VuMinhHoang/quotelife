package giavu.co.jp.core.graphql

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/13
 */
class DefaultTimeoutConfig : TimeoutConfig {
    companion object {
        const val DEFAULT_CONNECTION_TIMEOUT_MILLISECONDS: Long = 20000

        const val DEFAULT_WRITE_TIMEOUT_MILLISECONDS: Long = 20000

        const val DEFAULT_READ_TIMEOUT_MILLISECONDS: Long = 20000
    }


    override fun connectionTimeoutMillis(): Long {
        return DEFAULT_CONNECTION_TIMEOUT_MILLISECONDS
    }

    override fun writeTimeoutMillis(): Long {
        return DEFAULT_WRITE_TIMEOUT_MILLISECONDS
    }

    override fun readTimeoutMillis(): Long {
        return DEFAULT_READ_TIMEOUT_MILLISECONDS
    }
}