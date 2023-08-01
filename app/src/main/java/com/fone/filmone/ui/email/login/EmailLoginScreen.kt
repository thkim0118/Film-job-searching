package com.fone.filmone.ui.email.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun EmailLoginScreen(
    modifier: Modifier = Modifier,
    viewModel: EmailLoginViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding()
            .fillMaxSize(),
        snackbarHost = {
            FToast(baseViewModel = viewModel, hostState = it)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleComponent(
                onBackClick = {
                    navController.popBackStack()
                }
            )

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                EmailInputComponent(
                    email = uiState.email,
                    onValueChanged = viewModel::updateEmail
                )

                Spacer(modifier = Modifier.height(20.dp))

                PasswordInputComponent(
                    password = uiState.password,
                    onValueChanged = viewModel::updatePassword
                )

                Spacer(modifier = Modifier.height(40.dp))

                LoginButton(
                    enable = uiState.isButtonEnable,
                    onClick = viewModel::signIn
                )

                Spacer(modifier = Modifier.height(20.dp))

                FindEmailPasswordComponent(
                    modifier = Modifier,
                    onClick = {
                        FOneNavigator.navigateTo(
                            navDestinationState = NavDestinationState(
                                route = FOneDestinations.FindIdPassword.route
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun TitleComponent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    FTitleBar(
        modifier = modifier,
        titleText = stringResource(id = R.string.email_login_title),
        titleType = TitleType.Close,
        onCloseClick = {
            onBackClick()
        }
    )
}

@Composable
private fun EmailInputComponent(
    modifier: Modifier = Modifier,
    email: String,
    onValueChanged: (String) -> Unit
) {
    InputComponent(
        modifier = modifier,
        title = stringResource(id = R.string.email_login_email_title),
        placeholder = stringResource(id = R.string.email_login_email_placeholder),
        value = email,
        isPassword = false,
        onValueChanged = onValueChanged
    )
}

@Composable
private fun PasswordInputComponent(
    modifier: Modifier = Modifier,
    password: String,
    onValueChanged: (String) -> Unit
) {
    InputComponent(
        modifier = modifier,
        title = stringResource(id = R.string.email_login_password_title),
        placeholder = stringResource(id = R.string.email_login_password_placeholder),
        isPassword = true,
        value = password,
        onValueChanged = onValueChanged
    )
}

@Composable
private fun InputComponent(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    isPassword: Boolean,
    value: String,
    onValueChanged: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = LocalTypography.current.subtitle1(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        FTextField(
            text = value,
            onValueChange = onValueChanged,
            placeholder = placeholder,
            keyboardOptions = if (isPassword) {
                KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                )
            } else {
                KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            },
            visualTransformation = if (isPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
        )
    }
}

@Composable
private fun LoginButton(
    modifier: Modifier = Modifier,
    enable: Boolean,
    onClick: () -> Unit
) {
    FButton(
        modifier = modifier,
        title = stringResource(id = R.string.email_login_button_title),
        enable = enable,
        onClick = onClick
    )
}

@Composable
private fun FindEmailPasswordComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clickableSingleWithNoRipple { onClick() },
        text = stringResource(id = R.string.email_login_find_title),
        style = fTextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 13.textDp,
            lineHeight = 17.textDp,
            color = FColor.DisablePlaceholder
        ),
        textAlign = TextAlign.Center
    )
}
