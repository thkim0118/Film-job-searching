package com.fone.filmone.ui.signup.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.domain.model.signup.Gender
import com.fone.filmone.ui.common.*
import com.fone.filmone.ui.common.ext.fShadow
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.signup.SignUpSecondViewModel
import com.fone.filmone.ui.signup.components.IndicatorType
import com.fone.filmone.ui.signup.components.SignUpIndicator
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography
import java.util.regex.Pattern

@Composable
fun SignUpSecondScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    signUpVo: SignUpVo,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
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
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
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

                Spacer(modifier = Modifier.height(137.dp))
            }

            NextButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                signUpVo = signUpVo
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
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = uiState.isNicknameChecked) {
        if (uiState.isNicknameChecked) {
            focusManager.clearFocus()
        }
    }

    Row {
        FTextField(
            text = uiState.nickname,
            placeholder = stringResource(id = R.string.sign_up_second_nickname_placeholder),
            onValueChange = { value ->
                viewModel.updateNickname(value)
            },
            pattern = Pattern.compile("^[ㄱ-ㅣ가-힣a-zA-Z\\d\\s]+$"),
            topText = TopText(
                title = stringResource(id = R.string.sign_up_second_nickname_title),
                titleStar = true,
                titleSpace = 8.dp
            ),
            borderButtons = listOf(
                BorderButton(
                    text = stringResource(
                        id = if (uiState.isNicknameChecked) {
                            R.string.sign_up_second_nickname_check_duplicate_complete
                        } else {
                            R.string.sign_up_second_nickname_check_duplicate
                        }
                    ),
                    enable = if (uiState.isNicknameChecked) {
                        false
                    } else {
                        uiState.nickname.isNotEmpty()
                    },
                    onClick = {
                        viewModel.checkNicknameDuplication()
                    }
                )
            ),
            enabled = uiState.isNicknameChecked.not(),
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
        text = uiState.birthday,
        placeholder = stringResource(id = R.string.sign_up_second_birthday_sex_placeholder),
        onValueChange = { value ->
            viewModel.updateBirthDay(value)
        },
        pattern = Pattern.compile("^[\\d\\s-]+$"),
        autoCompletion = { before, after ->
            if (before.text.length < after.text.length) {
                when (after.text.length) {
                    5, 8 -> after.copy(
                        text = "${before.text}-${after.text.last()}",
                        selection = TextRange(after.text.length + 1)
                    )
                    else -> after
                }
            } else {
                when (after.text.length) {
                    5, 8 -> after.copy(
                        text = after.text.dropLast(1),
                    )
                    else -> after
                }
            }
        },
        textLimit = 10,
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

    Box(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(108.dp)
                .fShadow(shape = CircleShape)
        )
        Image(
            modifier = Modifier
                .align(Alignment.Center),
            imageVector = ImageVector.vectorResource(id = R.drawable.default_profile),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fShadow(
                    shape = CircleShape,
                    spotColor = FColor.Primary,
                    ambientColor = FColor.Primary
                ),
            imageVector = ImageVector.vectorResource(id = R.drawable.default_profile_camera),
            contentDescription = null
        )
    }
}

@Composable
private fun ColumnScope.NextButton(
    modifier: Modifier = Modifier,
    viewModel: SignUpSecondViewModel = hiltViewModel(),
    signUpVo: SignUpVo
) {
    val uiState by viewModel.uiState.collectAsState()
    val enable = uiState.isNicknameChecked && uiState.isBirthDayChecked

    Spacer(modifier = Modifier.weight(1f))

    FButton(
        modifier = modifier,
        title = stringResource(id = R.string.sign_up_next_title),
        enable = enable,
        onClick = {
            if (enable) {
                FOneNavigator.navigateTo(
                    FOneDestinations.SignUpThird.getRouteWithArg(
                        signUpVo.copy(
                            nickname = uiState.nickname,
                            birthday = uiState.birthday,
                            gender = uiState.gender.name
                        )
                    )
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpSecondScreenPreview() {
    FilmOneTheme {
        SignUpSecondScreen(signUpVo = SignUpVo())
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileComponentPreview() {
    FilmOneTheme {
        Column {
            ProfileComponent()
        }
    }
}