package com.fone.filmone.ui.email.find

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fone.filmone.R
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.phone.PhoneVerificationComponent
import com.fone.filmone.ui.common.phone.PhoneVerificationState
import com.fone.filmone.ui.email.find.component.TopGuideComponent
import com.fone.filmone.ui.email.find.model.FindScreenType
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun FindPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: FindViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.resetData()
    }

    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .defaultSystemBarPadding()
            .toastPadding(),
        snackbarHost = {
            FToast(baseViewModel = viewModel, hostState = it)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            TopGuideComponent()

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                PhoneVerificationComponent(
                    phoneNumber = uiState.phoneNumber,
                    phoneVerificationState = uiState.phoneVerificationState,
                    verificationTime = uiState.verificationTime,
                    onValueChanged = viewModel::updatePhoneNumber,
                    onVerifyClick = viewModel::transmitVerificationCode,
                    onVerificationCheckClick = { code ->
                        viewModel.checkVerificationCode(code, FindScreenType.FIND_PASSWORD)
                    }
                )

                if (uiState.phoneVerificationState is PhoneVerificationState.Complete &&
                    uiState.findPasswordToken.isNotEmpty()
                ) {
                    PasswordResetComponent(
                        newPassword = uiState.newPassword,
                        confirmedPassword = uiState.confirmedPassword,
                        onNewPasswordChanged = viewModel::updateNewPassword,
                        onConfirmedPasswordChanged = viewModel::updateConfirmedPassword,
                        isNewPasswordVisible = uiState.isNewPasswordVisible,
                        isConfirmedPasswordVisible = uiState.isConfirmedPasswordVisible,
                        onNewPasswordVisibleChanged = viewModel::updateNewPasswordVisible,
                        onConfirmedPasswordVisibleChanged = viewModel::updateConfirmedPasswordVisible,
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            ChangePasswordButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                enable = uiState.changePasswordButtonEnable,
                onClick = viewModel::changePassword

            )
        }
    }
}

@Composable
private fun PasswordResetComponent(
    modifier: Modifier = Modifier,
    newPassword: String,
    confirmedPassword: String,
    onNewPasswordChanged: (String) -> Unit,
    onConfirmedPasswordChanged: (String) -> Unit,
    isNewPasswordVisible: Boolean,
    isConfirmedPasswordVisible: Boolean,
    onNewPasswordVisibleChanged: () -> Unit,
    onConfirmedPasswordVisibleChanged: () -> Unit,
) {
    Column(modifier = modifier) {
        FTextField(
            text = newPassword,
            onValueChange = onNewPasswordChanged,
            placeholder = stringResource(id = R.string.find_password_input_1),
            topComponent = {
                Text(
                    text = stringResource(id = R.string.find_password_reset_title),
                    style = LocalTypography.current.subtitle1()
                )

                Spacer(modifier = Modifier.height(8.dp))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
            visualTransformation = if (isNewPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            tailComponent = {
                Icon(
                    modifier = Modifier.clickableSingleWithNoRipple { onNewPasswordVisibleChanged() },
                    imageVector = ImageVector.vectorResource(
                        id =
                        if (isNewPasswordVisible) {
                            R.drawable.password_hide
                        } else {
                            R.drawable.password_show
                        }
                    ),
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FTextField(
            text = confirmedPassword,
            onValueChange = onConfirmedPasswordChanged,
            placeholder = stringResource(id = R.string.find_password_input_2),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (isConfirmedPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            tailComponent = {
                Icon(
                    modifier = Modifier.clickableSingleWithNoRipple { onConfirmedPasswordVisibleChanged() },
                    imageVector = ImageVector.vectorResource(
                        id =
                        if (isConfirmedPasswordVisible) {
                            R.drawable.password_hide
                        } else {
                            R.drawable.password_show
                        }
                    ),
                    contentDescription = null
                )
            }
        )
    }
}

@Composable
private fun ColumnScope.ChangePasswordButton(
    modifier: Modifier = Modifier,
    enable: Boolean,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.weight(1f))

    FButton(
        modifier = modifier,
        title = stringResource(id = R.string.find_password_reset_title),
        enable = enable
    ) {
        onClick()
    }

    Spacer(modifier = Modifier.height(38.dp))
}
