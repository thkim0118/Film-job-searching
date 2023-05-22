package com.fone.filmone.ui.main.job

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun JobTabProfileItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    info: String
) {
    Column(modifier = modifier) {
        Box {
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
                    .clickableSingle { }
                    .padding(10.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.favorite_selected),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = LocalTypography.current.h5(),
                color = FColor.TextPrimary
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = info,
                style = LocalTypography.current.b3(),
                color = FColor.TextSecondary
            )
        }
    }
}