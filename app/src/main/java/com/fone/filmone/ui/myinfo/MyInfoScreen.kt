package com.fone.filmone.ui.myinfo

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.domain.model.signup.Interests
import com.fone.filmone.domain.model.signup.Job
import com.fone.filmone.ui.common.*
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.fShadow
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun MyInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val scrollState = rememberScrollState()

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
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(23.dp))

                NicknameComponent(
                    onDuplicateCheckClick = {

                    }
                )

                Spacer(modifier = Modifier.height(42.dp))

                JobComponent(
                    currentJob = Job.HUNTER,
                    onUpdateJob = {}
                )

                Spacer(modifier = Modifier.height(40.dp))

                InterestsComponent(
                    currentInterests = listOf(),
                    onUpdateInterests = { _, _ -> }
                )

                Spacer(modifier = Modifier.height(141.dp))
            }

            EditButton()

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun ProfileImageComponent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(65.dp)
            .clickableWithNoRipple {
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.TopStart),
            imageVector = ImageVector.vectorResource(id = R.drawable.default_profile),
            contentDescription = null
        )
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
private fun NicknameComponent(
    modifier: Modifier = Modifier,
    onDuplicateCheckClick: () -> Unit
) {
    FTextField(
        modifier = modifier,
        onValueChange = {

        },
        topText = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.my_info_nickname_title),
                    style = LocalTypography.current.subtitle2,
                    color = FColor.TextPrimary
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(id = R.string.my_info_nickname_subtitle),
                    style = fTextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
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
                enable = false,
                onClick = {
                    onDuplicateCheckClick()
                }
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun JobComponent(
    modifier: Modifier = Modifier,
    currentJob: Job?,
    onUpdateJob: (Job) -> Unit
) {
    Text(
        text = stringResource(id = R.string.my_info_job_choice_title),
        style = LocalTypography.current.subtitle2,
        color = FColor.TextPrimary
    )

    Spacer(modifier = Modifier.height(8.dp))

    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        Job.values().forEach { job ->
            JobTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                job = job,
                isSelected = currentJob == job,
                onClick = onUpdateJob
            )
        }
    }
}

@Composable
private fun JobTag(
    modifier: Modifier = Modifier,
    job: Job,
    isSelected: Boolean,
    onClick: (Job) -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(90.dp))
            .background(
                color = if (isSelected) {
                    FColor.Red50
                } else {
                    FColor.BgGroupedBase
                },
                shape = RoundedCornerShape(90.dp)
            )
            .clickable { onClick(job) },
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = job.name,
            style = if (isSelected) {
                fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    color = FColor.Primary
                )
            } else {
                fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    color = FColor.DisablePlaceholder
                )
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InterestsComponent(
    modifier: Modifier = Modifier,
    currentInterests: List<Interests>,
    onUpdateInterests: (Interests, Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.my_info_favorite_choice_title),
            style = LocalTypography.current.subtitle1,
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = stringResource(id = R.string.my_info_favorite_choice_subtitle),
            style = LocalTypography.current.label,
            color = FColor.DisablePlaceholder
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    FlowRow(
        modifier = Modifier,
        maxItemsInEachRow = 3
    ) {
        Interests.values().forEach { interests ->
            InterestsTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                interests = interests,
                isSelected = currentInterests.find { it == interests } != null,
                onClick = onUpdateInterests
            )
        }
    }
}

@Composable
private fun InterestsTag(
    modifier: Modifier = Modifier,
    interests: Interests,
    isSelected: Boolean,
    onClick: (Interests, Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(90.dp))
            .background(
                color = if (isSelected) {
                    FColor.Red50
                } else {
                    FColor.BgGroupedBase
                },
                shape = RoundedCornerShape(90.dp)
            )
            .clickable { onClick(interests, isSelected.not()) },
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = interests.title,
            style = if (isSelected) {
                fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    color = FColor.Primary
                )
            } else {
                fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 16.8.sp,
                    color = FColor.DisablePlaceholder
                )
            }
        )
    }
}

@Composable
private fun ColumnScope.EditButton() {
    Spacer(modifier = Modifier.weight(1f))

    FButton(
        title = stringResource(id = R.string.my_info_button_title),
        enable = false
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileImageComponentPreview() {
    FilmOneTheme {
        ProfileImageComponent()
    }
}

@Preview(showBackground = true)
@Composable
fun NicknameComponentPreview() {
    FilmOneTheme {
        NicknameComponent(onDuplicateCheckClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun JobComponentPreview() {
    FilmOneTheme {
        Column {
            JobComponent(currentJob = Job.HUNTER, onUpdateJob = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InterestsComponentPreview() {
    FilmOneTheme {
        Column {
            InterestsComponent(currentInterests = listOf(), onUpdateInterests = { _, _ -> })
        }
    }
}