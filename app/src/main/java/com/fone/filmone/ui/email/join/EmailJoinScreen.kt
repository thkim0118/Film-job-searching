package com.fone.filmone.ui.email.join

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.response.user.LoginType
import com.fone.filmone.ui.common.FBorderButton
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun EmailJoinScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: EmailJoinViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding(),
        snackbarHost = {
            FToast(baseViewModel = viewModel, hostState = it)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            FTitleBar(
                titleType = TitleType.Close,
                titleText = stringResource(id = R.string.email_join_title_text),
                onCloseClick = {
                    navController.popBackStack()
                }
            )

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp)
                ) {
                    NameComponent(
                        name = uiState.name,
                        onUpdateName = viewModel::updateName
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    EmailComponent(
                        email = uiState.email,
                        isEmailChecked = uiState.isEmailChecked,
                        isEmailDuplicated = uiState.isEmailDuplicated,
                        emailErrorType = uiState.emailErrorType,
                        onUpdateEmail = viewModel::updateEmail,
                        onCheckDuplicateEmail = { viewModel.checkDuplicateEmail() }
                    )

                    Spacer(modifier = Modifier.height(23.dp))

                    PasswordComponent(
                        password = uiState.password,
                        confirmedPassword = uiState.confirmedPassword,
                        isPasswordVisible = uiState.isPasswordVisible,
                        isConfirmedPasswordVisible = uiState.isConfirmedPasswordVisible,
                        passwordErrorType = uiState.passwordErrorType,
                        confirmedPasswordErrorType = uiState.confirmedPasswordErrorType,
                        onUpdatePassword = viewModel::updatePassword,
                        onUpdateConfirmedPassword = viewModel::updateConfirmedPassword,
                        onUpdatePasswordVisible = viewModel::updatePasswordVisible,
                        onUpdateConfirmedPasswordVisible = viewModel::updateConfirmedPasswordVisible,
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                }

                NextButton(
                    enable = uiState.isNextButtonEnable,
                    onClick = {
                        FOneNavigator.navigateTo(
                            NavDestinationState(
                                route = FOneDestinations.SignUpFirst.getRouteWithArg(
                                    SignUpVo(
                                        email = uiState.email,
                                        loginType = LoginType.PASSWORD,
                                        password = uiState.confirmedPassword,
                                        token = uiState.token
                                    )
                                )
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun NameComponent(
    modifier: Modifier = Modifier,
    name: String,
    onUpdateName: (String) -> Unit,
) {
    FTextField(
        modifier = modifier,
        text = name,
        placeholder = stringResource(id = R.string.email_join_name_placeholder),
        onValueChange = onUpdateName,
        topComponent = {
            Text(
                text = stringResource(id = R.string.email_join_name_top_text),
                style = LocalTypography.current.subtitle1()
            )

            Spacer(modifier = Modifier.height(6.dp))
        }
    )
}

@Composable
private fun EmailComponent(
    modifier: Modifier = Modifier,
    email: String,
    isEmailChecked: Boolean,
    isEmailDuplicated: Boolean,
    emailErrorType: EmailErrorType?,
    onUpdateEmail: (String) -> Unit,
    onCheckDuplicateEmail: () -> Unit,
) {
    FTextField(
        modifier = modifier,
        text = email,
        placeholder = stringResource(id = R.string.email_join_email_placeholder),
        onValueChange = onUpdateEmail,
        topComponent = {
            Text(
                text = stringResource(id = R.string.email_join_email_top_text),
                style = LocalTypography.current.subtitle1()
            )

            Spacer(modifier = Modifier.height(6.dp))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
        ),
        rightComponents = {
            Spacer(modifier = Modifier.width(4.dp))

            FBorderButton(
                text = stringResource(
                    id = if (isEmailChecked) {
                        R.string.sign_up_second_nickname_check_duplicate_complete
                    } else {
                        R.string.sign_up_second_nickname_check_duplicate
                    }
                ),
                enable = if (isEmailChecked) {
                    false
                } else {
                    email.isNotEmpty() && emailErrorType == null
                },
                onClick = {
                    if (isEmailChecked.not()) {
                        onCheckDuplicateEmail()
                    }
                }
            )
        },
        enabled = isEmailChecked.not(),
        bottomComponent = {
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                modifier = Modifier
                    .alpha(
                        if (emailErrorType != null) {
                            1f
                        } else {
                            0f
                        }
                    ),
                text = emailErrorType?.let {
                    stringResource(id = emailErrorType.titleRes)
                } ?: "",
                style = fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 12.textDp,
                    lineHeight = 14.4.textDp,
                    color = FColor.Error
                )
            )
        },
        isError = emailErrorType != null
    )
}

@Composable
private fun PasswordComponent(
    modifier: Modifier = Modifier,
    password: String,
    confirmedPassword: String,
    isPasswordVisible: Boolean,
    isConfirmedPasswordVisible: Boolean,
    passwordErrorType: PasswordErrorType?,
    confirmedPasswordErrorType: PasswordErrorType?,
    onUpdatePassword: (String) -> Unit,
    onUpdateConfirmedPassword: (String) -> Unit,
    onUpdatePasswordVisible: () -> Unit,
    onUpdateConfirmedPasswordVisible: () -> Unit
) {
    Column(modifier = modifier) {
        FTextField(
            text = password,
            placeholder = stringResource(id = R.string.email_join_password_placeholder_1),
            onValueChange = onUpdatePassword,
            topComponent = {
                Text(
                    text = stringResource(id = R.string.email_join_password_top_text),
                    style = LocalTypography.current.subtitle1()
                )

                Spacer(modifier = Modifier.height(6.dp))
            },
            tailComponent = {
                Icon(
                    modifier = Modifier.clickable {
                        onUpdatePasswordVisible()
                    },
                    imageVector = ImageVector.vectorResource(
                        id = if (isPasswordVisible) {
                            R.drawable.password_show
                        } else {
                            R.drawable.password_hide
                        }
                    ),
                    contentDescription = null
                )
            },
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = passwordErrorType != null,
            bottomComponent = {
                Text(
                    modifier = Modifier
                        .alpha(
                            if (passwordErrorType != null) {
                                1f
                            } else {
                                0f
                            }
                        ),
                    text = passwordErrorType?.let { stringResource(it.titleRes) } ?: "",
                    style = fTextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 12.textDp,
                        lineHeight = 14.32.textDp,
                        color = FColor.Error
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(17.dp))

        FTextField(
            text = confirmedPassword,
            placeholder = stringResource(id = R.string.email_join_password_placeholder_2),
            onValueChange = onUpdateConfirmedPassword,
            tailComponent = {
                Icon(
                    modifier = Modifier.clickable {
                        onUpdateConfirmedPasswordVisible()
                    },
                    imageVector = ImageVector.vectorResource(
                        id = if (isConfirmedPasswordVisible) {
                            R.drawable.password_show
                        } else {
                            R.drawable.password_hide
                        }
                    ),
                    contentDescription = null
                )
            },
            visualTransformation = if (isConfirmedPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = confirmedPasswordErrorType != null,
            bottomComponent = {
                Text(
                    modifier = Modifier
                        .alpha(
                            if (confirmedPasswordErrorType != null) {
                                1f
                            } else {
                                0f
                            }
                        ),
                    text = confirmedPasswordErrorType?.let { stringResource(id = it.titleRes) }
                        ?: "",
                    style = fTextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 12.textDp,
                        lineHeight = 14.32.textDp,
                        color = FColor.Error
                    )
                )
            }
        )
    }
}

@Composable
private fun ColumnScope.NextButton(
    modifier: Modifier = Modifier,
    enable: Boolean,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.weight(1f))

    FButton(
        modifier = modifier.padding(horizontal = 16.dp),
        title = stringResource(id = R.string.next),
        enable = enable
    ) {
        if (enable) {
            onClick()
        }
    }

    Spacer(modifier = Modifier.height(38.dp))
}
