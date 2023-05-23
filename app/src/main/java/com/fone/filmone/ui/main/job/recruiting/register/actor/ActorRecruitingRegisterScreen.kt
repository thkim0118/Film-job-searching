package com.fone.filmone.ui.main.job.recruiting.register.actor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FBorderButton
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.tag.career.CareerTags
import com.fone.filmone.ui.common.tag.categories.CategoryTags
import com.fone.filmone.ui.main.job.common.TagComponent
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import com.fone.filmone.ui.theme.Pretendard

@Composable
fun ActorRecruitingRegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding()
            .verticalScroll(scrollState)
    ) {
        TitleComponent(
            onBackClick = {
                navController.popBackStack()
            }
        )

        Step1Component()

        Divider(thickness = 8.dp, color = FColor.Divider2)

        Step2Component()

        Divider(thickness = 8.dp, color = FColor.Divider2)

        Step3Component()

        Divider(thickness = 8.dp, color = FColor.Divider2)

        Step4Component()

        Divider(thickness = 8.dp, color = FColor.Divider2)

        Step5Component()
    }
}

@Composable
private fun TitleComponent(
    onBackClick: () -> Unit
) {
    FTitleBar(
        titleText = stringResource(id = R.string.recruiting_register_actor_title_text),
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
                    .clickableSingle { },
                text = stringResource(id = R.string.recruiting_register_actor_title_right_button),
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
private fun Step1Component() {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_step_1_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light
        )

        Spacer(modifier = Modifier.height(9.dp))

        ContentTitleInputComponent()

        Spacer(modifier = Modifier.height(20.dp))

        CategorySelectComponent()
    }

    Divider(thickness = 8.dp, color = FColor.Divider2)

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        RecruitmentInputComponent()

        Spacer(modifier = Modifier.height(20.dp))

        AgeComponent(
            ageRange = 1f..70f,
            onUpdateAgeReset = {},
            onUpdateAgeRange = {}
        )
    }

    Divider(thickness = 8.dp, color = FColor.Divider2)

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        CareerInputComponent()

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step2Component() {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_step_2_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light
        )

        Spacer(modifier = Modifier.height(9.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_actor_production_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_actor_production_placeholder),
            text = "",
            onValueChanged = {}
        )

        Spacer(modifier = Modifier.height(10.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_actor_work_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_actor_work_placeholder),
            text = "",
            onValueChanged = {}
        )

        Spacer(modifier = Modifier.height(10.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_actor_director_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_actor_director_placeholder),
            text = "",
            onValueChanged = {}
        )

        Spacer(modifier = Modifier.height(10.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_actor_genre_title),
            titleSpace = 28,
            placeholder = stringResource(id = R.string.recruiting_register_actor_genre_placeholder),
            text = "",
            onValueChanged = {}
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_log_line_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_log_line_private),
            isRequired = false,
            tagEnable = false,
            onTagClick = {

            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        FTextField(
            onValueChange = {},
            fixedHeight = 134.dp,
            placeholder = stringResource(id = R.string.recruiting_register_actor_log_line_placeholder),
            bottomComponent = {
                TextLimitComponent(
                    modifier = Modifier
                        .align(Alignment.End),
                    currentTextSize = 0,
                    maxTextSize = 200
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step3Component(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_step_3_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_location_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_location_undetermined),
            isRequired = false,
            tagEnable = true,
            onTagClick = {}
        )

        Spacer(modifier = Modifier.height(5.dp))

        FTextField(
            onValueChange = {},
            placeholder = stringResource(id = R.string.recruiting_register_actor_location_placeholder),
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_period_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_period_undetermined),
            isRequired = false,
            tagEnable = true,
            onTagClick = {}
        )

        Spacer(modifier = Modifier.height(5.dp))

        FTextField(
            onValueChange = {},
            placeholder = stringResource(id = R.string.recruiting_register_actor_period_placeholder),
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_pay_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_pay_undetermined),
            isRequired = false,
            tagEnable = true,
            onTagClick = {}
        )

        Spacer(modifier = Modifier.height(5.dp))

        FTextField(
            onValueChange = {},
            placeholder = stringResource(id = R.string.recruiting_register_actor_pay_placeholder),
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step4Component(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle()) {
                    append(stringResource(id = R.string.recruiting_register_actor_step_4_title))
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.W500,
                        color = FColor.Error
                    )
                ) {
                    append("*")
                }
            },
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light,
            fontSize = 15.textDp,
            lineHeight = 20.textDp,
        )

        Spacer(modifier = Modifier.height(5.dp))

        FTextField(
            onValueChange = {},
            textLimit = 500,
            fixedHeight = 134.dp,
            placeholder = stringResource(id = R.string.recruiting_register_actor_step_4_placeholder),
            bottomComponent = {
                TextLimitComponent(
                    modifier = Modifier
                        .align(Alignment.End),
                    currentTextSize = 0,
                    maxTextSize = 500
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Step5Component(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_step_5_title),
            style = LocalTypography.current.h4(),
            color = FColor.Secondary1Light
        )

        Spacer(modifier = Modifier.height(13.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_actor_manager_title),
            titleSpace = 13,
            text = "",
            onValueChanged = {},
        )

        Spacer(modifier = Modifier.height(20.dp))

        LeftTitleTextField(
            title = stringResource(id = R.string.recruiting_register_actor_manager_title),
            titleSpace = 13,
            text = "",
            onValueChanged = {},
        )

        Spacer(modifier = Modifier.height(56.dp))

        FButton(
            title = stringResource(id = R.string.recruiting_register_actor_register_button_title),
            enable = false,
            onClick = {}
        )

        Spacer(modifier = Modifier.height(38.dp))
    }
}

