package com.fone.filmone.ui.main.job

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.ui.common.ext.LazyColumnLastItemCallback
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun JobTabJobOpeningsComponent(
    modifier: Modifier = Modifier,
    jobTabJobOpeningUiModels: List<JobTabJobOpeningUiModel>,
    onItemClick: (Int, Type) -> Unit,
    onScrapClick: (Int) -> Unit,
    onLastItemVisible: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumnLastItemCallback(
            contentPadding = PaddingValues(top = 11.dp),
            onLastItemCallback = { index ->
                onLastItemVisible()
            },
        ) {
            items(jobTabJobOpeningUiModels) {
                JobOpeningComponent(
                    categories = it.categories,
                    title = it.title,
                    deadline = it.deadline ?: stringResource(id = R.string.always_recruiting),
                    director = it.director,
                    gender = it.gender,
                    period = it.period,
                    jobType = it.jobType,
                    casting = it.casting,
                    isScrap = it.isScrap,
                    onItemClick = { onItemClick(it.id, it.type) },
                    onScrapClick = { onScrapClick(it.id) }
                )

                Divider(
                    thickness = 6.dp,
                    color = FColor.Divider2,
                )
            }
        }
    }
}

@Composable
private fun JobOpeningComponent(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    title: String,
    deadline: String,
    director: String,
    gender: Gender,
    period: String,
    jobType: JobType,
    casting: String?,
    isScrap: Boolean,
    onItemClick: () -> Unit,
    onScrapClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickableSingle { onItemClick() }
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 10.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 20.dp),
        ) {
            Tags(categories = categories)

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = title,
                style = LocalTypography.current.b2(),
                color = FColor.TextPrimary,
            )

            Spacer(modifier = Modifier.height(6.dp))

            JobOpeningInfo(
                deadline = deadline,
                director = director,
                gender = gender,
                period = period,
                jobType = jobType,
                casting = casting,
            )
        }

        Image(
            modifier = Modifier
                .clip(shape = CircleShape)
                .clickable { onScrapClick() },
            imageVector = ImageVector.vectorResource(
                id = if (isScrap) {
                    R.drawable.job_opening_scrap_selected
                } else {
                    R.drawable.job_opening_scrap_unselected
                }
            ),
            contentDescription = null,
        )
    }
}

@Composable
private fun Tags(
    modifier: Modifier = Modifier,
    categories: List<Category>,
) {
    Row(modifier = modifier) {
        categories.forEachIndexed { index, category ->
            CategoryTag(category = category)

            if (index != categories.lastIndex) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}

@Composable
private fun CategoryTag(
    modifier: Modifier = Modifier,
    category: Category,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(100.dp))
            .background(
                shape = RoundedCornerShape(100.dp),
                color = FColor.BgGroupedBase,
            )
            .padding(horizontal = 10.dp, vertical = 2.dp),
    ) {
        Text(
            text = stringResource(id = category.stringRes),
            style = LocalTypography.current.b4(),
            color = FColor.Secondary1Light,
        )
    }
}

@Composable
private fun JobOpeningInfo(
    deadline: String,
    director: String,
    gender: Gender,
    period: String,
    jobType: JobType,
    casting: String?,
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min),
    ) {
        JobOpeningContent(
            title = stringResource(id = R.string.job_opening_deadline_title),
            content = deadline,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Divider(
            color = FColor.Divider1,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 3.dp)
                .width(1.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        JobOpeningContent(
            title = stringResource(id = R.string.job_opening_director_title),
            content = director,
        )
    }

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min),
    ) {
        JobOpeningContent(
            title = stringResource(id = R.string.job_opening_gender_title),
            content = stringResource(id = gender.stringRes),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Divider(
            color = FColor.Divider1,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 3.dp)
                .width(1.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        JobOpeningContent(
            title = stringResource(id = R.string.job_opening_period_title),
            content = period,
        )
    }

    if (casting != null) {
        JobOpeningContent(
            title = stringResource(id = jobType.stringRes),
            content = casting,
        )
    }
}

@Composable
private fun JobOpeningContent(
    title: String,
    content: String,
) {
    Row {
        Text(
            text = title,
            style = LocalTypography.current.b4(),
            color = FColor.DisablePlaceholder,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = content,
            style = LocalTypography.current.b4(),
            color = FColor.TextSecondary,
        )
    }
}
