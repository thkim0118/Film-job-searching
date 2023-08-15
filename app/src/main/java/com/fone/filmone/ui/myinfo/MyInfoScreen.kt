package com.fone.filmone.ui.myinfo

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.core.image.ImageBase64Util
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.ui.common.FBorderButton
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.dialog.ProfileSettingDialog
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.fShadow
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.tag.categories.CategoryTags
import com.fone.filmone.ui.common.tag.job.JobTags
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MyInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MyInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()
    val scrollState = rememberScrollState()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
            viewModel.updateProfileEncodingState()
            coroutineScope.launch(Dispatchers.IO) {
                uri?.let {
                    val encodeString = ImageBase64Util.encodeToString(context, it)
                    viewModel.updateEncodedProfileString(encodeString)
                }
            }
        }
    )

    Scaffold(
        modifier = Modifier,
        snackbarHost = {
            FToast(baseViewModel = viewModel, hostState = it)
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .defaultSystemBarPadding()
                    .toastPadding()
            ) {
                FTitleBar(
                    titleType = TitleType.Back,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        ProfileImageComponent(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            onClick = {
                                viewModel.updateDialog(MyInfoDialogState.ProfileSetting)
                            },
                            imageUri = imageUri,
                            uiState = uiState
                        )

                        Spacer(modifier = Modifier.height(23.dp))

                        NicknameComponent(
                            onDuplicateCheckClick = viewModel::checkNicknameDuplicate,
                            onUpdateNickname = viewModel::updateNickname,
                            uiState = uiState
                        )

                        Spacer(modifier = Modifier.height(42.dp))

                        JobComponent(
                            onUpdateJob = viewModel::updateJob,
                            uiState = uiState
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        InterestsComponent(
                            onUpdateInterests = viewModel::updateInterest,
                            uiState = uiState
                        )

                        Spacer(modifier = Modifier.height(141.dp))
                    }

                    EditButton(
                        uiState = uiState,
                        onClick = {
                            viewModel.updateUserInfo(
                                onSuccess = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            DialogScreen(
                dialogState = dialogState,
                launcher = launcher,
                onDefaultImageRequestClick = {
                    viewModel.updateDefaultProfile()
                    imageUri = null
                },
                onDismiss = viewModel::clearDialog
            )
        }
    }
}

@Composable
private fun ProfileImageComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageUri: Uri?,
    uiState: MyInfoUiState
) {
    val profileUrl = uiState.profileUrl ?: ""

    Box(
        modifier = modifier
            .size(65.dp)
            .clickableWithNoRipple {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        when {
            imageUri != null -> {
                ProfileImage(imageModel = imageUri)
            }

            profileUrl.isNotEmpty() -> {
                ProfileImage(imageModel = profileUrl)
            }

            else -> {
                Image(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    imageVector = ImageVector.vectorResource(id = R.drawable.default_profile),
                    contentDescription = null
                )
            }
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
private fun ProfileImage(
    modifier: Modifier = Modifier,
    imageModel: Any?
) {
    GlideImage(
        modifier = modifier
            .size(65.dp)
            .clip(CircleShape),
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = FColor.Gray700,
            durationMillis = 350,
            dropOff = 0.65f,
            tilt = 20f
        ),

        imageModel = imageModel,
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

@Composable
private fun NicknameComponent(
    modifier: Modifier = Modifier,
    onDuplicateCheckClick: () -> Unit,
    onUpdateNickname: (String) -> Unit,
    uiState: MyInfoUiState
) {
    val nickname = uiState.nickname
    val enableDuplicate = uiState.isEnableDuplicate

    FTextField(
        text = nickname,
        modifier = modifier,
        onValueChange = onUpdateNickname,
        topComponent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.my_info_nickname_title),
                    style = LocalTypography.current.subtitle2(),
                    color = FColor.TextPrimary
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(id = R.string.my_info_nickname_subtitle),
                    style = fTextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 12.textDp,
                        lineHeight = 14.textDp,
                        color = FColor.DisablePlaceholder
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        },
        rightComponents = {
            Spacer(modifier = Modifier.width(4.dp))

            FBorderButton(
                text = stringResource(id = R.string.my_info_nickname_duplicate_title),
                enable = enableDuplicate,
                onClick = {
                    if (enableDuplicate) {
                        onDuplicateCheckClick()
                    }
                }
            )
        }
    )
}

@Composable
private fun JobComponent(
    modifier: Modifier = Modifier,
    uiState: MyInfoUiState,
    onUpdateJob: (Job) -> Unit
) {
    val currentJob = uiState.job

    Text(
        text = stringResource(id = R.string.my_info_job_choice_title),
        style = LocalTypography.current.subtitle2(),
        color = FColor.TextPrimary
    )

    Spacer(modifier = Modifier.height(8.dp))

    JobTags(
        modifier = modifier,
        currentJob = currentJob,
        onUpdateJob = onUpdateJob
    )
}

@Composable
private fun InterestsComponent(
    modifier: Modifier = Modifier,
    uiState: MyInfoUiState,
    onUpdateInterests: (Category, Boolean) -> Unit
) {
    val currentInterests = uiState.interests

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.my_info_favorite_choice_title),
            style = LocalTypography.current.subtitle1(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = stringResource(id = R.string.my_info_favorite_choice_subtitle),
            style = LocalTypography.current.label(),
            color = FColor.DisablePlaceholder
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    CategoryTags(
        currentCategories = currentInterests,
        onUpdateCategories = onUpdateInterests
    )
}

@Composable
private fun ColumnScope.EditButton(
    uiState: MyInfoUiState,
    onClick: () -> Unit
) {
    val enable = uiState.isEnableEditButton &&
        uiState.isEnableDuplicate.not() &&
        uiState.isUpdateProfileEncoding.not()

    Spacer(modifier = Modifier.weight(1f))

    FButton(
        title = stringResource(id = R.string.my_info_button_title),
        enable = enable
    ) {
        if (enable) {
            onClick()
        }
    }
}

@Composable
private fun DialogScreen(
    dialogState: MyInfoDialogState,
    launcher: ManagedActivityResultLauncher<String, Uri?>,
    onDefaultImageRequestClick: () -> Unit,
    onDismiss: () -> Unit
) {
    when (dialogState) {
        MyInfoDialogState.Clear -> Unit
        MyInfoDialogState.ProfileSetting -> ProfileSettingDialog(
            onAlbumClick = {
                launcher.launch("image/*")
                onDismiss()
            },
            onDefaultProfileClick = {
                onDefaultImageRequestClick()
                onDismiss()
            },
            onCancelButtonClick = onDismiss
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileImageComponentPreview() {
    FilmOneTheme {
        ProfileImageComponent(uiState = MyInfoUiState(), onClick = {}, imageUri = null)
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknameComponentPreview() {
    FilmOneTheme {
        NicknameComponent(
            onDuplicateCheckClick = {},
            onUpdateNickname = {},
            uiState = MyInfoUiState()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JobComponentPreview() {
    FilmOneTheme {
        Column {
            JobComponent(
                onUpdateJob = {},
                uiState = MyInfoUiState()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InterestsComponentPreview() {
    FilmOneTheme {
        Column {
            InterestsComponent(
                onUpdateInterests = { _, _ -> },
                uiState = MyInfoUiState()
            )
        }
    }
}
