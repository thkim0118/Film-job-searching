package com.fone.filmone.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.ui.FOneDestinations
import com.fone.filmone.ui.common.*
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.signup.components.IndicatorType
import com.fone.filmone.ui.signup.components.SignUpIndicator
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography
import com.fone.filmone.ui.theme.Pretendard

@Composable
fun SignUpSecondScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .fillMaxSize()
    ) {
        TitleBar(
            titleType = TitleType.Back,
            titleText = stringResource(id = R.string.sign_up_title_text),
            onBackClick = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            SignUpIndicator(indicatorType = IndicatorType.Second)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(id = R.string.sign_up_second_title),
                style = LocalTypography.current.h1
            )

            Spacer(modifier = Modifier.height(32.dp))

            NicknameComponent()

            Spacer(modifier = Modifier.height(23.dp))

            BirthdaySexComponent()

            Spacer(modifier = Modifier.height(40.dp))

            ProfileComponent()

            Spacer(modifier = Modifier.weight(1f))

            FButton(
                title = stringResource(id = R.string.sign_up_next_title),
                enable = false,
                onClick = {
                    navController.navigate(FOneDestinations.SignUp.SignUpThird.route)
                }
            )

            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}

@Composable
private fun NicknameComponent() {
    Row {
        FTextField(
            text = "",
            placeholder = stringResource(id = R.string.sign_up_second_nickname_placeholder),
            onValueChange = { value ->
            },
            topText = TopText(
                title = stringResource(id = R.string.sign_up_second_nickname_title),
                titleStar = true
            ),
            borderButtons = listOf(
                BorderButton(
                    text = stringResource(id = R.string.sign_up_second_nickname_check_duplicate),
                    enable = false,
                    onClick = {

                    }
                )
            ),
            bottomType =
                BottomType.Error(
                    errorText = stringResource(id = R.string.sign_up_second_nickname_error_title),
                    isError = true
                )
        )
    }
}

@Composable
private fun BirthdaySexComponent() {
    FTextField(
        text = "",
        placeholder = stringResource(id = R.string.sign_up_second_birthday_sex_placeholder),
        onValueChange = { value ->
        },
        topText = TopText(
            title = stringResource(id = R.string.sign_up_second_birthday_sex_title),
            subtitle = stringResource(id = R.string.sign_up_second_birthday_sex_subtitle),
            titleStar = true
        ),
        borderButtons = listOf(
            BorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_sex_man),
                enable = false,
                onClick = {

                }
            ),
            BorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_sex_woman),
                enable = false,
                onClick = {

                }
            )
        ),
    )
}

@Composable
private fun ProfileComponent() {
    Text(
        text = stringResource(id = R.string.sign_up_second_profile_title),
        style = LocalTypography.current.subtitle1
    )

    Spacer(modifier = Modifier.height(2.dp))

    Text(
        text = stringResource(id = R.string.sign_up_second_profile_subtitle),
        style = LocalTypography.current.label
    )

    Spacer(modifier = Modifier.height(8.dp))

    Box {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.default_profile_shadow),
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            imageVector = ImageVector.vectorResource(id = R.drawable.default_profile),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpSecondScreenPreview() {
    FilmOneTheme {
        SignUpSecondScreen()
    }
}