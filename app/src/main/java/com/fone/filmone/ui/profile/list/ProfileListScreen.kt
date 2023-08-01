package com.fone.filmone.ui.profile.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.theme.FColor
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileListViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding()
    ) {
        TitleComponent(
            userName = uiState.userName,
            onBackClick = {
                navController.popBackStack()
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(uiState.profileUrls) {
                ProfileItem(imageUrl = it)
            }
        }
    }
}

@Composable
private fun ProfileItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    GlideImage(
        modifier = modifier
            .background(color = FColor.Gray300)
            .fillMaxWidth()
            .aspectRatio(114 / 132f),
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = FColor.Gray700,
            durationMillis = 350,
            dropOff = 0.65f,
            tilt = 20f
        ),
        imageModel = imageUrl,
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
}

@Composable
private fun TitleComponent(
    modifier: Modifier = Modifier,
    userName: String,
    onBackClick: () -> Unit,
) {
    FTitleBar(
        modifier = modifier,
        titleText = userName,
        titleType = TitleType.Close,
        onCloseClick = {
            onBackClick()
        }
    )
}