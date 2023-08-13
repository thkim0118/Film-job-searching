package com.fone.filmone.ui.signup.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FRadioButton
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.dialog.SingleButtonDialog
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.phone.PhoneVerificationComponent
import com.fone.filmone.ui.signup.AgreeState
import com.fone.filmone.ui.signup.SignUpThirdDialogState
import com.fone.filmone.ui.signup.SignUpThirdUiState
import com.fone.filmone.ui.signup.SignUpThirdViewModel
import com.fone.filmone.ui.signup.components.IndicatorType
import com.fone.filmone.ui.signup.components.SignUpIndicator
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun SignUpThirdScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: SignUpThirdViewModel = hiltViewModel(),
    signUpVo: SignUpVo
) {
    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding()
            .fillMaxSize(),
        snackbarHost = {
            FToast(
                modifier = modifier,
                baseViewModel = viewModel,
                hostState = it
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SignUpMainScreen(
                navController = navController,
                uiState = uiState,
                onPhoneNumberChanged = viewModel::updatePhoneNumber,
                onVerifyClick = viewModel::transmitVerificationCode,
                onVerificationCheckClick = viewModel::checkVerificationCode,
                onUpdateAllAgreeState = viewModel::updateAllAgreeState,
                onUpdateAgreeState = viewModel::updateAgreeState,
                onSignUpClick = { viewModel.signUp(signUpVo) }
            )

            DialogScreen(
                dialogState = dialogState,
                onDismiss = viewModel::clearDialogState
            )
        }
    }
}

@Composable
private fun SignUpMainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    uiState: SignUpThirdUiState,
    onPhoneNumberChanged: (String) -> Unit,
    onVerifyClick: () -> Unit,
    onVerificationCheckClick: (String) -> Unit,
    onUpdateAllAgreeState: () -> Unit,
    onUpdateAgreeState: (AgreeState) -> Unit,
    onSignUpClick: () -> Unit
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
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                SignUpIndicator(indicatorType = IndicatorType.Third)

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = stringResource(id = R.string.sign_up_third_title),
                    style = LocalTypography.current.h1()
                )

                Spacer(modifier = Modifier.height(32.dp))

                PhoneVerificationComponent(
                    phoneNumber = uiState.phoneNumber,
                    phoneVerificationState = uiState.phoneVerificationState,
                    verificationTime = uiState.verificationTime,
                    onValueChanged = onPhoneNumberChanged,
                    onVerifyClick = onVerifyClick,
                    onVerificationCheckClick = onVerificationCheckClick
                )

                Spacer(modifier = Modifier.height(49.dp))

                TermComponent(
                    uiState = uiState,
                    onUpdateAllAgreeState = onUpdateAllAgreeState,
                    onUpdateAgreeState = onUpdateAgreeState
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            NextButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                uiState = uiState,
                onClick = onSignUpClick
            )

            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}

