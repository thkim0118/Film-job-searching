package com.fone.filmone.ui.email.find

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.response.user.LoginType
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.phone.PhoneVerificationComponent
import com.fone.filmone.ui.common.phone.PhoneVerificationState
import com.fone.filmone.ui.email.find.component.TopGuideComponent
import com.fone.filmone.ui.email.find.model.FindScreenType
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun FindIdScreen(
    modifier: Modifier = Modifier,
    viewModel: FindViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(key1 = true) {
        viewModel.resetData()
    }

    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
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
                isRetransmitVisible = uiState.phoneVerificationState != PhoneVerificationState.Complete,
                onValueChanged = viewModel::updatePhoneNumber,
                onVerifyClick = viewModel::transmitVerificationCode,
                onVerificationCheckClick = { code ->
                    viewModel.checkVerificationCode(code, FindScreenType.FIND_ID)
                }
            )

            FindIdResultComponent(
                findIdLoginType = uiState.findIdLoginType,
                email = uiState.findIdEmail
            )

            Spacer(modifier = Modifier.height(38.dp))
        }

        GoLoginButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            enable = uiState.goLoginButtonEnable,
            onClick = {
                navController.popBackStack()
            }
        )
    }
}

@Composable
private fun FindIdResultComponent(
    modifier: Modifier = Modifier,
    findIdLoginType: LoginType?,
    email: String
) {
    if (findIdLoginType == null) {
        return
    }

    val title = if (findIdLoginType == LoginType.PASSWORD) {
        stringResource(id = R.string.find_id_email_result, email)
    } else {
        stringResource(id = R.string.find_id_social_result, findIdLoginType.titleRes)
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(modifier = modifier, text = title, style = LocalTypography.current.b2())
}

@Composable
private fun ColumnScope.GoLoginButton(
    modifier: Modifier = Modifier,
    enable: Boolean,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.weight(1f))

    FButton(
        modifier = modifier,
        title = stringResource(id = R.string.find_id_login_button),
        enable = enable
    ) {
        if (enable) {
            onClick()
        }
    }

    Spacer(modifier = Modifier.height(38.dp))
}
