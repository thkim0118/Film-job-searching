package com.fone.filmone.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.TitleBar
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.signup.components.IndicatorType
import com.fone.filmone.ui.signup.components.SignUpIndicator
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun SignUpFirstScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TitleBar(
            titleType = TitleType.Back,
            titleText = stringResource(id = R.string.sign_up_title_text)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            SignUpIndicator(indicatorType = IndicatorType.First)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(id = R.string.sign_up_first_title),
                style = LocalTypography.current.h1
            )

            Spacer(modifier = Modifier.height(32.dp))

            ChoiceTitle(
                title = stringResource(id = R.string.sign_up_first_choice_job),
                subtitle = stringResource(id = R.string.sign_up_first_choice_job_subtitle)
            )

            Spacer(modifier = Modifier.height(8.dp))

            JobTags()

            Spacer(modifier = Modifier.height(40.dp))

            ChoiceTitle(
                title = stringResource(id = R.string.sign_up_first_choice_favorite),
                subtitle = stringResource(id = R.string.sign_up_first_choice_favorite_subtitle)
            )

            Spacer(modifier = Modifier.height(8.dp))

            FavoriteTags()

            Spacer(
                modifier = Modifier.weight(1f)
            )

            FButton(title = stringResource(id = R.string.sign_up_next_title), enable = false)

            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}

@Composable
private fun ChoiceTitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                lineHeight = 18.sp,
                color = FColor.TextPrimary
            )
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = subtitle,
            style = LocalTypography.current.label,
            color = FColor.DisablePlaceholder
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun JobTags(
    modifier: Modifier = Modifier
) {
    val jobItems = listOf(
        stringResource(id = R.string.sign_up_first_choice_job_actor),
        stringResource(id = R.string.sign_up_first_choice_job_staff),
        stringResource(id = R.string.sign_up_first_choice_job_normal),
        stringResource(id = R.string.sign_up_first_choice_job_hunter),
    )

    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        jobItems.forEach { title ->
            JobTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                title = title,
                isSelected = false
            )
        }
    }
}

@Composable
private fun JobTag(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(90.dp))
            .background(
                color = if (isSelected) {
                    FColor.Primary
                } else {
                    FColor.BgGroupedBase
                },
                shape = RoundedCornerShape(90.dp)
            ),
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = title,
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
private fun FavoriteTags(
    modifier: Modifier = Modifier
) {
    val jobItems = listOf(
        stringResource(id = R.string.sign_up_first_choice_favorite_long_film),
        stringResource(id = R.string.sign_up_first_choice_favorite_short_film),
        stringResource(id = R.string.sign_up_first_choice_favorite_independent_film),
        stringResource(id = R.string.sign_up_first_choice_favorite_web_drama),
        stringResource(id = R.string.sign_up_first_choice_favorite_music_video_cf),
        stringResource(id = R.string.sign_up_first_choice_favorite_ott_tv_drama),
        stringResource(id = R.string.sign_up_first_choice_favorite_youtube),
        stringResource(id = R.string.sign_up_first_choice_favorite_promotion_viral),
        stringResource(id = R.string.sign_up_first_choice_favorite_etc),
    )

    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        jobItems.forEach { title ->
            FavoriteTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                title = title,
                isSelected = false,
            )
        }
    }
}

@Composable
private fun FavoriteTag(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(90.dp))
            .background(
                color = if (isSelected) {
                    FColor.Primary
                } else {
                    FColor.BgGroupedBase
                },
                shape = RoundedCornerShape(90.dp)
            ),
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = title,
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

@Preview(showBackground = true)
@Composable
private fun JobTagsPreview() {
    JobTags()
}


@Preview(showBackground = true)
@Composable
private fun FavoriteTagsPreview() {
    FavoriteTags()
}

@Preview(showBackground = true)
@Composable
private fun SignUpFirstScreenPreview() {
    FilmOneTheme {
        SignUpFirstScreen()
    }
}