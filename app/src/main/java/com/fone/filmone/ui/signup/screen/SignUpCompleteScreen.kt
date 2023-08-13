package com.fone.filmone.ui.signup.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.signup.SignUpCompleteViewModel
import com.fone.filmone.ui.theme.FColor

@Composable
fun SignUpCompleteScreen(
    modifier: Modifier = Modifier,
    accessToken: String,
    email: String,
    loginType: String,
    password: String?,
    nickname: String,
    navController: NavHostController,
    viewModel: SignUpCompleteViewModel = hiltViewModel()
) {
    BackHandler(true) {
        navigateLoginScreen(navController)
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = {
            FToast(baseViewModel = viewModel, hostState = it)
        }
    ) {
        Box(
            modifier = modifier
                .defaultSystemBarPadding()
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    imageVector = ImageVector.vectorResource(id = R.drawable.sign_up_complete_check),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.W700,
                                fontSize = 19.textDp,
                                color = FColor.TextPrimary,
                            )
                        ) {
                            append(nickname)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 19.textDp,
                                color = FColor.TextPrimary
                            )
                        ) {
                            append(stringResource(id = R.string.sign_up_complete_title_1))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.W700,
                                fontSize = 19.textDp,
                                color = FColor.TextPrimary,
                            )
                        ) {
                            append(stringResource(id = R.string.sign_up_complete_title_2))
                        }
                    },
                    lineHeight = 26.textDp,
                    textAlign = TextAlign.Center
                )
            }

            FButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 38.dp, start = 20.dp, end = 20.dp),
                title = stringResource(id = R.string.sign_up_complete_button_title),
                enable = true
            ) {
                viewModel.signIn(
                    accessToken = accessToken,
                    email = email,
                    loginType = loginType,
                    password = password
                )
            }
        }
    }
}

private fun navigateLoginScreen(navController: NavHostController) {
    navController.navigate(FOneDestinations.Login.route) {
        popUpTo(0)
    }
}

