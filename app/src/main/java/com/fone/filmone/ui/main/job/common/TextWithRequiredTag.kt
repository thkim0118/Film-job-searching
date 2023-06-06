package com.fone.filmone.ui.main.job.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TextWithRequiredTag(
    modifier: Modifier = Modifier,
    title: String,
    tagTitle: String,
    isRequired: Boolean,
    tagEnable: Boolean,
    onTagClick: () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        TextWithRequired(
            title = title,
            isRequired = isRequired
        )

        Spacer(modifier = Modifier.weight(1f))

        TagComponent(
            title = tagTitle,
            enable = tagEnable,
            onClick = {
                onTagClick()
            }
        )
    }
}