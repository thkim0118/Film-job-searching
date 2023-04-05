package com.fone.filmone.ui.signup.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.common.*
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.signup.AgreeState
import com.fone.filmone.ui.signup.PhoneVerificationState
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
    signUpVo: SignUpVo
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

            SignUpIndicator(indicatorType = IndicatorType.Third)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(id = R.string.sign_up_third_title),
                style = LocalTypography.current.h1
            )

            Spacer(modifier = Modifier.height(32.dp))

            PhoneVerificationComponent()

            Spacer(modifier = Modifier.height(49.dp))

            TermComponent()

            Spacer(modifier = Modifier.weight(1f))

            NextButton(signUpVo = signUpVo)

            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}

@Composable
private fun PhoneVerificationComponent(
    viewModel: SignUpThirdViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FTextField(
        text = uiState.phoneNumber,
        onValueChange = { value ->
            viewModel.updatePhoneNumber(value)
        },
        textLimit = 11,
        placeholder = stringResource(id = R.string.sign_up_third_phone_number_placeholder),
        topText = TopText(
            title = stringResource(id = R.string.sign_up_third_phone_number_title),
            titleStar = false,
            titleSpace = 8.dp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone
        ),
        borderButtons = listOf(
            BorderButton(
                text = stringResource(
                    id = when (uiState.phoneVerificationState) {
                        PhoneVerificationState.Complete -> R.string.sign_up_third_phone_number_verification
                        PhoneVerificationState.Retransmit -> R.string.sign_up_third_phone_number_retransmit
                        PhoneVerificationState.ShouldVerify -> R.string.sign_up_third_phone_number_transmit
                    }
                ),
                enable = uiState.phoneNumber.length >= 10 && uiState.phoneVerificationState != PhoneVerificationState.Complete,
                onClick = {
                    viewModel.transmitVerificationCode()
                }
            )
        )
    )

    RetransmitComponent()
}

@Composable
fun RetransmitComponent(
    modifier: Modifier = Modifier,
    viewModel: SignUpThirdViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var verificationCode by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .alpha(
                if (uiState.phoneVerificationState == PhoneVerificationState.Retransmit) {
                    1f
                } else {
                    0f
                }
            )
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        FTextField(
            text = verificationCode,
            onValueChange = { value ->
                verificationCode = value
            },
            textLimit = 6,
            placeholder = stringResource(id = R.string.sign_up_third_phone_number_verification_code_placeholder),
            borderButtons = listOf(
                BorderButton(
                    text = stringResource(id = R.string.sign_up_third_phone_number_check_code),
                    enable = verificationCode.length in 1..6,
                    onClick = {
                        viewModel.checkVerificationCode()
                    }
                )
            ),
            textFieldTail = FTextFieldTail.Text(
                text = uiState.verificationTime,
                style = fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    color = FColor.ColorFF5841
                )
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.sign_up_third_phone_number_check_code_guide),
            style = LocalTypography.current.label
        )
    }
}

@Composable
private fun TermComponent(
    viewModel: SignUpThirdViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var isTermArrowExpanded by rememberSaveable { mutableStateOf(true) }
    var isPrivacyArrowExpanded by rememberSaveable { mutableStateOf(false) }
    var isMarketingArrowExpanded by rememberSaveable { mutableStateOf(false) }

    fun clickArrow(arrowType: ArrowType, isEnable: Boolean) {
        isTermArrowExpanded = arrowType == ArrowType.Term && isEnable
        isPrivacyArrowExpanded = arrowType == ArrowType.Privacy && isEnable
        isMarketingArrowExpanded = arrowType == ArrowType.Marketing && isEnable
    }

    Row {
        FRadioButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            enable = uiState.isTermAllAgree,
            onClick = {
                viewModel.updateAllAgreeState(uiState.isTermAllAgree.not())
            }
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = stringResource(id = R.string.sign_up_third_agree_all),
            style = LocalTypography.current.h5,
            color = FColor.TextSecondary
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        FRadioButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            enable = uiState.agreeState.contains(AgreeState.Term),
            onClick = {
                viewModel.updateAgreeState(AgreeState.Term)
            }
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.sign_up_third_agree_term),
            style = LocalTypography.current.h5,
            color = FColor.DisablePlaceholder
        )

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

        TermContent(termText = "이용약관 이용약관 이용약관 이용약관 이용약관 이용약관")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        FRadioButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            enable = uiState.agreeState.contains(AgreeState.Privacy),
            onClick = {
                viewModel.updateAgreeState(AgreeState.Privacy)
            }
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.sign_up_third_agree_privacy),
            style = LocalTypography.current.h5,
            color = FColor.DisablePlaceholder
        )

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

        TermContent(termText = "이용약관 이용약관 이용약관 이용약관 이용약관 이용약관")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        FRadioButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            enable = uiState.agreeState.contains(AgreeState.Marketing),
            onClick = {
                viewModel.updateAgreeState(AgreeState.Marketing)
            }
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.sign_up_third_agree_marketing),
            style = LocalTypography.current.h5,
            color = FColor.DisablePlaceholder
        )

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

        TermContent(termText = "이용약관 이용약관 이용약관 이용약관 이용약관 이용약관")
    }
}

@Composable
fun TermContent(
    modifier: Modifier = Modifier,
    termText: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = FColor.BgGroupedBase, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text = termText, style = fTextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = FColor.DisablePlaceholder
            )
        )
    }
}

@Composable
private fun NextButton(
    viewModel: SignUpThirdViewModel = hiltViewModel(),
    signUpVo: SignUpVo
) {
    val uiState by viewModel.uiState.collectAsState()
    val enable = uiState.isRequiredTemAllAgree

    FButton(
        title = stringResource(id = R.string.sign_up_third_button_title),
        enable = enable
    ) {
        if (enable) {
            FOneNavigator.navigateTo(
                FOneDestinations.SignUp.SignUpComplete.getRouteWithArg(
                    signUpVo.copy(
                        phoneNumber = uiState.phoneNumber
                    )
                )
            )
        }
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