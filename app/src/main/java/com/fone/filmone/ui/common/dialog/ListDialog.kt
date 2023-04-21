package com.fone.filmone.ui.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun ListDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false
    ),
    titleText: String,
    buttonText: String,
    content: @Composable () -> Unit,
    onButtonClick: () -> Unit,
) {
    val dialogModifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 40.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .background(color = FColor.TextSecondary, shape = RoundedCornerShape(10.dp))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FColor.DimColorThin)
            .clickable(onClick = onDismissRequest)
    ) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = properties,
        ) {
            Column(
                modifier = dialogModifier
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp),
                    text = titleText,
                    style = LocalTypography.current.h4(),
                    color = FColor.White,
                    textAlign = TextAlign.Center
                )

                content()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onButtonClick)
                        .padding(vertical = 17.5.dp),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = buttonText,
                        style = LocalTypography.current.button1(),
                        color = FColor.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}