package com.fone.filmone.ui.profile.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.main.job.common.TextLimitComponent
import com.fone.filmone.ui.main.job.common.TextWithRequired
import com.fone.filmone.ui.theme.FColor

@Composable
fun DetailInputComponent(
    modifier: Modifier = Modifier,
    detailInfo: String,
    detailInfoTextLimit: Int,
    onUpdateDetailInfo: (String) -> Unit,
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequired(
            title = stringResource(id = R.string.profile_register_detail_title),
            isRequired = true
        )

        Spacer(modifier = Modifier.height(6.dp))

        FTextField(
            text = detailInfo,
            placeholder = stringResource(id = R.string.profile_register_detail_placeholder),
            placeholderTextColor = FColor.DisablePlaceholder,
            onValueChange = onUpdateDetailInfo,
            textLimit = detailInfoTextLimit,
            fixedHeight = 138.dp,
            bottomComponent = {
                Spacer(modifier = Modifier.height(2.dp))

                TextLimitComponent(
                    modifier = Modifier.align(Alignment.End),
                    currentTextSize = detailInfo.length,
                    maxTextSize = detailInfoTextLimit
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}
