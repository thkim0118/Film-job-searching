package com.fone.filmone.ui.myregister

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun MyRecruitmentScreen(
    modifier: Modifier = Modifier,
    registerPosts: List<RegisterPostUiModel>
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (registerPosts.isNotEmpty()) {
            LazyColumn {
                items(registerPosts) {
                    RegisterItem(
                        type = it.type,
                        categories = it.categories,
                        title = it.title,
                        deadline = it.deadline,
                        director = it.director,
                        gender = it.gender,
                        period = it.period,
                        jobType = it.jobType,
                        casting = it.casting
                    )

                    Divider(thickness = 8.dp, color = FColor.Divider2)
                }
            }
        } else {
            EmptyScreen(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun RegisterItem(
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
                style = LocalTypography.current.b2(),
                color = FColor.TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            RegisterInfo(
                deadline = deadline,
                director = director,
                gender = gender,
                period = period,
                jobType = jobType,
                casting = casting
            )

            Spacer(modifier = Modifier.height(21.dp))

            RegisterButtons(
                onEditClick = {},
                onDeleteClick = {}
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
                style = LocalTypography.current.b4(),
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
            style = LocalTypography.current.b4(),
            color = FColor.Secondary1Light
        )
    }
}

@Composable
private fun RegisterInfo(
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
        RegisterContent(
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

        RegisterContent(
            title = stringResource(id = R.string.job_opening_director_title),
            content = director
        )
    }

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
    ) {
        RegisterContent(
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

        RegisterContent(
            title = stringResource(id = R.string.job_opening_period_title),
            content = period
        )
    }

    RegisterContent(
        title = stringResource(id = jobType.stringRes),
        content = casting
    )
}

@Composable
private fun RegisterButtons(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Divider(color = FColor.Divider1)

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .clickableSingle { onEditClick() }
                .padding(vertical = 13.dp)
                .weight(1f),
            text = stringResource(id = R.string.my_register_edit_button_title),
            style = LocalTypography.current.button2(),
            textAlign = TextAlign.Center
        )

        Divider(
            color = FColor.Divider1,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Text(
            modifier = Modifier
                .clickableSingle { onDeleteClick() }
                .padding(vertical = 13.dp)
                .weight(1f),
            text = stringResource(id = R.string.my_register_delete_button_title),
            style = LocalTypography.current.button2(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RegisterContent(
    title: String,
    content: String
) {
    Row {
        Text(
            text = title,
            style = LocalTypography.current.b4(),
            color = FColor.DisablePlaceholder
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = content,
            style = LocalTypography.current.b4(),
            color = FColor.TextSecondary
        )
    }
}

@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.my_register_recruitment_empty_title),
            style = LocalTypography.current.subtitle2(),
            color = FColor.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.my_register_recruitment_empty_subtitle_actor),
                style = LocalTypography.current.subtitle2(),
                color = FColor.Primary,
                textDecoration = TextDecoration.Underline
            )

            Text(
                text = " | ",
                style = LocalTypography.current.subtitle2(),
                color = FColor.Primary
            )

            Text(
                text = stringResource(id = R.string.my_register_recruitment_empty_subtitle_staff),
                style = LocalTypography.current.subtitle2(),
                color = FColor.Primary,
                textDecoration = TextDecoration.Underline
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}