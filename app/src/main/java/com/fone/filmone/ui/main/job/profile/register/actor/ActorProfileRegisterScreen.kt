package com.fone.filmone.ui.main.job.profile.register.actor

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.FBorderButton
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.tag.career.CareerTags
import com.fone.filmone.ui.common.tag.categories.CategoryTags
import com.fone.filmone.ui.main.job.common.LeftTitleTextField
import com.fone.filmone.ui.main.job.common.TextLimitComponent
import com.fone.filmone.ui.main.job.common.TextWithRequired
import com.fone.filmone.ui.main.job.common.TextWithRequiredTag
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.Pretendard
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import java.util.regex.Pattern

@Composable
fun ActorProfileRegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ActorProfileRegisterViewModel = hiltViewModel()
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
            TitleComponent(
                onBackClick = {
                    navController.popBackStack()
                },
                onRegisterClick = {

                }
            )

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                PictureComponent(pictureUriList = emptyList())

                Divider(thickness = 8.dp, color = FColor.Divider2)

                Spacer(modifier = Modifier.height(20.dp))

                UserInfoInputComponent(
                    name = uiState.name,
                    birthday = uiState.birthday,
                    genderTagEnable = uiState.genderTagEnable,
                    currentGender = uiState.gender,
                    hookingComments = uiState.hookingComments,
                    commentsTextLimit = uiState.commentsTextLimit,
                    height = uiState.height,
                    weight = uiState.weight,
                    email = uiState.email,
                    ability = uiState.ability,
                    sns = uiState.sns,
                    onNameChanged = viewModel::updateName,
                    onUpdateBirthday = viewModel::updateBirthday,
                    onUpdateGender = viewModel::updateGender,
                    onUpdateGenderTag = viewModel::updateGenderTag,
                    onUpdateComments = viewModel::updateComments,
                    onUpdateHeight = viewModel::updateHeight,
                    onUpdateWeight = viewModel::updateWeight,
                    onUpdateEmail = viewModel::updateEmail,
                    onUpdateAbility = viewModel::updateAbility,
                    onUpdateSns = viewModel::updateSns
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                DetailInputComponent(
                    detailInfo = uiState.detailInfo,
                    detailInfoTextLimit = uiState.detailInfoTextLimit,
                    onUpdateDetailInfo = viewModel::updateDetailInfo
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                CareerInputComponent(
                    currentCareer = uiState.career,
                    careerDetail = uiState.careerDetail,
                    careerDetailTextLimit = uiState.careerDetailTextLimit,
                    onUpdateCareer = viewModel::updateCareer,
                    onUpdateCareerDetail = viewModel::updateCareerDetail
                )

                Divider(thickness = 8.dp, color = FColor.Divider2)

                CategorySelectComponent(
                    categoryTagEnable = uiState.categoryTagEnable,
                    currentCategory = uiState.categories.toList(),
                    onCategoryTagClick = viewModel::updateCategoryTag,
                    onUpdateCategory = viewModel::updateCategory
                )

                RegisterButton(
                    buttonEnable = uiState.registerButtonEnable,
                    onRegisterButtonClick = viewModel::register
                )
            }
        }
    }
}

@Composable
private fun TitleComponent(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    FTitleBar(
        titleText = stringResource(id = R.string.profile_register_actor_title_text),
        leading = {
            Image(
                modifier = Modifier
                    .clickableSingleWithNoRipple { onBackClick() },
                imageVector = ImageVector.vectorResource(id = R.drawable.title_bar_back),
                contentDescription = null
            )
        },
        action = {
            Text(
                modifier = Modifier
                    .clickableSingle { onRegisterClick() },
                text = stringResource(id = R.string.profile_register_actor_title_right_button),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 15.textDp,
                    lineHeight = 20.textDp,
                    color = FColor.Secondary1Light
                )
            )
        }
    )
}

