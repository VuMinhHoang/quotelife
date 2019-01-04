package giavu.hoangvm.japanfood.utils

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
object StringUtils {

    val EMPTY = ""

    fun isNullOrEmpty(text: CharSequence?): Boolean {
        return text == null || trimAllWhiteSpaces(text)!!.isEmpty()
    }

    fun isNullOrEmpty(text: String?): Boolean {
        return text == null || trimAllWhiteSpaces(text)!!.isEmpty()
    }

    fun urlEncode(s: String): String {
        try {
            return URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("URLEncoder.encode() failed for " + s)
        }

    }

    fun trimAllWhiteSpaces(text: CharSequence?): String? {
        return if (text == null)
            null
        else
            trimAllWhiteSpaces(text.toString())
    }

    fun trimAllWhiteSpaces(text: String?): String? {
        if (text == null || text.isEmpty()) {
            return text
        }

        var startIdx = 0
        while (startIdx < text.length) {
            val ch = text[startIdx]

            if (isExtensiveWhiteSpace(ch)) {
                startIdx++
                continue
            }

            break
            startIdx++
        }

        if (startIdx == text.length) {
            return ""
        }

        var endIdx = text.length - 1
        while (endIdx >= 0) {
            val ch = text[endIdx]

            if (isExtensiveWhiteSpace(ch)) {
                endIdx--
                continue
            }

            break
            endIdx--
        }

        return text.substring(startIdx, endIdx + 1)
    }

    private fun isExtensiveWhiteSpace(ch: Char): Boolean {
        return (Character.isWhitespace(ch)
                || Character.isISOControl(ch)
                || ch == '\u3000'  // FULL-WIDTH WHITESPACE

                || ch == '\u00A0'   // NO-BREAK SPACE

                || ch == '\u2007'   // FIGURE SPACE

                || ch == '\u202F'   // NARROW NO-BREAK SPACE
                )
    }

    @JvmOverloads
    fun join(array: Array<Any>?, separator: Char = ','): String? {
        if (array == null) {
            return null
        }

        var bufSize = array.size

        if (bufSize <= 0) {
            return ""
        }

        bufSize *= (if (array[0] == null) 16 else array[0].toString().length) + 1

        val buf = StringBuilder(bufSize)

        for (i in array.indices) {
            if (i > 0) {
                buf.append(separator)
            }

            if (array[i] != null) {
                buf.append(array[i])
            }
        }

        return buf.toString()
    }

    fun appendNullSafe(s: String): String {
        return if (isNullOrEmpty(s)) "" else s
    }

    /**
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     */
    fun equals(string1: String?, string2: String?): Boolean {
        return if (string1 == null && string2 == null) {
            true
        } else if (string1 == null || string2 == null) {
            false
        } else {
            string1 == string2
        }
    }
}