package com.fone.filmone.ui.main.job.profile.register

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.core.image.ImageBase64Util
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.Pretendard
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PictureComponent(
    modifier: Modifier = Modifier,
    pictureList: List<String>,
    limit: Int = 9,
    onUpdateProfileImage: (String) -> Unit,
    onRemoveImage: (String) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            coroutineScope.launch(Dispatchers.IO) {
                uri?.let {
                    val encodeString = ImageBase64Util.encodeToString(context, it)
                    onUpdateProfileImage(encodeString)
                }
            }
        }
    )

    @Composable
    fun PictureRegisterItem(
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .size(80.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(
                    shape = RoundedCornerShape(5.dp),
                    color = if (pictureList.isEmpty()) {
                        FColor.DisableBase
                    } else {
                        FColor.Secondary1
                    }
                )
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.profile_register_camera_image),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = FColor.BgGroupedBase
                            )
                        ) {
                            append("${pictureList.size} /")
                        }
                        withStyle(
                            SpanStyle(
                                color = FColor.White
                            )
                        ) {
                            append(" $limit")
                        }
                    },
                    fontWeight = FontWeight.W400,
                    fontFamily = Pretendard,
                    fontSize = 13.textDp,
                    lineHeight = 18.textDp,
                )
            }
        }
    }

    @Composable
    fun RegisteredPicture(
        modifier: Modifier = Modifier,
        encodedImageString: String,
        isRepresentativeItem: Boolean
    ) {
        val backgroundModifier = modifier
            .size(80.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(shape = RoundedCornerShape(5.dp), color = FColor.DisableBase)
        var decodedBitmap: Bitmap? by remember { mutableStateOf(null) }

        LaunchedEffect(key1 = encodedImageString) {
            decodedBitmap = ImageBase64Util.decodeToBitmap(encodedImageString)
        }

        Box(modifier = backgroundModifier) {
            GlideImage(
                modifier = backgroundModifier,
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = FColor.Gray700,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),
                imageModel = decodedBitmap,
                contentScale = ContentScale.Crop,
            )

            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp)
                    .clip(shape = CircleShape)
                    .clickableSingle { onRemoveImage(encodedImageString) },
                imageVector = ImageVector.vectorResource(id = R.drawable.profile_register_close_button_16px),
                contentDescription = null
            )

            if (isRepresentativeItem) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .clip(shape = RoundedCornerShape(100.dp))
                        .background(
                            shape = RoundedCornerShape(100.dp),
                            color = FColor.DimColorBasic
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 2.dp, horizontal = 9.5.dp),
                        text = stringResource(id = R.string.profile_register_gallery_representative),
                        style = fTextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 12.textDp,
                            lineHeight = 17.textDp,
                            color = FColor.BgBase
                        )
                    )
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier) {
            PictureRegisterItem(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickableSingle {
                        if (pictureList.size >= limit) {
                            return@clickableSingle
                        }

                        launcher.launch("image/*")
                    }
            )

            LazyRow(
                modifier = Modifier,
                contentPadding = PaddingValues(horizontal = 7.dp)
            ) {
                itemsIndexed(pictureList) { index, encodedString ->
                    RegisteredPicture(
                        encodedImageString = encodedString,
                        isRepresentativeItem = index == 0
                    )

                    if (index != pictureList.lastIndex) {
                        Spacer(modifier = Modifier.width(7.dp))
                    }
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 5.dp),
            text = stringResource(id = R.string.profile_register_gallery_hint),
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 12.textDp,
                lineHeight = 18.textDp,
                color = FColor.DisablePlaceholder
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}