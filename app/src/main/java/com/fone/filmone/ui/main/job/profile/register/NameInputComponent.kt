package com.fone.filmone.ui.main.job.profile.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fone.filmone.R
import com.fone.filmone.ui.main.job.common.LeftTitleTextField

@Composable
fun NameInputComponent(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit
) {
    LeftTitleTextField(
        modifier = modifier,
        title = stringResource(id = R.string.profile_register_name_title),
        titleSpace = 28,
        placeholder = stringResource(id = R.string.profile_register_name_placeholder),
        text = name,
        onValueChanged = onNameChanged
    )
}
