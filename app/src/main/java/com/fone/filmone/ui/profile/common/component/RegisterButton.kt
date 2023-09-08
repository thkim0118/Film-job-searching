package com.fone.filmone.ui.profile.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.FButton

@Composable
fun RegisterButton(
    modifier: Modifier = Modifier,
    buttonEnable: Boolean,
    onRegisterButtonClick: () -> Unit
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        FButton(
            title = stringResource(id = R.string.profile_register_register_button),
            enable = buttonEnable,
            onClick = onRegisterButtonClick
        )

        Spacer(modifier = Modifier.height(38.dp))
    }
}
