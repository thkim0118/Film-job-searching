package com.fone.filmone.ui.main.job

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.ui.common.component.ProfileItem
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState

@Composable
fun JobTabProfileComponent(
    modifier: Modifier = Modifier,
    jobTabProfilesUiModels: List<JobTabProfilesUiModel>,
    onFavoriteImageClick: (Int) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            items(jobTabProfilesUiModels) {
                ProfileItem(
                    imageUrl = it.profileUrl,
                    name = it.name,
                    info = it.info,
                    isWant = it.isWant,
                    onFavoriteImageClick = { onFavoriteImageClick(it.id) },
                    onImageClick = {
                        val route: String = when (it.type) {
                            Type.ACTOR -> {
                                FOneDestinations.ActorProfileDetail.getRouteWithArg(it.id)
                            }

                            Type.STAFF -> {
                                FOneDestinations.StaffProfileDetail.getRouteWithArg(it.id)
                            }
                        }
                        FOneNavigator.navigateTo(
                            navDestinationState = NavDestinationState(route = route)
                        )
                    }
                )
            }
        }
    }
}
