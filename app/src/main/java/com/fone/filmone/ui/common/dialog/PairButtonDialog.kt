package com.fone.filmone.ui.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.Pretendard

@Composable
fun PairButtonDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false
    ),
    titleText: String,
    leftButtonText: String,
    rightButtonText: String,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit
) {
    val dialogModifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 40.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .background(color = Color.White, shape = RoundedCornerShape(10.dp))

    val titleTextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W400,
        fontSize = 16.textDp,
        lineHeight = 22.textDp,
        color = FColor.TextPrimary,
        textAlign = TextAlign.Center
    )

    val buttonTextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 16.textDp,
        lineHeight = 22.textDp,
        color = FColor.TextSecondary,
        textAlign = TextAlign.Center
    )

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
                        .padding(vertical = 32.dp),
                    text = titleText,
                    style = titleTextStyle
                )

                Divider(color = FColor.Divider1)

                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickableSingle { onLeftButtonClick() }
                            .padding(vertical = 14.5.dp),
                        text = leftButtonText,
                        style = buttonTextStyle
                    )

                    Divider(
                        color = FColor.Divider1,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickableSingle { onRightButtonClick() }
                            .padding(vertical = 14.5.dp),
                        text = rightButtonText,
                        style = buttonTextStyle
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PairButtonDialogPreview() {
    FilmOneTheme {
        PairButtonDialog(
            titleText = "Title",
            leftButtonText = "아니오",
            rightButtonText = "네",
            onLeftButtonClick = {},
            onRightButtonClick = {}
        )
    }
}