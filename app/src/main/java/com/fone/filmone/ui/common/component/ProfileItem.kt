package com.fone.filmone.ui.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    info: String,
    isWant: Boolean,
    onFavoriteImageClick: () -> Unit,
    onImageClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .clickableSingle { onImageClick() }
    ) {
        Box {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(164 / 198f)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(shape = RoundedCornerShape(5.dp), color = FColor.DisablePlaceholder)
            )

            GlideImage(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(164 / 198f)
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

            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .clickableSingle { onFavoriteImageClick() }
                    .padding(10.dp),
                imageVector = ImageVector.vectorResource(
                    id = if (isWant) {
                        R.drawable.favorite_selected
                    } else {
                        R.drawable.favorite_unselected
                    }
                ),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 16.textDp,
                    lineHeight = 18.textDp,
                    color = FColor.TextPrimary
                ),
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = info,
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 13.textDp,
                    lineHeight = 16.textDp,
                    color = FColor.TextSecondary
                ),
            )
        }
    }
}
