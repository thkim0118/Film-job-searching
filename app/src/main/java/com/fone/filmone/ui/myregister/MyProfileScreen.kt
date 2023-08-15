package com.fone.filmone.ui.myregister

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.main.job.JobTab
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MyProfileScreen(
    modifier: Modifier = Modifier,
    profilePosts: List<RegisterPostProfileUiModel>
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = FColor.Divider2)
    ) {
        if (profilePosts.isNotEmpty()) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
            ) {
                items(profilePosts) {
                    Spacer(modifier = Modifier.height(18.dp))

                    RegisterProfileItem(
                        profileUrl = it.profileUrl,
                        name = it.name,
                        type = it.type,
                        info = it.info
                    )

                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        } else {
            EmptyScreen(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun RegisterProfileItem(
    modifier: Modifier = Modifier,
    profileUrl: String,
    name: String,
    type: Type,
    info: String
) {
    val shape = RoundedCornerShape(10.dp)

    Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = FColor.White
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            GlideImage(
                modifier = modifier
                    .weight(104f)
                    .aspectRatio(104 / 126f)
                    .clip(shape = shape),
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = FColor.Gray700,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),
                imageModel = profileUrl,
                contentScale = ContentScale.Crop,
                failure = {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center),
                        imageVector = ImageVector.vectorResource(id = R.drawable.splash_fone_logo),
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(199f)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = LocalTypography.current.h5(),
                        color = FColor.TextPrimary
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = type.name,
                        style = LocalTypography.current.subtitle2(),
                        color = FColor.Primary
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = info,
                    style = LocalTypography.current.b2(),
                    color = FColor.TextSecondary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Divider(color = FColor.BgGroupedBase)

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(
                                shape = RoundedCornerShape(5.dp),
                                color = FColor.BgGroupedBase
                            )
                            .clickableSingle { }
                            .padding(vertical = 10.dp, horizontal = 14.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.my_register_edit_button_title),
                            style = LocalTypography.current.button2(),
                            color = FColor.TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(
                                shape = RoundedCornerShape(5.dp),
                                color = FColor.BgGroupedBase
                            )
                            .clickableSingle { }
                            .padding(vertical = 10.dp, horizontal = 14.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.my_register_delete_button_title),
                            style = LocalTypography.current.button2(),
                            color = FColor.TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.my_register_profile_empty_title),
            style = LocalTypography.current.subtitle2(),
            color = FColor.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle {
                    FOneNavigator.navigateTo(
                        navDestinationState = NavDestinationState(
                            route = FOneDestinations.Main.getRouteWithJobInitialPageArg(
                                JobTab.PROFILE.name
                            ),
                            isPopAll = true
                        )
                    )
                },
            text = stringResource(id = R.string.my_register_profile_empty_subtitle),
            style = LocalTypography.current.subtitle2(),
            color = FColor.Primary,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
    }
}
