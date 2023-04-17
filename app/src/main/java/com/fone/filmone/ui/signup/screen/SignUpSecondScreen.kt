package com.fone.filmone.ui.signup.screen

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.core.image.ImageBase64Util
import com.fone.filmone.domain.model.signup.Gender
import com.fone.filmone.ui.common.*
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.fShadow
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.signup.SignUpSecondUiState
import com.fone.filmone.ui.signup.SignUpSecondViewModel
import com.fone.filmone.ui.signup.components.IndicatorType
import com.fone.filmone.ui.signup.components.SignUpIndicator
import com.fone.filmone.ui.signup.model.SignUpVo
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@Composable
fun SignUpSecondScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    signUpVo: SignUpVo,
    viewModel: SignUpSecondViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    BackHandler(enabled = true) {
        navigateLoginScreen(navController)
    }

    Box(
        modifier = modifier
            .defaultSystemBarPadding()
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            FTitleBar(
                titleType = TitleType.Back,
                titleText = stringResource(id = R.string.sign_up_title_text),
                onBackClick = {
                    navigateLoginScreen(navController)
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    SignUpIndicator(indicatorType = IndicatorType.Second)

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = stringResource(id = R.string.sign_up_second_title),
                        style = LocalTypography.current.h1
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    NicknameComponent(
                        uiState = uiState,
                        onUpdateNickname = viewModel::updateNickname,
                        onCheckDuplicateNickname = viewModel::checkNicknameDuplication
                    )

                    Spacer(modifier = Modifier.height(23.dp))

                    BirthdaySexComponent(
                        uiState = uiState,
                        onUpdateBirthday = viewModel::updateBirthDay,
                        onUpdateGender = viewModel::updateGender
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    ProfileComponent(
                        onUpdateProfileEncoding = viewModel::updateProfileUploadState,
                        onUpdateProfileImage = viewModel::updateProfileImage
                    )

                    Spacer(modifier = Modifier.height(137.dp))
                }

                NextButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    signUpVo = signUpVo,
                    uiState = uiState
                )

                Spacer(modifier = Modifier.height(38.dp))
            }
        }

        FToast(baseViewModel = viewModel)
    }
}

private fun navigateLoginScreen(navController: NavHostController) {
    navController.navigate(FOneDestinations.Login.route) {
        popUpTo(0)
    }
}

@Composable
private fun NicknameComponent(
    uiState: SignUpSecondUiState,
    onUpdateNickname: (String) -> Unit,
    onCheckDuplicateNickname: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = uiState.isNicknameChecked) {
        if (uiState.isNicknameChecked) {
            focusManager.clearFocus()
        }
    }

    Row {
        FTextField(
            text = uiState.nickname,
            placeholder = stringResource(id = R.string.sign_up_second_nickname_placeholder),
            onValueChange = onUpdateNickname,
            pattern = Pattern.compile("^[ㄱ-ㅣ가-힣a-zA-Z\\d\\s]+$"),
            topText = {
                Row {
                    Text(
                        text = stringResource(id = R.string.sign_up_second_nickname_title),
                        style = LocalTypography.current.subtitle1
                    )

                    Text(
                        text = " *",
                        style = fTextStyle(
                            fontWeight = FontWeight.W500,
                            fontSize = 16.sp,
                            lineHeight = 19.2.sp,
                            color = FColor.Error
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
            },
            borderButtons = listOf(
                BorderButton(
                    text = stringResource(
                        id = if (uiState.isNicknameChecked) {
                            R.string.sign_up_second_nickname_check_duplicate_complete
                        } else {
                            R.string.sign_up_second_nickname_check_duplicate
                        }
                    ),
                    enable = if (uiState.isNicknameChecked) {
                        false
                    } else {
                        uiState.nickname.isNotEmpty()
                    },
                    onClick = {
                        if (uiState.isNicknameChecked.not()) {
                            onCheckDuplicateNickname()
                        }
                    }
                )
            ),
            enabled = uiState.isNicknameChecked.not(),
            bottomType = BottomType.Error(
                errorText = stringResource(id = R.string.sign_up_second_nickname_error_title),
                isError = uiState.isNicknameDuplicated
            )
        )
    }
}

@Composable
private fun BirthdaySexComponent(
    uiState: SignUpSecondUiState,
    onUpdateBirthday: (String) -> Unit,
    onUpdateGender: (Gender) -> Unit,
) {
    FTextField(
        text = uiState.birthday,
        placeholder = stringResource(id = R.string.sign_up_second_birthday_sex_placeholder),
        onValueChange = onUpdateBirthday,
        pattern = Pattern.compile("^[\\d\\s-]+$"),
        autoCompletion = { before, after ->
            if (before.text.length < after.text.length) {
                when (after.text.length) {
                    5, 8 -> after.copy(
                        text = "${before.text}-${after.text.last()}",
                        selection = TextRange(after.text.length + 1)
                    )
                    else -> after
                }
            } else {
                when (after.text.length) {
                    5, 8 -> after.copy(
                        text = after.text.dropLast(1),
                    )
                    else -> after
                }
            }
        },
        textLimit = 10,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        topText = {
            Row {
                Text(
                    text = stringResource(id = R.string.sign_up_second_birthday_sex_title),
                    style = LocalTypography.current.subtitle1
                )

                Text(
                    text = " *",
                    style = fTextStyle(
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        lineHeight = 19.2.sp,
                        color = FColor.Error
                    )
                )

                Text(
                    text = stringResource(id = R.string.sign_up_second_birthday_sex_subtitle),
                    style = LocalTypography.current.label,
                    color = FColor.DisablePlaceholder
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        },
        borderButtons = listOf(
            BorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_sex_man),
                enable = uiState.gender == Gender.MAN,
                onClick = {
                    onUpdateGender(Gender.MAN)
                }
            ),
            BorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_sex_woman),
                enable = uiState.gender == Gender.WOMAN,
                onClick = {
                    onUpdateGender(Gender.WOMAN)
                }
            )
        ),
    )
}

