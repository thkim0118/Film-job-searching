package com.fone.filmone.ui.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.Pretendard

@Composable
fun SingleButtonDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false
    ),
    titleText: String,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                text = titleText,
                style = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    color = FColor.TextPrimary,
                    textAlign = TextAlign.Center
                )
            )

            Divider(color = FColor.Divider1)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableSingle { onButtonClick.invoke() }
                    .padding(vertical = 11.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buttonText,
                    style = TextStyle(
                        fontFamily = Pretendard,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        color = FColor.TextSecondary,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SingleButtonDialogPreview() {
    FilmOneTheme {
        SingleButtonDialog(
            titleText = "Title",
            buttonText = "Confirm",
        ) {

        }
    }
}