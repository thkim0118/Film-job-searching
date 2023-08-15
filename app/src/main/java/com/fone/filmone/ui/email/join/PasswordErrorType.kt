package com.fone.filmone.ui.email.join

import androidx.annotation.StringRes
import com.fone.filmone.R

enum class PasswordErrorType(@StringRes val titleRes: Int) {
    INVALID_TYPE(R.string.email_join_password_error),
    NOT_MATCH(R.string.email_join_confirmed_password_error)
}
