package com.fone.filmone.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@Composable
fun FTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    pattern: Pattern? = null,
    onTextChanged: ((beforeValue: TextFieldValue, afterValue: TextFieldValue) -> TextFieldValue)? = null,
    textLimit: Int = Int.MAX_VALUE,
    onFocusChange: (Boolean) -> Unit = {},
    topComponent: @Composable ColumnScope.() -> Unit = {},
    bottomComponent: @Composable ColumnScope.() -> Unit = {},
    leftComponents: @Composable RowScope.() -> Unit = {},
    rightComponents: @Composable RowScope.() -> Unit = {},
    rowComponentAlignment: Alignment.Vertical = Alignment.CenterVertically,
    tailComponent: @Composable () -> Unit = {},
    isError: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    backgroundColor: Color = FColor.Divider2,
    errorBorderColor: Color = FColor.Error,
    cornerRounded: Int = 5,
    textStyle: TextStyle = LocalTypography.current.b2(),
    textColor: Color = FColor.TextPrimary,
    fixedHeight: Dp = 42.dp,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholderTextColor: Color = FColor.DisablePlaceholder,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Text
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    cursorColor: Color = FColor.TextPrimary,
    focusRequester: FocusRequester = FocusRequester(),
) {
    val coroutineScope = rememberCoroutineScope()
    var isFocused by rememberSaveable { mutableStateOf(false) }

    val textSelectionColors = TextSelectionColors(
        handleColor = cursorColor,
        backgroundColor = cursorColor.copy(alpha = 0.4f)
    )

    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    var textFieldValue by remember { mutableStateOf(TextFieldValue(text)) }
    if (textFieldValue.text != text) {
        textFieldValue = TextFieldValue(text)
    }

    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        Box(
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChange(it.isFocused)

                    coroutineScope.launch {
                        textFieldValue = if (isFocused) {
                            textFieldValue
                                .copy(selection = TextRange(textFieldValue.text.length))
                        } else {
                            textFieldValue.copy(selection = TextRange(0))
                        }
                    }
                }
        ) {
            @OptIn(ExperimentalMaterialApi::class)
            BasicTextField(
                modifier = Modifier,
                value = textFieldValue,
                onValueChange = {
                    if (it.text.isEmpty()) {
                        textFieldValue = it
                        onValueChange(it.text)
                        return@BasicTextField
                    }

                    if (it.text.length > textLimit) {
                        return@BasicTextField
                    }

                    if (onTextChanged != null) {
                        textFieldValue = onTextChanged(textFieldValue, it)
                    }

                    if (pattern == null) {
                        textFieldValue = if (onTextChanged != null) {
                            textFieldValue
                        } else {
                            it
                        }
                        onValueChange(it.text)
                        return@BasicTextField
                    }

                    if (pattern.matcher(it.text).matches()) {
                        textFieldValue = if (onTextChanged != null) {
                            textFieldValue
                        } else {
                            it
                        }

                        onValueChange(
                            if (onTextChanged != null) {
                                textFieldValue
                            } else {
                                it
                            }.text
                        )
                        return@BasicTextField
                    }
                },
                cursorBrush = SolidColor(cursorColor),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = textStyle.copy(
                    color = textColor,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.textDp,
                    lineHeight = 19.textDp,
                ),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                decorationBox = @Composable { innerTextField ->
                    Column(
                        modifier = Modifier
                    ) {
                        topComponent()

                        Row(verticalAlignment = rowComponentAlignment) {
                            leftComponents()

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(fixedHeight)
                                    .clip(shape = RoundedCornerShape(cornerRounded.dp))
                                    .border(
                                        width = 1.dp,
                                        color = if (isError) {
                                            errorBorderColor
                                        } else {
                                            FColor.Transparent
                                        },
                                        shape = RoundedCornerShape(cornerRounded.dp)
                                    )
                                    .background(
                                        backgroundColor,
                                        RoundedCornerShape(cornerRounded.dp)
                                    )
                                    .indicatorLine(
                                        enabled,
                                        false,
                                        interactionSource,
                                        TextFieldDefaults.textFieldColors(
                                            backgroundColor = backgroundColor,
                                            cursorColor = cursorColor,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                        )
                                    )
                                    .defaultMinSize(
                                        minWidth = TextFieldDefaults.MinWidth,
                                        minHeight = TextFieldDefaults.MinHeight
                                    )
                            ) {
                                TextFieldDefaults.TextFieldDecorationBox(
                                    value = textFieldValue.text,
                                    visualTransformation = visualTransformation,
                                    innerTextField = @Composable {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(fixedHeight)
                                            ) {
                                                // FIXME https://holykisa.tistory.com/74
                                                innerTextField()
                                            }

                                            tailComponent()
                                        }
                                    },
                                    placeholder = @Composable {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(fixedHeight)
                                        ) {
                                            Text(
                                                text = placeholder,
                                                style = fTextStyle(
                                                    fontWeight = FontWeight.W400,
                                                    fontSize = 14.textDp,
                                                    lineHeight = 19.textDp,
                                                    color = placeholderTextColor,
                                                ),
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    },
                                    contentPadding = PaddingValues(
                                        vertical = 12.dp,
                                        horizontal = 10.dp
                                    ),
                                    label = null,
                                    singleLine = true,
                                    enabled = enabled,
                                    isError = isError,
                                    interactionSource = interactionSource,
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = backgroundColor,
                                        cursorColor = cursorColor,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                    )
                                )
                            }

                            rightComponents()
                        }

                        bottomComponent()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FTextFieldPreview() {
    FilmOneTheme {
        FTextField(
            text = "Input Text",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FTextFieldFixedSizePreview() {
    FilmOneTheme {
        FTextField(
            text = "Input Text",
            onValueChange = {},
            fixedHeight = 150.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FTextFieldPlaceHolderPreview() {
    FilmOneTheme {
        FTextField(
            text = "",
            placeholder = "3~8자리의 숫자, 영어, 한글만 가능합니다.",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FTextFieldBottomErrorTypePreview() {
    FilmOneTheme {
        FTextField(
            text = "Input Text",
            onValueChange = {},
            topComponent = {
                Row {
                    Text(
                        text = "topText.title",
                        style = LocalTypography.current.subtitle1()
                    )

                    Text(
                        text = " *",
                        style = fTextStyle(
                            fontWeight = FontWeight.W500,
                            fontSize = 16.textDp,
                            lineHeight = 19.2.textDp,
                            color = FColor.Error
                        )
                    )

                    Text(
                        text = "topText.subtitle",
                        style = LocalTypography.current.label(),
                        color = FColor.DisablePlaceholder
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FTextFieldBottomWithFBorderButtonPreview() {
    FilmOneTheme {
        Row {
            FTextField(
                text = "Input Text",
                onValueChange = {},
                topComponent = {
                    Row {
                        Text(
                            text = "topText.title",
                            style = LocalTypography.current.subtitle1()
                        )

                        Text(
                            text = " *",
                            style = fTextStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 16.textDp,
                                lineHeight = 19.2.textDp,
                                color = FColor.Error
                            )
                        )

                        Text(
                            text = "topText.subtitle",
                            style = LocalTypography.current.label(),
                            color = FColor.DisablePlaceholder
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))
                },
                rightComponents = {
                    Spacer(modifier = Modifier.width(4.dp))

                    FBorderButton(
                        text = "중복확인",
                        enable = true,
                        onClick = {}
                    )
                },
                tailComponent = {
                    Text(
                        text = "3:00",
                        style = fTextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 12.textDp,
                            lineHeight = 14.textDp,
                            color = FColor.ColorFF5841
                        )
                    )
                }
            )
        }
    }
}