@Composable
private fun PictureComponent(
    modifier: Modifier = Modifier,
    pictureUriList: List<Uri>,
) {
    @Composable
    fun PictureRegisterItem(
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .size(80.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(
                    shape = RoundedCornerShape(5.dp),
                    color = if (pictureUriList.isEmpty()) {
                        FColor.DisableBase
                    } else {
                        FColor.Secondary1
                    }
                )
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.profile_register_camera_image),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = FColor.BgGroupedBase
                            )
                        ) {
                            append("${pictureUriList.size} /")
                        }
                        withStyle(
                            SpanStyle(
                                color = FColor.White
                            )
                        ) {
                            append(" 9")
                        }
                    },
                    fontWeight = FontWeight.W400,
                    fontFamily = Pretendard,
                    fontSize = 13.textDp,
                    lineHeight = 18.textDp,
                )
            }
        }
    }

    @Composable
    fun RegisteredPicture(
        modifier: Modifier = Modifier,
        imageUri: Uri,
        isRepresentativeItem: Boolean
    ) {
        val backgroundModifier = modifier
            .size(80.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(shape = RoundedCornerShape(5.dp), color = FColor.DisableBase)

        Box(modifier = backgroundModifier) {
            GlideImage(
                modifier = backgroundModifier,
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

            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.profile_register_close_button_16px),
                contentDescription = null
            )

            if (isRepresentativeItem) {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(100.dp))
                        .background(
                            shape = RoundedCornerShape(100.dp),
                            color = FColor.DimColorBasic
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.profile_register_actor_gallery_representative),
                        style = fTextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 12.textDp,
                            lineHeight = 17.textDp,
                            color = FColor.BgBase
                        )
                    )
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier) {
            PictureRegisterItem(modifier = Modifier.padding(start = 16.dp))

            LazyRow(
                modifier = Modifier,
                contentPadding = PaddingValues(horizontal = 7.dp)
            ) {
                itemsIndexed(pictureUriList) { index, uri ->
                    RegisteredPicture(imageUri = uri, isRepresentativeItem = index == 0)
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 5.dp),
            text = stringResource(id = R.string.profile_register_actor_gallery_hint),
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 12.textDp,
                lineHeight = 18.textDp,
                color = FColor.DisablePlaceholder
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun UserInfoInputComponent(
    modifier: Modifier = Modifier,
    name: String,
    birthday: String,
    genderTagEnable: Boolean,
    currentGender: Gender?,
    hookingComments: String,
    commentsTextLimit: Int,
    height: String,
    weight: String,
    email: String,
    ability: String,
    sns: String,
    onNameChanged: (String) -> Unit,
    onUpdateBirthday: (String) -> Unit,
    onUpdateGender: (Gender) -> Unit,
    onUpdateGenderTag: (Boolean) -> Unit,
    onUpdateComments: (String) -> Unit,
    onUpdateHeight: (String) -> Unit,
    onUpdateWeight: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdateAbility: (String) -> Unit,
    onUpdateSns: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        NameInputComponent(name = name, onNameChanged = onNameChanged)

        Spacer(modifier = Modifier.height(20.dp))

        HookingCommentsComponent(
            hookingComments = hookingComments,
            commentsTextLimit = commentsTextLimit,
            onUpdateComments = onUpdateComments
        )

        Spacer(modifier = Modifier.height(20.dp))

        BirthdayInputComponent(
            birthday = birthday,
            genderTagEnable = genderTagEnable,
            currentGender = currentGender,
            onUpdateBirthday = onUpdateBirthday,
            onUpdateGender = onUpdateGender,
            onUpdateGenderTag = onUpdateGenderTag,
        )

        Spacer(modifier = Modifier.height(20.dp))

        HeightWeightInputComponent(
            height = height,
            weight = weight,
            onUpdateHeight = onUpdateHeight,
            onUpdateWeight = onUpdateWeight
        )

        Spacer(modifier = Modifier.height(20.dp))

        EmailInputComponent(email = email, onUpdateEmail = onUpdateEmail)

        Spacer(modifier = Modifier.height(20.dp))

        AbilityInputComponent(ability = ability, onUpdateAbility = onUpdateAbility)

        Spacer(modifier = Modifier.height(20.dp))

        SnsInputComponent(sns = sns, onUpdateSns = onUpdateSns)

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun NameInputComponent(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit
) {
    LeftTitleTextField(
        modifier = modifier,
        title = stringResource(id = R.string.profile_register_actor_name_title),
        titleSpace = 28,
        placeholder = stringResource(id = R.string.profile_register_actor_name_placeholder),
        text = name,
        onValueChanged = onNameChanged
    )
}

@Composable
private fun HookingCommentsComponent(
    modifier: Modifier = Modifier,
    hookingComments: String,
    commentsTextLimit: Int,
    onUpdateComments: (String) -> Unit,
) {
    Column(
        modifier = modifier

    ) {
        TextWithRequired(
            title = stringResource(id = R.string.profile_register_actor_comment_title),
            isRequired = true
        )

        Spacer(modifier = Modifier.height(6.dp))

        FTextField(
            text = hookingComments,
            onValueChange = onUpdateComments,
            fixedHeight = 69.dp,
            maxLines = Int.MAX_VALUE,
            singleLine = false,
            placeholder = stringResource(id = R.string.profile_register_actor_comment_placeholder),
            textLimit = commentsTextLimit,
            bottomComponent = {
                TextLimitComponent(
                    modifier = Modifier
                        .align(Alignment.End),
                    currentTextSize = hookingComments.length,
                    maxTextSize = commentsTextLimit
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Default,
                keyboardType = KeyboardType.Text
            ),
        )
    }
}

@Composable
private fun BirthdayInputComponent(
    modifier: Modifier = Modifier,
    birthday: String,
    genderTagEnable: Boolean,
    currentGender: Gender?,
    onUpdateBirthday: (String) -> Unit,
    onUpdateGenderTag: (Boolean) -> Unit,
    onUpdateGender: (Gender) -> Unit,
) {
    Column(
        modifier = modifier

    ) {
        TextWithRequiredTag(
            title = stringResource(id = R.string.profile_register_actor_birth_title),
            tagTitle = stringResource(id = R.string.profile_register_actor_gender_irrelevant),
            isRequired = true,
            tagEnable = genderTagEnable,
            onTagClick = {
                onUpdateGenderTag(genderTagEnable.not())
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        FTextField(
            text = birthday,
            placeholder = stringResource(id = R.string.profile_register_actor_birth_placeholder),
            placeholderTextColor = FColor.DisablePlaceholder,
            onValueChange = onUpdateBirthday,
            pattern = Pattern.compile("^[\\d\\s-]+$"),
            onTextChanged = { before, after ->
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
            rightComponents = {
                Spacer(modifier = Modifier.width(8.dp))

                FBorderButton(
                    text = stringResource(id = R.string.gender_man),
                    enable = currentGender == Gender.MAN,
                    onClick = {
                        onUpdateGender(Gender.MAN)
                    }
                )

                Spacer(modifier = Modifier.width(6.dp))

                FBorderButton(
                    text = stringResource(id = R.string.gender_woman),
                    enable = currentGender == Gender.WOMAN,
                    onClick = {
                        onUpdateGender(Gender.WOMAN)
                    }
                )
            },
            textLimit = 10,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )
    }
}

@Composable
private fun HeightWeightInputComponent(
    modifier: Modifier = Modifier,
    height: String,
    weight: String,
    onUpdateHeight: (String) -> Unit,
    onUpdateWeight: (String) -> Unit,
) {
    val rightComponentTextStyle = fTextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 12.textDp,
        lineHeight = 17.textDp,
        color = FColor.DisablePlaceholder
    )

    Row(
        modifier = modifier

    ) {
        LeftTitleTextField(
            modifier = Modifier.weight(1f),
            title = stringResource(id = R.string.profile_register_actor_height_title),
            titleSpace = 12,
            text = height,
            tailComponent = {
                Text(
                    text = stringResource(id = R.string.profile_register_actor_height_postfix),
                    style = rightComponentTextStyle
                )
            },
            onValueChanged = onUpdateHeight,
        )

        Spacer(modifier = Modifier.width(34.dp))

        LeftTitleTextField(
            modifier = Modifier.weight(1f),
            title = stringResource(id = R.string.profile_register_actor_weight_title),
            titleSpace = 12,
            text = weight,
            tailComponent = {
                Text(
                    text = stringResource(id = R.string.profile_register_actor_weight_postfix),
                    style = rightComponentTextStyle
                )
            },
            onValueChanged = onUpdateWeight,
        )
    }
}

@Composable
private fun EmailInputComponent(
    modifier: Modifier = Modifier,
    email: String,
    onUpdateEmail: (String) -> Unit
) {
    LeftTitleTextField(
        modifier = modifier,
        title = stringResource(id = R.string.profile_register_actor_email_title),
        titleSpace = 13,
        text = email,
        placeholder = stringResource(id = R.string.profile_register_actor_email_placeholder),
        onValueChanged = onUpdateEmail
    )
}

@Composable
private fun AbilityInputComponent(
    modifier: Modifier = Modifier,
    ability: String,
    onUpdateAbility: (String) -> Unit
) {
    LeftTitleTextField(
        modifier = modifier,
        title = stringResource(id = R.string.profile_register_actor_speciality_title),
        titleSpace = 36,
        placeholder = stringResource(id = R.string.profile_register_actor_speciality_placeholder),
        isRequired = false,
        text = ability,
        onValueChanged = onUpdateAbility
    )
}

@Composable
private fun SnsInputComponent(
    modifier: Modifier = Modifier,
    sns: String,
    onUpdateSns: (String) -> Unit,
) {
    LeftTitleTextField(
        modifier = modifier,
        title = stringResource(id = R.string.profile_register_actor_sns_title),
        titleSpace = 36,
        placeholder = stringResource(id = R.string.profile_register_actor_sns_placeholder),
        isRequired = false,
        text = sns,
        onValueChanged = onUpdateSns
    )
}

@Composable
private fun DetailInputComponent(
    modifier: Modifier = Modifier,
    detailInfo: String,
    detailInfoTextLimit: Int,
    onUpdateDetailInfo: (String) -> Unit,
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequired(
            title = stringResource(id = R.string.profile_register_actor_detail_title),
            isRequired = true
        )

        Spacer(modifier = Modifier.height(6.dp))

        FTextField(
            text = detailInfo,
            placeholder = stringResource(id = R.string.profile_register_actor_detail_placeholder),
            placeholderTextColor = FColor.DisablePlaceholder,
            onValueChange = onUpdateDetailInfo,
            textLimit = detailInfoTextLimit,
            fixedHeight = 138.dp,
            bottomComponent = {
                Spacer(modifier = Modifier.height(2.dp))

                TextLimitComponent(
                    modifier = Modifier.align(Alignment.End),
                    currentTextSize = detailInfo.length,
                    maxTextSize = detailInfoTextLimit
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun CareerInputComponent(
    modifier: Modifier = Modifier,
    currentCareer: Career?,
    careerDetail: String,
    careerDetailTextLimit: Int,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerDetail: (String) -> Unit
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        CareerTags(currentCareers = currentCareer, onUpdateCareer = onUpdateCareer)

        Spacer(modifier = Modifier.height(10.dp))

        FTextField(
            text = careerDetail,
            onValueChange = onUpdateCareerDetail,
            placeholder = stringResource(id = R.string.profile_register_actor_career_placeholder),
            textLimit = careerDetailTextLimit,
            singleLine = false,
            maxLines = Int.MAX_VALUE,
            bottomComponent = {
                Spacer(modifier = Modifier.height(2.dp))

                TextLimitComponent(
                    modifier = Modifier.align(Alignment.End),
                    currentTextSize = careerDetail.length,
                    maxTextSize = careerDetailTextLimit
                )
            },
            fixedHeight = 139.dp
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun CategorySelectComponent(
    modifier: Modifier = Modifier,
    categoryTagEnable: Boolean,
    currentCategory: List<Category>,
    onCategoryTagClick: (Boolean) -> Unit,
    onUpdateCategory: (Category, Boolean) -> Unit
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(R.string.profile_register_actor_category_title),
            tagTitle = stringResource(id = R.string.profile_register_actor_category_all),
            isRequired = false,
            tagEnable = categoryTagEnable,
            onTagClick = {
                onCategoryTagClick(categoryTagEnable.not())
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        CategoryTags(
            currentCategories = currentCategory,
            onUpdateCategories = onUpdateCategory
        )

        Spacer(modifier = Modifier.height(58.dp))
    }
}

@Composable
private fun RegisterButton(
    modifier: Modifier = Modifier,
    buttonEnable: Boolean,
    onRegisterButtonClick: () -> Unit
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        FButton(
            title = stringResource(id = R.string.profile_register_actor_register_button),
            enable = buttonEnable,
            onClick = onRegisterButtonClick
        )

        Spacer(modifier = Modifier.height(38.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ActorProfileRegisterScreenPreview() {
    FilmOneTheme {
        ActorProfileRegisterScreen()
    }
}
