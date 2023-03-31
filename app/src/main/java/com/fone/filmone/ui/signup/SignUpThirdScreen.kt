package com.fone.filmone.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun SignUpThirdScreen(
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

            SignUpIndicator(indicatorType = IndicatorType.Third)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(id = R.string.sign_up_third_title),
                style = LocalTypography.current.h1
            )

            Spacer(modifier = Modifier.height(32.dp))

            PhoneVerificationComponent()

            Spacer(modifier = Modifier.weight(1f))

            TermComponent()

            Spacer(modifier = Modifier.weight(1f))

            FButton(
                title = stringResource(id = R.string.sign_up_third_button_title),
                enable = false
            ) {
                navController.navigate(FOneDestinations.SignUp.SignUpComplete.route)
            }

            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}

@Composable
private fun PhoneVerificationComponent() {
    val isVerificationCodeSend by rememberSaveable { mutableStateOf(true) }

    FTextField(
        onValueChange = { value -> },
        placeholder = stringResource(id = R.string.sign_up_third_phone_number_placeholder),
        topText = TopText(
            title = stringResource(id = R.string.sign_up_third_phone_number_title),
            titleStar = false,
        ),
        borderButtons = listOf(
            BorderButton(
                text = stringResource(id = R.string.sign_up_third_phone_number_send_title),
                enable = false,
                onClick = {

                }
            )
        )
    )

    Column(
        modifier = Modifier
            .alpha(
                if (isVerificationCodeSend) {
                    1f
                } else {
                    0f
                }
            )
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        FTextField(
            onValueChange = { value -> },
            placeholder = stringResource(id = R.string.sign_up_third_phone_number_verification_code_placeholder),
            borderButtons = listOf(
                BorderButton(
                    text = stringResource(id = R.string.sign_up_third_phone_number_check_code),
                    enable = false,
                    onClick = {

                    }
                )
            ),
            textFieldTail = FTextFieldTail.Text(
                text = "3:00",
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
private fun TermComponent() {
    val isTermArrowExpanded by rememberSaveable { mutableStateOf(true) }
    val isPrivacyArrowExpanded by rememberSaveable { mutableStateOf(false) }
    val isMarketingArrowExpanded by rememberSaveable { mutableStateOf(false) }

    Row {
        FRadioButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            enable = false
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
            enable = false
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.sign_up_third_agree_term),
            style = LocalTypography.current.h5,
            color = FColor.DisablePlaceholder
        )

        if (isTermArrowExpanded) {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_up),
                contentDescription = null
            )
        } else {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_down),
                contentDescription = null
            )
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
            enable = false
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.sign_up_third_agree_privacy),
            style = LocalTypography.current.h5,
            color = FColor.DisablePlaceholder
        )

        if (isPrivacyArrowExpanded) {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_up),
                contentDescription = null
            )
        } else {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_down),
                contentDescription = null
            )
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
            enable = false
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.sign_up_third_agree_marketing),
            style = LocalTypography.current.h5,
            color = FColor.DisablePlaceholder
        )

        if (isMarketingArrowExpanded) {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_up),
                contentDescription = null
            )
        } else {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(id = R.drawable.term_arrow_down),
                contentDescription = null
            )
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

@Preview(showBackground = true)
@Composable
fun SignUpThirdScreenPreview() {
    FilmOneTheme {
        SignUpThirdScreen()
    }
}