@Composable
private fun ProfileComponent(
    onUpdateProfileEncoding: () -> Unit,
    onUpdateProfileImage: (String) -> Unit
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
            onUpdateProfileEncoding()
            coroutineScope.launch(Dispatchers.IO) {
                uri?.let {
                    val encodeString = ImageBase64Util.encodeToString(context, it)
                    onUpdateProfileImage(encodeString)
                }
            }
        }
    )

    Text(
        text = stringResource(id = R.string.sign_up_second_profile_title),
        style = LocalTypography.current.subtitle1
    )

    Spacer(modifier = Modifier.height(2.dp))

    Text(
        text = stringResource(id = R.string.sign_up_second_profile_subtitle),
        style = LocalTypography.current.label
    )

    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .clickableWithNoRipple {
                launcher.launch("image/*")
            }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(108.dp)
                .fShadow(shape = CircleShape)
        )
        if (imageUri == null) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.default_profile),
                contentDescription = null
            )
        } else {
            GlideImage(
                modifier = Modifier
                    .size(106.dp)
                    .clip(CircleShape),
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = FColor.Gray700,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),

                imageModel = imageUri,
                contentScale = ContentScale.Crop,
                failure = {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center),
                        imageVector = ImageVector.vectorResource(id = R.drawable.default_profile),
                        contentDescription = null
                    )
                }
            )
        }
        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fShadow(
                    shape = CircleShape,
                    spotColor = FColor.Primary,
                    ambientColor = FColor.Primary
                ),
            imageVector = ImageVector.vectorResource(id = R.drawable.default_profile_camera),
            contentDescription = null
        )
    }
}

@Composable
private fun ColumnScope.NextButton(
    modifier: Modifier = Modifier,
    signUpVo: SignUpVo,
    uiState: SignUpSecondUiState
) {
    val enable =
        uiState.isNicknameChecked && uiState.isBirthDayChecked && uiState.isProfileUploading.not() && uiState.gender != null

    Spacer(modifier = Modifier.weight(1f))

    FButton(
        modifier = modifier,
        title = stringResource(id = R.string.sign_up_next_title),
        enable = enable,
        onClick = {
            if (enable) {
                FOneNavigator.navigateTo(
                    FOneDestinations.SignUpThird.getRouteWithArg(
                        signUpVo.copy(
                            nickname = uiState.nickname,
                            birthday = uiState.birthday,
                            gender = uiState.gender?.name ?: Gender.IRRELEVANT.name,
                            profileUrl = uiState.profileUrl
                        )
                    )
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileComponentPreview() {
    FilmOneTheme {
        Column {
            ProfileComponent(onUpdateProfileImage = {}, onUpdateProfileEncoding = {})
        }
    }
}