@Composable
private fun CareerInputComponent() {
    TextWithRequiredTag(
        title = stringResource(id = R.string.recruiting_register_actor_career_title),
        tagTitle = stringResource(
            id = R.string.recruiting_register_actor_career_irrelevant
        ),
        isRequired = false,
        tagEnable = false, // TODO 설정
        onTagClick = {

        }
    )

    Spacer(modifier = Modifier.height(6.dp))

    CareerTags(
        currentCareer = null,
        onUpdateCareer = {

        }
    )
}

@Composable
private fun RecruitmentInputComponent() {
    TextWithRequiredTag(
        title = stringResource(id = R.string.recruiting_register_actor_deadline_title),
        tagTitle = stringResource(id = R.string.recruiting_register_actor_always_recruiting),
        isRequired = true,
        tagEnable = false,
        onTagClick = {}
    )

    Spacer(modifier = Modifier.height(6.dp))

    FTextField(
        onValueChange = {},
        placeholder = stringResource(id = R.string.recruiting_register_actor_deadline_placeholder),
    )

    Spacer(modifier = Modifier.height(20.dp))

    FTextField(
        onValueChange = {},
        leftComponents = {
            Text(
                text = stringResource(id = R.string.recruiting_register_actor_target_role_title),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 15.textDp,
                    lineHeight = 20.textDp,
                    color = FColor.TextPrimary
                )
            )

            Spacer(modifier = Modifier.width(19.dp))
        },
        placeholder = stringResource(id = R.string.recruiting_register_actor_target_role_placeholder),
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextWithRequiredTag(
        title = stringResource(id = R.string.recruiting_register_actor_target_personal),
        tagTitle = stringResource(id = R.string.recruiting_register_actor_gender_irrelevant),
        isRequired = true,
        tagEnable = false,
        onTagClick = {}
    )

    Spacer(modifier = Modifier.height(6.dp))

    FTextField(
        onValueChange = {},
        rightComponents = {
            Spacer(modifier = Modifier.width(8.dp))

            FBorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_gender_man),
                enable = false,// uiState.gender == Gender.MAN,
                onClick = {
//                        onUpdateGender(Gender.MAN)
                }
            )

            Spacer(modifier = Modifier.width(6.dp))

            FBorderButton(
                text = stringResource(id = R.string.sign_up_second_birthday_gender_woman),
                enable = false,//uiState.gender == Gender.WOMAN,
                onClick = {
//                        onUpdateGender(Gender.WOMAN)
                }
            )
        }
    )
}

