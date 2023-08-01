package com.fone.filmone.ui.email.join

import androidx.annotation.StringRes
import com.fone.filmone.R

enum class EmailErrorType(@StringRes val titleRes: Int) {
    NOT_EMAIL_TYPE(R.string.email_join_email_check_error_type_1),
}