package com.fone.filmone.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.ui.common.component.ProfileItem
import com.fone.filmone.ui.common.empty.EmptyScreen

@Composable
fun StaffScreen(
    modifier: Modifier = Modifier,
    staffUiState: ProfileUiState,
    onFavoriteClick: (Int, Type) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        when (staffUiState) {
            is ProfileUiState.HasItems -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(26.dp)
                ) {
                    items(staffUiState.uiModels) {
                        ProfileItem(
                            imageUrl = it.profileUrl,
                            name = it.name,
                            info = it.info,
                            isWant = it.isWant,
                            onFavoriteImageClick = { onFavoriteClick(it.id, Type.STAFF) },
                        )
                    }
                }
            }

            ProfileUiState.NoData -> {
                EmptyScreen(
                    modifier = Modifier.align(Alignment.Center),
                    title = stringResource(id = R.string.favorite_empty_title)
                )
            }
        }
    }
}