@Composable
private fun CategorySelectComponent() {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextWithRequired(
            title = stringResource(id = R.string.profile_register_actor_category_title),
            isRequired = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = R.string.recruiting_register_actor_category_subtitle),
            style = fTextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 12.textDp,
                lineHeight = 17.textDp,
                color = FColor.DisablePlaceholder
            )
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    CategoryTags(
        currentCategories = listOf(),
        onUpdateCategories = { category, enable -> }
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun ContentTitleInputComponent() {
    TextWithRequired(
        title = stringResource(id = R.string.recruiting_register_actor_title),
        isRequired = true
    )

    Spacer(modifier = Modifier.height(8.dp))

    FTextField(
        onValueChange = {},
        tailComponent = {
            TextLimitComponent(
                currentTextSize = 0,
                maxTextSize = 50
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AgeComponent(
    modifier: Modifier = Modifier,
    ageRange: ClosedFloatingPointRange<Float>,
    onUpdateAgeReset: () -> Unit,
    onUpdateAgeRange: (ClosedFloatingPointRange<Float>) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        TextWithRequiredTag(
            title = stringResource(id = R.string.recruiting_register_actor_age_title),
            tagTitle = stringResource(id = R.string.recruiting_register_actor_gender_irrelevant),
            isRequired = false,
            tagEnable = false,
            onTagClick = onUpdateAgeReset
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(
                id = R.string.job_filter_age_range,
                ageRange.start.toInt(),
                ageRange.endInclusive.toInt()
            ),
            style = LocalTypography.current.b2(),
            color = FColor.TextSecondary
        )

        RangeSlider(
            modifier = Modifier.padding(0.dp),
            value = ageRange,
            onValueChange = onUpdateAgeRange,
            valueRange = 1f..70f,
            steps = 70,
            colors = SliderDefaults.colors(
                thumbColor = FColor.Primary,
                activeTrackColor = FColor.Primary,
                inactiveTrackColor = FColor.DisablePlaceholder,
                activeTickColor = FColor.Transparent,
                inactiveTickColor = FColor.Transparent,
            ),
        )
    }
}

@Composable
private fun TextWithRequired(
    modifier: Modifier = Modifier,
    title: String,
    isRequired: Boolean
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.W500,
                    color = FColor.TextPrimary
                )
            ) {
                append(title)
            }
            if (isRequired) {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.W500,
                        color = FColor.Error
                    )
                ) {
                    append(" *")
                }
            }
        },
        fontFamily = Pretendard,
        fontSize = 15.textDp,
        lineHeight = 20.textDp,
    )
}

@Composable
private fun TextWithRequiredTag(
    modifier: Modifier = Modifier,
    title: String,
    tagTitle: String,
    isRequired: Boolean,
    tagEnable: Boolean,
    onTagClick: () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        TextWithRequired(
            title = title,
            isRequired = isRequired
        )

        Spacer(modifier = Modifier.weight(1f))

        TagComponent(
            title = tagTitle,
            enable = tagEnable,
            onClick = {
                onTagClick()
            }
        )
    }
}

@Composable
private fun LeftTitleTextField(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String = "",
    titleSpace: Int,
    text: String,
    onValueChanged: (String) -> Unit
) {
    FTextField(
        modifier = modifier,
        text = text,
        onValueChange = onValueChanged,
        leftComponents = {
            TextWithRequired(
                title = title,
                isRequired = true
            )

            Spacer(modifier = Modifier.width(titleSpace.dp))
        },
        placeholder = placeholder
    )
}

@Composable
private fun TextLimitComponent(
    modifier: Modifier = Modifier,
    currentTextSize: Int,
    maxTextSize: Int,
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(color = FColor.TextSecondary)
            ) {
                append(currentTextSize.toString())
            }

            withStyle(
                style = SpanStyle(color = FColor.DisableBase)
            ) {
                append("/")
            }

            withStyle(
                style = SpanStyle(color = FColor.DisableBase)
            ) {
                append(maxTextSize.toString())
            }
        },
        fontWeight = FontWeight.W400,
        fontSize = 12.textDp,
        lineHeight = 17.textDp
    )
}