@Composable
private fun TermComponent(
    uiState: SignUpThirdUiState,
    onUpdateAllAgreeState: () -> Unit,
    onUpdateAgreeState: (AgreeState) -> Unit
) {
    var isTermArrowExpanded by rememberSaveable { mutableStateOf(false) }
    var isPrivacyArrowExpanded by rememberSaveable { mutableStateOf(false) }
    var isMarketingArrowExpanded by rememberSaveable { mutableStateOf(false) }

    fun clickArrow(arrowType: ArrowType, isEnable: Boolean) {
        isTermArrowExpanded = arrowType == ArrowType.Term && isEnable
        isPrivacyArrowExpanded = arrowType == ArrowType.Privacy && isEnable
        isMarketingArrowExpanded = arrowType == ArrowType.Marketing && isEnable
    }

    Row(
        modifier = Modifier
            .clickableWithNoRipple {
                onUpdateAllAgreeState()
            }
    ) {
        FRadioButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            enable = uiState.isTermAllAgree,
            onClick = {
                onUpdateAllAgreeState()
            }
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = stringResource(id = R.string.sign_up_third_agree_all),
            style = LocalTypography.current.h5(),
            color = FColor.TextSecondary
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickableWithNoRipple {
                    onUpdateAgreeState(AgreeState.Term)
                }
        ) {
            FRadioButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                enable = uiState.agreeState.contains(AgreeState.Term),
                onClick = {
                    onUpdateAgreeState(AgreeState.Term)
                }
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.sign_up_third_agree_term),
                style = LocalTypography.current.h5(),
                color = FColor.DisablePlaceholder
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickableWithNoRipple { clickArrow(ArrowType.Term, isTermArrowExpanded.not()) }
        ) {
            if (isTermArrowExpanded) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_up),
                    contentDescription = null
                )
            } else {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_down),
                    contentDescription = null
                )
            }
        }
    }

    if (isTermArrowExpanded) {
        Spacer(modifier = Modifier.height(7.dp))

        TermContent(termText = stringResource(id = R.string.sign_up_term_common))
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickableWithNoRipple {
                    onUpdateAgreeState(AgreeState.Privacy)
                }
        ) {
            FRadioButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                enable = uiState.agreeState.contains(AgreeState.Privacy),
                onClick = {
                    onUpdateAgreeState(AgreeState.Privacy)
                }
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.sign_up_third_agree_privacy),
                style = LocalTypography.current.h5(),
                color = FColor.DisablePlaceholder
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickableWithNoRipple {
                    clickArrow(
                        ArrowType.Privacy,
                        isPrivacyArrowExpanded.not()
                    )
                }
        ) {
            if (isPrivacyArrowExpanded) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_up),
                    contentDescription = null
                )
            } else {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_down),
                    contentDescription = null
                )
            }
        }
    }

    if (isPrivacyArrowExpanded) {
        Spacer(modifier = Modifier.height(7.dp))

        TermContent(termText = stringResource(id = R.string.sign_up_term_privacy))
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickableWithNoRipple {
                    onUpdateAgreeState(AgreeState.Marketing)
                }
        ) {
            FRadioButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                enable = uiState.agreeState.contains(AgreeState.Marketing),
                onClick = {
                    onUpdateAgreeState(AgreeState.Marketing)
                }
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.sign_up_third_agree_marketing),
                style = LocalTypography.current.h5(),
                color = FColor.DisablePlaceholder
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickableWithNoRipple {
                    clickArrow(
                        ArrowType.Marketing,
                        isMarketingArrowExpanded.not()
                    )
                }
        ) {
            if (isMarketingArrowExpanded) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_up),
                    contentDescription = null
                )
            } else {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_down),
                    contentDescription = null
                )
            }
        }
    }

    if (isMarketingArrowExpanded) {
        Spacer(modifier = Modifier.height(7.dp))

        TermContent(termText = stringResource(id = R.string.sign_up_term_marketing))
    }
}

@Composable
fun TermContent(
    modifier: Modifier = Modifier,
    termText: String,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = FColor.BgGroupedBase, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .height(83.dp)
                .verticalScroll(scrollState),
            text = termText,
            style = fTextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 12.textDp,
                lineHeight = 16.textDp,
                color = FColor.DisablePlaceholder
            )
        )
    }
}

@Composable
private fun ColumnScope.NextButton(
    modifier: Modifier = Modifier,
    uiState: SignUpThirdUiState,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.weight(1f))

    FButton(
        modifier = modifier,
        title = stringResource(id = R.string.sign_up_third_button_title),
        enable = uiState.isRequiredTemAllAgree
    ) {
        if (uiState.isRequiredTemAllAgree) {
            onClick()
        }
    }
}

@Composable
private fun DialogScreen(
    dialogState: SignUpThirdDialogState,
    onDismiss: () -> Unit
) {
    when (dialogState) {
        SignUpThirdDialogState.Clear -> Unit
        SignUpThirdDialogState.SignUpFail -> {
            SignUpFailDialog(onClick = onDismiss)
        }

        SignUpThirdDialogState.VerificationComplete -> {
            VerificationCompleteDialog(onClick = onDismiss)
        }
    }
}

@Composable
private fun SignUpFailDialog(
    onClick: () -> Unit
) {
    SingleButtonDialog(
        titleText = stringResource(id = R.string.sign_up_third_dialog_sign_up_fail_title),
        buttonText = stringResource(id = R.string.confirm)
    ) {
        onClick()
    }
}

@Composable
private fun VerificationCompleteDialog(
    onClick: () -> Unit
) {
    LocalFocusManager.current.clearFocus()

    SingleButtonDialog(
        titleText = stringResource(id = R.string.sign_up_third_dialog_verification_complete_title),
        buttonText = stringResource(id = R.string.confirm)
    ) {
        onClick()
    }
}

private sealed interface ArrowType {
    object Term : ArrowType
    object Privacy : ArrowType
    object Marketing : ArrowType
}

@Preview(showBackground = true)
@Composable
fun SignUpThirdScreenPreview() {
    FilmOneTheme {
        SignUpThirdScreen(signUpVo = SignUpVo())
    }
}