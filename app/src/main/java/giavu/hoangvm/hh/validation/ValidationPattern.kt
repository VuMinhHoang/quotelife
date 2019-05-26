package giavu.hoangvm.hh.validation

import java.util.regex.Pattern

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/19
 */
object ValidationPattern {
    val EMAIL = Pattern.compile(
            "\\A\\s*[^@\\s]{1,64}" + "\\@" + "((?:[-\\p{L}\\d]+\\.)+\\p{L}{2,})\\s*\\z"
    )
    val USERNAME = Pattern.compile("[A-Za-z0-9_]+")
    val PASSWORD = Pattern.compile("\\w{5,120}$")
}
