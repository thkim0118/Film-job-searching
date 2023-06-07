package com.fone.filmone.ui.main.job.profile.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.main.job.common.TextLimitComponent
import com.fone.filmone.ui.main.job.common.TextWithRequired

@Composable
fun HookingCommentsComponent(
    modifier: Modifier = Modifier,
    hookingComments: String,
    commentsTextLimit: Int,
    onUpdateComments: (String) -> Unit,
) {
    Column(
        modifier = modifier

    ) {
        TextWithRequired(
            title = stringResource(id = R.string.profile_register_comment_title),
            isRequired = true
        )

        Spacer(modifier = Modifier.height(6.dp))

        FTextField(
            text = hookingComments,
            onValueChange = onUpdateComments,
            fixedHeight = 69.dp,
            maxLines = Int.MAX_VALUE,
            singleLine = false,
            placeholder = stringResource(id = R.string.profile_register_comment_placeholder),
            textLimit = commentsTextLimit,
            bottomComponent = {
                TextLimitComponent(
                    modifier = Modifier
                        .align(Alignment.End),
                    currentTextSize = hookingComments.length,
                    maxTextSize = commentsTextLimit
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Default,
                keyboardType = KeyboardType.Text
            ),
        )
    }
}