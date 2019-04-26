package giavu.co.jp.core.graphql

import androidx.annotation.NonNull
import java.util.concurrent.TimeUnit

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/13
 */
class TimeoutConfigBuilder {

    private var connectionTimeoutMillis = java.lang.Long.MIN_VALUE

    private var writeTimeoutMillis = java.lang.Long.MIN_VALUE

    private var readTimeoutMillis = java.lang.Long.MIN_VALUE

    fun connectionTimeout(time: Long, @NonNull unit: TimeUnit): TimeoutConfigBuilder {
        this.connectionTimeoutMillis = unit.toMillis(time)
        return this
    }

    fun writeTimeout(time: Long, @NonNull unit: TimeUnit): TimeoutConfigBuilder {
        this.writeTimeoutMillis = unit.toMillis(time)
        return this
    }

    fun readTimeout(time: Long, @NonNull unit: TimeUnit): TimeoutConfigBuilder {
        this.readTimeoutMillis = unit.toMillis(time)
        return this
    }

    fun build(): TimeoutConfig {
        return TimeoutConfigImpl(
                getConnectionTimeoutMillis(),
                getWriteTimeoutMillis(),
                getReadTimeoutMillis()
        )
    }

    private fun getConnectionTimeoutMillis(): Long {
        return if (0 <= connectionTimeoutMillis)
            connectionTimeoutMillis
        else
            DefaultTimeoutConfig.DEFAULT_CONNECTION_TIMEOUT_MILLISECONDS
    }

    private fun getWriteTimeoutMillis(): Long {
        return if (0 <= writeTimeoutMillis)
            writeTimeoutMillis
        else
            DefaultTimeoutConfig.DEFAULT_WRITE_TIMEOUT_MILLISECONDS
    }

    private fun getReadTimeoutMillis(): Long {
        return if (0 <= readTimeoutMillis)
            readTimeoutMillis
        else
            DefaultTimeoutConfig.DEFAULT_READ_TIMEOUT_MILLISECONDS
    }

    private class TimeoutConfigImpl internal constructor(private val connectionTimeoutMillis: Long, private val writeTimeoutMillis: Long, private val readTimeoutMillis: Long) : TimeoutConfig {

        override fun connectionTimeoutMillis(): Long {
            return connectionTimeoutMillis
        }

        override fun writeTimeoutMillis(): Long {
            return writeTimeoutMillis
        }

        override fun readTimeoutMillis(): Long {
            return readTimeoutMillis
        }
    }
}