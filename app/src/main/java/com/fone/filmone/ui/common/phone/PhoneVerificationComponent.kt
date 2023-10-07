package com.fone.filmone.ui.common.phone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.FBorderButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun PhoneVerificationComponent(
    phoneNumber: String,
    phoneVerificationState: PhoneVerificationState,
    verificationTime: String,
    isRetransmitVisible: Boolean = true,
    onValueChanged: (String) -> Unit,
    onVerifyClick: () -> Unit,
    onVerificationCheckClick: (String) -> Unit
) {
    FTextField(
        text = phoneNumber,
        onValueChange = onValueChanged,
        textLimit = 11,
        placeholder = stringResource(id = R.string.sign_up_third_phone_number_placeholder),
        topComponent = {
            Text(
                text = stringResource(id = R.string.sign_up_third_phone_number_title),
                style = LocalTypography.current.subtitle1()
            )

            Spacer(modifier = Modifier.height(8.dp))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        rightComponents = {
            Spacer(modifier = Modifier.width(4.dp))

            FBorderButton(
                text = stringResource(
                    id = when (phoneVerificationState) {
                        PhoneVerificationState.Complete -> R.string.sign_up_third_phone_number_verification
                        PhoneVerificationState.Retransmit -> R.string.sign_up_third_phone_number_retransmit
                        PhoneVerificationState.ShouldVerify -> R.string.sign_up_third_phone_number_transmit
                    }
                ),
                enable = phoneNumber.length >= 10 && phoneVerificationState != PhoneVerificationState.Complete,
                onClick = onVerifyClick
            )
        }
    )

    if (isRetransmitVisible) {
        RetransmitComponent(
            phoneVerificationState = phoneVerificationState,
            verificationTime = verificationTime,
            onVerificationCheckClick = onVerificationCheckClick
        )
    }
}

@Composable
private fun RetransmitComponent(
    modifier: Modifier = Modifier,
    phoneVerificationState: PhoneVerificationState,
    verificationTime: String,
    onVerificationCheckClick: (String) -> Unit
) {
    val isVisible = phoneVerificationState == PhoneVerificationState.Retransmit
    var verificationCode by remember { mutableStateOf("") }
    val enable = verificationCode.length == 6

    Column(
        modifier = modifier
            .alpha(
                if (isVisible) {
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            placeholder = stringResource(id = R.string.sign_up_third_phone_number_verification_code_placeholder),
            rightComponents = {
                Spacer(modifier = Modifier.width(4.dp))

                FBorderButton(
                    text = stringResource(id = R.string.sign_up_third_phone_number_check_code),
                    enable = enable,
                    onClick = {
                        if (enable) {
                            onVerificationCheckClick(verificationCode)
                        }
                    }
                )
            },
            tailComponent = {
                Text(
                    text = verificationTime,
                    style = fTextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 12.textDp,
                        lineHeight = 14.textDp,
                        color = FColor.ColorFF5841
                    )
                )
            },
            enabled = isVisible
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.sign_up_third_phone_number_check_code_guide),
            style = LocalTypography.current.label()
        )
    }
}
