package com.fone.filmone.ui.main.job.filter.actor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.RangeSlider
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.response.common.user.Category
import com.fone.filmone.data.datamodel.response.common.user.Gender
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.tag.ToggleSelectTag
import com.fone.filmone.ui.common.tag.interests.InterestsTags
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun ActorFilterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ActorFilterViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding()
            .padding(horizontal = 16.dp)
    ) {
        ActorFilterTitle(
            onRefreshClick = {

            },
            onCloseClick = {
                navController.popBackStack()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        GenderTagsComponent(
            currentGender = state.genders,
            onUpdateGender = viewModel::updateGender,
            onUpdateGenderAll = viewModel::updateGenderSelectAll
        )

        Spacer(modifier = Modifier.height(30.dp))

        AgeComponent(
            ageRange = state.ageRange,
            onUpdateAgeReset = viewModel::updateAgeRangeReset,
            onUpdateAgeRange = viewModel::updateAgeRange
        )

        Spacer(modifier = Modifier.height(6.dp))

        InterestsComponent(
            currentInterests = state.interests.toList(),
            onUpdateInterestsAll = viewModel::updateInterestSelectAll,
            onUpdateInterests = viewModel::updateInterest
        )
    }
}

@Composable
private fun ActorFilterTitle(
    modifier: Modifier = Modifier,
    onRefreshClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onRefreshClick() },
            modifier = Modifier.size(24.dp)
        ) {
            Image(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.job_openings_filter_refresh),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(11.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.actor_filter_title),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 19.textDp,
                lineHeight = 26.textDp,
                color = FColor.TextPrimary
            ),
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.width(11.dp))

        IconButton(
            onClick = { onCloseClick() },
            modifier = Modifier.size(24.dp)
        ) {
            Image(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.title_bar_close),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun GenderTagsComponent(
    modifier: Modifier = Modifier,
    currentGender: Set<Gender>,
    onUpdateGender: (Gender, Boolean) -> Unit,
    onUpdateGenderAll: () -> Unit
) {
    Column(modifier = modifier) {
        FilterComponentTitle(
            title = stringResource(id = R.string.actor_filter_gender_title),
            tagTitle = stringResource(id = R.string.actor_filter_all_select),
            onTagClick = onUpdateGenderAll
        )

        Spacer(modifier = Modifier.height(6.dp))

        GenderTags(
            currentGender = currentGender,
            onUpdateGender = onUpdateGender
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenderTags(
    modifier: Modifier = Modifier,
    currentGender: Set<Gender>,
    onUpdateGender: (Gender, Boolean) -> Unit
) {
    FlowRow(modifier = modifier) {
        Gender.values().forEach { gender ->
            ToggleSelectTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                type = gender,
                title = stringResource(id = gender.stringRes),
                isSelected = currentGender.find { it == gender } != null,
                onClick = onUpdateGender
            )
        }
    }
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
        FilterComponentTitle(
            title = stringResource(id = R.string.actor_filter_age),
            tagTitle = stringResource(id = R.string.actor_filter_age_irrelevant),
            onTagClick = onUpdateAgeReset
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(
                id = R.string.actor_filter_age_range,
                ageRange.start.toInt(),
                ageRange.endInclusive.toInt()
            ),
            style = LocalTypography.current.b2(),
            color = FColor.TextSecondary
        )

        Spacer(modifier = Modifier.height(6.dp))

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
private fun InterestsComponent(
    modifier: Modifier = Modifier,
    currentInterests: List<Category>,
    onUpdateInterestsAll: () -> Unit,
    onUpdateInterests: (Category, Boolean) -> Unit
) {
    Column(modifier = modifier) {
        FilterComponentTitle(
            title = stringResource(id = R.string.actor_filter_interests_title),
            tagTitle = stringResource(id = R.string.actor_filter_all_select),
            onTagClick = onUpdateInterestsAll
        )

        InterestsTags(
            currentInterests = currentInterests,
            onUpdateInterests = onUpdateInterests
        )
    }
}

@Composable
private fun FilterComponentTitle(
    modifier: Modifier = Modifier,
    title: String,
    tagTitle: String,
    onTagClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = LocalTypography.current.subtitle1()
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(100.dp))
                .background(shape = RoundedCornerShape(100.dp), color = FColor.Secondary1)
                .clickable { onTagClick() }
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 15.dp),
                text = tagTitle,
                style = fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 12.textDp,
                    lineHeight = 12.textDp,
                    color = FColor.BgBase
                )
            )
        }
    }
}