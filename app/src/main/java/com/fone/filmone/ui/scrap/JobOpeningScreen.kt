package com.fone.filmone.ui.scrap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.response.common.user.Category
import com.fone.filmone.data.datamodel.response.common.user.Gender
import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun JobOpeningScreen(
    modifier: Modifier = Modifier,
    jobOpeningUiModes: List<JobOpeningUiModel>
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (jobOpeningUiModes.isNotEmpty()) {
            LazyColumn(
                contentPadding = PaddingValues(top = 11.dp)
            ) {
                items(jobOpeningUiModes) {
                    JobOpeningComponent(
                        type = it.type,
                        categories = it.categories,
                        title = it.title,
                        deadline = it.deadline,
                        director = it.director,
                        gender = it.gender,
                        period = it.period,
                        jobType = it.jobType,
                        casting = it.casting,
                        onScrapClick = {}
                    )

                    Divider(
                        thickness = 6.dp,
                        color = FColor.Divider2
                    )
                }
            }
        } else {
            EmptyScreen(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun JobOpeningComponent(
    modifier: Modifier = Modifier,
    type: Type,
    categories: List<Category>,
    title: String,
    deadline: String,
    director: String,
    gender: Gender,
    period: String,
    jobType: JobType,
    casting: String,
    onScrapClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 20.dp)
        ) {
            Tags(type = type, categories = categories)

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = title,
                style = LocalTypography.current.b1(),
                color = FColor.TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            JobOpeningInfo(
                deadline = deadline,
                director = director,
                gender = gender,
                period = period,
                jobType = jobType,
                casting = casting
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
                .background(
                    shape = CircleShape,
                    color = FColor.BgGroupedBase
                )
                .clickableSingle {
                    onScrapClick()
                }
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.job_opening_scrap),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun Tags(
    modifier: Modifier = Modifier,
    type: Type,
    categories: List<Category>
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .background(
                    shape = RoundedCornerShape(5.dp),
                    color = when (type) {
                        Type.ACTOR -> FColor.Red200
                        Type.STAFF -> FColor.Secondary1Light
                    }
                )
                .padding(horizontal = 10.dp, vertical = 2.dp)
        ) {
            Text(
                text = type.name,
                style = LocalTypography.current.b3(),
                color = FColor.White
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

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
    category: Category
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(100.dp))
            .background(
                shape = RoundedCornerShape(100.dp),
                color = FColor.BgGroupedBase
            )
            .padding(horizontal = 10.dp, vertical = 2.dp)
    ) {
        Text(
            text = stringResource(id = category.stringRes),
            style = LocalTypography.current.b3(),
            color = FColor.Secondary1Light
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
    casting: String,
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
    ) {
        JobOpeningContent(
            title = stringResource(id = R.string.job_opening_deadline_title),
            content = deadline
        )

        Spacer(modifier = Modifier.width(8.dp))

        Divider(
            color = FColor.Divider1,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 3.dp)
                .width(1.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        JobOpeningContent(
            title = stringResource(id = R.string.job_opening_director_title),
            content = director
        )
    }

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
    ) {
        JobOpeningContent(
            title = stringResource(id = R.string.job_opening_gender_title),
            content = stringResource(id = gender.stringRes)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Divider(
            color = FColor.Divider1,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 3.dp)
                .width(1.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        JobOpeningContent(
            title = stringResource(id = R.string.job_opening_period_title),
            content = period
        )
    }

    JobOpeningContent(
        title = stringResource(id = jobType.stringRes),
        content = casting
    )
}

@Composable
private fun JobOpeningContent(
    title: String,
    content: String
) {
    Row {
        Text(
            text = title,
            style = LocalTypography.current.b3(),
            color = FColor.DisablePlaceholder
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = content,
            style = LocalTypography.current.b3(),
            color = FColor.TextSecondary
        )
    }
}

@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.scrap_empty_image),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.scrap_empty_title),
            style = LocalTypography.current.subtitle2(),
            color = FColor.TextPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TagsActorPreview() {
    FilmOneTheme {
        Tags(type = Type.ACTOR, categories = listOf(Category.OTT_DRAMA, Category.SHORT_FILM))
    }
}

@Preview(showBackground = true)
@Composable
fun TagsStaffPreview() {
    FilmOneTheme {
        Tags(type = Type.STAFF, categories = listOf(Category.OTT_DRAMA, Category.SHORT_FILM))
    }
}

@Preview(showBackground = true)
@Composable
fun JobOpeningInfoPreview() {
    FilmOneTheme {
        Column {
            JobOpeningInfo(
                deadline = "2023.01.20",
                director = "성균관대학교 영상학과",
                gender = Gender.MAN,
                period = "일주일",
                jobType = JobType.Field,
                casting = "수영선수"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JobOpeningComponentPreview() {
    FilmOneTheme {
        JobOpeningComponent(
            type = Type.ACTOR,
            categories = listOf(Category.OTT_DRAMA, Category.SHORT_FILM),
            title = "성균관대 영상학과에서 단편영화<Duet>배우 모집합니다.",
            deadline = "2023.01.20",
            director = "성균관대학교 영상학과",
            gender = Gender.MAN,
            period = "일주일",
            jobType = JobType.PART,
            casting = "수영선수"
        )
    }
}