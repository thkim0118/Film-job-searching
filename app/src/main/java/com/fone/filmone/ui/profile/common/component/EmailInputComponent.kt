package com.fone.filmone.ui.profile.common.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.fone.filmone.R
import com.fone.filmone.ui.main.job.common.LeftTitleTextField

@Composable
fun EmailInputComponent(
    modifier: Modifier = Modifier,
    email: String,
    onUpdateEmail: (String) -> Unit,
) {
    LeftTitleTextField(
        modifier = modifier,
        title = stringResource(id = R.string.profile_register_email_title),
        titleSpace = 13,
        text = email,
        placeholder = stringResource(id = R.string.profile_register_email_placeholder),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
        ),
        onValueChanged = onUpdateEmail,
    )
}
