package com.fone.filmone.ui.myregister

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.domain.model.jobopenings.JobType
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.main.job.JobTab
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun MyRecruitmentScreen(
    modifier: Modifier = Modifier,
    registerPosts: List<RegisterPostUiModel>,
    viewModel: MyRegisterViewModel = hiltViewModel(),
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
                        casting = it.casting,
                        onEditClick = {
                            when (it.type) {
                                Type.ACTOR -> {
                                    FOneNavigator.navigateTo(
                                        navDestinationState = NavDestinationState(
                                            route = FOneDestinations.ActorRecruitingEdit.getRouteWithContentId(it.id),
                                        ),
                                    )
                                }
                                Type.STAFF -> {
                                    FOneNavigator.navigateTo(
                                        navDestinationState = NavDestinationState(
                                            route = FOneDestinations.StaffRecruitingEdit.getRouteWithContentId(it.id),
                                        ),
                                    )
                                }
                            }
                        },
                        onDeleteClick = {
                            viewModel.updateDialogState(MyRegisterDialogState.RemoveContent(it.id))
                        },
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
    casting: String?,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 10.dp),
        ) {
            Tags(type = type, categories = categories)

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = title,
                style = LocalTypography.current.b2(),
                color = FColor.TextPrimary,
            )

            Spacer(modifier = Modifier.height(6.dp))

            RegisterInfo(
                deadline = deadline,
                director = director,
                gender = gender,
                period = period,
                jobType = jobType,
                casting = casting,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        RegisterButtons(
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
        )
    }
}

@Composable
private fun Tags(
    modifier: Modifier = Modifier,
    type: Type,
    categories: List<Category>,
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
                    },
                )
                .padding(horizontal = 10.dp, vertical = 2.dp),
        ) {
            Text(
                text = type.name,
                style = LocalTypography.current.b4(),
                color = FColor.White,
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
private fun RegisterInfo(
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
        RegisterContent(
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

        RegisterContent(
            title = stringResource(id = R.string.job_opening_director_title),
            content = director,
        )
    }

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min),
    ) {
        RegisterContent(
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

        RegisterContent(
            title = stringResource(id = R.string.job_opening_period_title),
            content = period,
        )
    }

    if (casting != null) {
        RegisterContent(
            title = stringResource(id = jobType.stringRes),
            content = casting,
        )
    }
}

@Composable
private fun RegisterButtons(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Divider(color = FColor.Divider1)

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .clickableSingle { onEditClick() }
                .padding(vertical = 13.dp)
                .weight(1f),
            text = stringResource(id = R.string.my_register_edit_button_title),
            style = LocalTypography.current.button2(),
            textAlign = TextAlign.Center,
        )

        Divider(
            color = FColor.Divider1,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
        )

        Text(
            modifier = Modifier
                .clickableSingle { onDeleteClick() }
                .padding(vertical = 13.dp)
                .weight(1f),
            text = stringResource(id = R.string.my_register_delete_button_title),
            style = LocalTypography.current.button2(),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun RegisterContent(
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

@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.my_register_recruitment_empty_title),
            style = LocalTypography.current.subtitle2(),
            color = FColor.TextPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .clickableSingle {
                        FOneNavigator.navigateTo(
                            navDestinationState = NavDestinationState(
                                route = FOneDestinations.Main.getRouteWithJobInitialPageArg(
                                    JobTab.JOB_OPENING.name,
                                ),
                                isPopAll = true,
                            ),
                        )
                    },
                text = stringResource(id = R.string.my_register_recruitment_empty_subtitle_actor),
                style = LocalTypography.current.subtitle2(),
                color = FColor.Primary,
                textDecoration = TextDecoration.Underline,
            )

            Text(
                text = " | ",
                style = LocalTypography.current.subtitle2(),
                color = FColor.Primary,
            )

            Text(
                modifier = Modifier
                    .clickableSingle {
                        FOneNavigator.navigateTo(
                            navDestinationState = NavDestinationState(
                                route = FOneDestinations.Main.getRouteWithJobInitialPageArg(
                                    JobTab.JOB_OPENING.name,
                                ),
                                isPopAll = true,
                            ),
                        )
                    },
                text = stringResource(id = R.string.my_register_recruitment_empty_subtitle_staff),
                style = LocalTypography.current.subtitle2(),
                color = FColor.Primary,
                textDecoration = TextDecoration.Underline,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
