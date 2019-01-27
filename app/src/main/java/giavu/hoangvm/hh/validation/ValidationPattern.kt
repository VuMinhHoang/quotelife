package giavu.hoangvm.hh.validation

import java.util.regex.Pattern

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/19
 */
object ValidationPattern {
    val PHONE_NUMBER = Pattern.compile("\\d{7,15}")
    val EMAIL_ADDRESS = Pattern.compile(
            "\\A\\s*[^@\\s]{1,64}" + "\\@" + "((?:[-\\p{L}\\d]+\\.)+\\p{L}{2,})\\s*\\z"
    )
    val KANA_NAME = Pattern.compile("\\A[ァ-ー\\s]{1,20}\\z")
    val PASSWORD = Pattern.compile("\\{6,20}$")
}