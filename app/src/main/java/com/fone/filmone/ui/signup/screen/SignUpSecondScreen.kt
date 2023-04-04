package com.fone.filmone.ui.signup.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.core.LogUtil
import com.fone.filmone.domain.model.signup.Gender
import com.fone.filmone.ui.common.*
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.signup.SignUpSecondViewModel
import com.fone.filmone.ui.signup.components.IndicatorType
import com.fone.filmone.ui.signup.components.SignUpIndicator
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography
import java.util.regex.Pattern

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
        FTitleBar(
            titleType = TitleType.Back,
            titleText = stringResource(id = R.string.sign_up_title_text),
            onBackClick = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
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
private fun NicknameComponent(
    viewModel: SignUpSecondViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Row {
        FTextField(
            text = uiState.nickname,
            placeholder = stringResource(id = R.string.sign_up_second_nickname_placeholder),
            onValueChange = { value ->
                viewModel.updateNickname(value)
            },
            pattern = Pattern.compile("^[ㄱ-ㅣ가-힣a-zA-Z0-9\\s]+$"),
            topText = TopText(
                title = stringResource(id = R.string.sign_up_second_nickname_title),
                titleStar = true,
                titleSpace = 8.dp
            ),
            borderButtons = listOf(
                BorderButton(
                    text = stringResource(id = R.string.sign_up_second_nickname_check_duplicate),
                    enable = uiState.isNicknameChecked,
                    onClick = {

                    }
                )
            ),
            bottomType =
            BottomType.Error(
                errorText = stringResource(id = R.string.sign_up_second_nickname_error_title),
                isError = uiState.isNicknameDuplicated
            )
        )
    }
}

@Composable
private fun BirthdaySexComponent(
    viewModel: SignUpSecondViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FTextField(
        text = uiState.birthDay,
        placeholder = stringResource(id = R.string.sign_up_second_birthday_sex_placeholder),
        onValueChange = { value ->
            viewModel.updateBirthDay(value)
        },
        pattern = Pattern.compile("^[0-9\\s]+$"),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        topText = TopText(
            title = stringResource(id = R.string.sign_up_second_birthday_sex_title),
            subtitle = stringResource(id = R.string.sign_up_second_birthday_sex_subtitle),
            titleStar = true,
            titleSpace = 8.dp
        ),
        borderButtons = listOf(
            BorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_sex_man),
                enable = uiState.gender == Gender.MAN,
                onClick = {
                    viewModel.updateGender(Gender.MAN)
                }
            ),
            BorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_sex_woman),
                enable = uiState.gender == Gender.WOMAN,
                onClick = {
                    viewModel.updateGender(Gender.WOMAN)
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