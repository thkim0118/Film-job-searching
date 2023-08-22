package com.fone.filmone.ui.scrap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.empty.EmptyScreen
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CompetitionsScreen(
    modifier: Modifier = Modifier,
    competitionUiModels: List<CompetitionUiModel>,
    onScrapClick: (Int) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (competitionUiModels.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(start = 16.dp, end = 20.dp),
                contentPadding = PaddingValues(top = 11.dp)
            ) {
                items(competitionUiModels) {
                    CompetitionComponent(
                        imageUrl = it.imageUrl,
                        title = it.title,
                        host = it.host,
                        dday = it.dday,
                        viewCount = it.vieweCount,
                        isScrap = it.isScrap,
                        onScrapClick = { onScrapClick(it.id) }
                    )

                    Divider(
                        thickness = 1.dp,
                        color = FColor.BgGroupedBase
                    )
                }
            }
        } else {
            EmptyScreen(
                modifier = Modifier.align(Alignment.Center),
                title = stringResource(id = R.string.scrap_empty_title)
            )
        }
    }
}

@Composable
private fun CompetitionComponent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    host: String,
    dday: String,
    viewCount: String,
    isScrap: Boolean,
    onScrapClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        CompetitionImage(modifier, imageUrl)

        Spacer(modifier = Modifier.width(12.dp))

        CompetitionContent(
            title = title,
            host = host,
            dday = dday,
            viewCount = viewCount
        )

        Spacer(modifier = Modifier.width(18.dp))

        CompetitionScrapImage(isScrap = isScrap, onScrapClick = onScrapClick)
    }
}

@Composable
private fun CompetitionImage(modifier: Modifier, imageUrl: String) {
    GlideImage(
        modifier = modifier
            .size(width = 95.dp, height = 98.dp)
            .clip(shape = RoundedCornerShape(5.dp)),
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
private fun RowScope.CompetitionContent(
    modifier: Modifier = Modifier,
    title: String,
    host: String,
    dday: String,
    viewCount: String,
) {
    Column(
        modifier = modifier
            .weight(1f)
    ) {
        Text(
            text = title,
            style = LocalTypography.current.subtitle2(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = host,
            style = LocalTypography.current.b3(),
            color = FColor.TextSecondary
        )

        Spacer(modifier = Modifier.height(19.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dday,
                style = LocalTypography.current.b3(),
                color = FColor.Secondary1Light
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.scrap_competition_view_count),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = viewCount,
                style = LocalTypography.current.b3(),
                color = FColor.DisablePlaceholder
            )
        }
    }
}

@Composable
private fun CompetitionScrapImage(
    isScrap: Boolean,
    onScrapClick: () -> Unit,
) {

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
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
private fun CompetitionContentPreview() {
    FilmOneTheme {
        Row {
            Box(
                modifier = Modifier
                    .size(width = 95.dp, height = 98.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(shape = RoundedCornerShape(5.dp), color = FColor.Gray300)
            )

            Spacer(modifier = Modifier.width(12.dp))

            CompetitionContent(
                title = "제목 예 2022 베리어프리 콘텐츠 공모전",
                host = "방송통신 위원회",
                dday = "D-12",
                viewCount = "3,222",
            )

            Spacer(modifier = Modifier.width(18.dp))

            CompetitionScrapImage(
                isScrap = true,
                onScrapClick = {}
            )
        }
    }
}
