package com.fone.filmone.core.util

import java.util.regex.Pattern

object PatternUtil {

    const val dateRegex = "^[\\d\\s-]+$"

    fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                    ")+"
        )

        return emailPattern.matcher(email).matches()
    }

    // ex) 2023-06-14
    fun isValidDate(birthday: String): Boolean {
        val birthDayPattern = Pattern.compile("^(\\d{4})-(0[1-9]|1[0-2])-(0\\d|[1-2]\\d|3[0-1])+$")

        return birthDayPattern.matcher(birthday).matches()
    }
}