package com.fone.filmone.ui.main.job.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fone.filmone.ui.common.FTextField

@Composable
fun LeftTitleTextField(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String = "",
    titleSpace: Int,
    text: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Text
    ),
    isRequired: Boolean = true,
    tailComponent: @Composable () -> Unit = {},
    onValueChanged: (String) -> Unit
) {
    FTextField(
        modifier = modifier,
        text = text,
        onValueChange = onValueChanged,
        leftComponents = {
            TextWithRequired(
                title = title,
                isRequired = isRequired
            )

            Spacer(modifier = Modifier.width(titleSpace.dp))
        },
        tailComponent = tailComponent,
        placeholder = placeholder,
        keyboardOptions = keyboardOptions
    )
}
