package com.fone.filmone.ui.login

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.core.login.SNSLoginUtil
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.domain.model.signup.SocialLoginType
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.fShadow
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snsLoginUtil = viewModel.localSnsLoginUtil
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                snsLoginUtil.handleResult(
                    GoogleSignIn.getSignedInAccountFromIntent(result.data),
                    SocialLoginType.GOOGLE
                )
            }
        }
    )

    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .fillMaxSize()
            .padding(horizontal = 45.dp)
    ) {
        Spacer(modifier = Modifier.weight(110f))

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.login_fone_logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.login_title),
            style = LocalTypography.current.h1,
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.height(100.dp))

        KakaoLoginButton(
            onClick = {
                snsLoginUtil.login(
                    context,
                    SocialLoginType.KAKAO
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        NaverLoginButton(
            onClick = {
                snsLoginUtil.login(
                    context,
                    SocialLoginType.NAVER
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        GoogleLoginButton(
            onClick = {
                snsLoginUtil.login(
                    context,
                    SocialLoginType.GOOGLE,
                    launcher
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        AppleLoginButton(
            onClick = {
                snsLoginUtil.login(
                    context,
                    SocialLoginType.APPLE,
                    launcher
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(id = R.string.login_inquiry_title),
                style = fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    lineHeight = 14.32.sp,
                    color = FColor.Color9E9E9E
                )
            )

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                modifier = Modifier
                    .clickableSingleWithNoRipple {
                        navController.navigate(FOneDestinations.Inquiry.route)
                    },
                text = stringResource(id = R.string.login_inquiry_text),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 12.sp,
                    lineHeight = 14.4.sp,
                    color = FColor.Color666666
                )
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(183f))
    }
}

@Composable
private fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    LoginButtonContainer(
        modifier = modifier
            .clickableSingleWithNoRipple(onClick = {
                onClick.invoke()
            }),
        backgroundColor = FColor.Kakao,
        imageRes = R.drawable.login_social_kakao,
        titleRes = R.string.login_kakao_title,
        textColor = FColor.Color3C3C3C
    )
}

@Composable
private fun NaverLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    LoginButtonContainer(
        modifier = modifier
            .clickableSingleWithNoRipple {
                onClick.invoke()
            },
        backgroundColor = FColor.Naver,
        imageRes = R.drawable.login_social_naver,
        titleRes = R.string.login_naver_title,
        textColor = FColor.White
    )
}

@Composable
private fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    LoginButtonContainer(
        modifier = modifier
            .clickableSingleWithNoRipple {
                onClick.invoke()
            },
        backgroundColor = FColor.White,
        borderColor = FColor.ColorF5F5F5,
        imageRes = R.drawable.login_social_google,
        titleRes = R.string.login_google_title,
        textColor = FColor.Color757575
    )
}

@Composable
private fun AppleLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    LoginButtonContainer(
        modifier = modifier
            .clickableSingleWithNoRipple {
                onClick.invoke()
            },
        backgroundColor = FColor.Black,
        imageRes = R.drawable.login_social_apple,
        titleRes = R.string.login_apple_title,
        textColor = FColor.White
    )
}

@Composable
private fun LoginButtonContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    borderColor: Color? = null,
    @DrawableRes imageRes: Int,
    @StringRes titleRes: Int,
    textColor: Color
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fShadow(shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .background(color = backgroundColor, shape = RoundedCornerShape(5.dp))
            .border(
                width = if (borderColor != null) {
                    1.dp
                } else {
                    0.dp
                },
                color = borderColor ?: FColor.Transparent,
                shape = RoundedCornerShape(5.dp)
            )
            .height(42.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(46f))

        Image(
            imageVector = ImageVector.vectorResource(id = imageRes),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(34.dp))

        Text(
            text = stringResource(id = titleRes),
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                lineHeight = 16.8.sp,
                color = textColor
            ),
        )

        Spacer(modifier = Modifier.weight(68f))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    FilmOneTheme {
        LoginScreen()
    }
}