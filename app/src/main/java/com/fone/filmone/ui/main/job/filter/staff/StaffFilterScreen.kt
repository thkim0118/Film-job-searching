package com.fone.filmone.ui.main.job.filter.staff

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.RangeSlider
import androidx.compose.material.Scaffold
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
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.FButton
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.common.tag.ToggleSelectTag
import com.fone.filmone.ui.common.tag.categories.CategoryTags
import com.fone.filmone.ui.common.tag.domain.DomainTags
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun StaffFilterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: StaffFilterViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ActorFilterTitle(
                onRefreshClick = {
                    viewModel.unSelectAllGenders()
                    viewModel.unSelectAllInterests()
                    viewModel.unSelectAllDomains()
                    viewModel.updateAgeRangeReset()
                },
                onCloseClick = {
                    navController.popBackStack()
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
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

                Spacer(modifier = Modifier.height(26.dp))

                DomainComponent(
                    currentDomains = state.domains.toList(),
                    onUpdateDomainAll = viewModel::updateDomainSelectAll,
                    onUpdateDomain = viewModel::updateDomain
                )
            }

            NextButton(
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(38.dp))
        }
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
            text = stringResource(id = R.string.job_filter_title),
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
            title = stringResource(id = R.string.job_filter_gender_title),
            tagTitle = stringResource(id = R.string.job_filter_all_select),
            onTagClick = onUpdateGenderAll,
            currentGender = currentGender,
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
            title = stringResource(id = R.string.job_filter_age),
            tagTitle = stringResource(id = R.string.job_filter_age_irrelevant),
            onTagClick = onUpdateAgeReset,
            ageRange = ageRange,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(
                if (ageRange.endInclusive >= 70f) {
                    R.string.job_filter_age_range
                } else {
                    R.string.job_filter_age_range_no_limit
                },
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
            title = stringResource(id = R.string.job_filter_interests_title),
            tagTitle = stringResource(id = R.string.job_filter_all_select),
            onTagClick = onUpdateInterestsAll,
            currentInterests = currentInterests,
        )

        Spacer(modifier = Modifier.height(6.dp))

        CategoryTags(
            currentCategories = currentInterests,
            onUpdateCategories = onUpdateInterests
        )
    }
}

@Composable
private fun DomainComponent(
    modifier: Modifier = Modifier,
    currentDomains: List<Domain>,
    onUpdateDomainAll: () -> Unit,
    onUpdateDomain: (Domain, Boolean) -> Unit
) {
    Column(modifier = modifier) {
        FilterComponentTitle(
            title = stringResource(id = R.string.job_filter_domain_title),
            tagTitle = stringResource(id = R.string.job_filter_all_select),
            onTagClick = onUpdateDomainAll,
            currentDomains = currentDomains,
        )

        Spacer(modifier = Modifier.height(6.dp))

        DomainTags(
            currentDomains = currentDomains,
            onUpdateDomain = onUpdateDomain
        )
    }
}

@Composable
private fun FilterComponentTitle(
    modifier: Modifier = Modifier,
    title: String,
    tagTitle: String,
    onTagClick: () -> Unit,
    currentGender: Set<Gender>? = null,
    ageRange: ClosedFloatingPointRange<Float>? = null,
    currentInterests: List<Category>? = null,
    currentDomains: List<Domain>? = null,
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

        if (currentGender != null) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(100.dp))
                    .background(
                        shape = RoundedCornerShape(100.dp),
                        color = if (currentGender.isEmpty()) FColor.Secondary1 else FColor.Gray300
                    )
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

        if (ageRange != null) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(100.dp))
                    .background(
                        shape = RoundedCornerShape(100.dp),
                        color = if (ageRange.start.toInt() == 1 && ageRange.endInclusive.toInt() == 70) FColor.Secondary1 else FColor.Gray300
                    )
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

        if (currentInterests != null) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(100.dp))
                    .background(
                        shape = RoundedCornerShape(100.dp),
                        color = if (currentInterests.isEmpty()) FColor.Secondary1 else FColor.Gray300
                    )
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

        if (currentDomains != null) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(100.dp))
                    .background(
                        shape = RoundedCornerShape(100.dp),
                        color = if (currentDomains.isEmpty()) FColor.Secondary1 else FColor.Gray300
                    )
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
}

@Composable
private fun ColumnScope.NextButton(
    modifier: Modifier = Modifier,
) {
    val enable = false

    Spacer(modifier = Modifier.weight(1f))

    FButton(
        modifier = modifier,
        title = stringResource(id = R.string.job_filter_button_title),
        enable = enable,
        onClick = {
            if (enable) {
            }
        }
    )
}
