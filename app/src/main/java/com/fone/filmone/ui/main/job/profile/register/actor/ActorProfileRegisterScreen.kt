package com.fone.filmone.ui.main.job.profile.register.actor

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fone.filmone.R
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableSingleWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.Pretendard
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ActorProfileRegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: ActorProfileRegisterViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding(),
        snackbarHost = {
            FToast(baseViewModel = viewModel, hostState = it)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TitleComponent(
                onBackClick = {

                },
                onRegisterClick = {

                }
            )

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                PictureComponent(pictureUriList = emptyList())

                Divider(thickness = 8.dp, color = FColor.Divider2)


            }
        }
    }
}

@Composable
private fun TitleComponent(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    FTitleBar(
        titleText = stringResource(id = R.string.profile_register_actor_title_text),
        leading = {
            Image(
                modifier = Modifier
                    .clickableSingleWithNoRipple { onBackClick() },
                imageVector = ImageVector.vectorResource(id = R.drawable.title_bar_back),
                contentDescription = null
            )
        },
        action = {
            Text(
                modifier = Modifier
                    .clickableSingle { onRegisterClick() },
                text = stringResource(id = R.string.profile_register_actor_title_right_button),
                style = fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 15.textDp,
                    lineHeight = 20.textDp,
                    color = FColor.Secondary1Light
                )
            )
        }
    )
}

@Composable
private fun PictureComponent(
    modifier: Modifier = Modifier,
    pictureUriList: List<Uri>,
) {
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
                    color = if (pictureUriList.isEmpty()) {
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
                            append("${pictureUriList.size} /")
                        }
                        withStyle(
                            SpanStyle(
                                color = FColor.White
                            )
                        ) {
                            append(" 9")
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
        imageUri: Uri,
        isRepresentativeItem: Boolean
    ) {
        val backgroundModifier = modifier
            .size(80.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(shape = RoundedCornerShape(5.dp), color = FColor.DisableBase)

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
                imageModel = imageUri,
                contentScale = ContentScale.Crop,
                failure = {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center),
                        imageVector = ImageVector.vectorResource(id = R.drawable.default_profile),
                        contentDescription = null
                    )
                }
            )

            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.profile_register_close_button_16px),
                contentDescription = null
            )

            if (isRepresentativeItem) {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(100.dp))
                        .background(
                            shape = RoundedCornerShape(100.dp),
                            color = FColor.DimColorBasic
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.profile_register_actor_gallery_representative),
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
            PictureRegisterItem(modifier = Modifier.padding(start = 16.dp))

            LazyRow(
                modifier = Modifier,
                contentPadding = PaddingValues(horizontal = 7.dp)
            ) {
                itemsIndexed(pictureUriList) { index, uri ->
                    RegisteredPicture(imageUri = uri, isRepresentativeItem = index == 0)
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 5.dp),
            text = stringResource(id = R.string.profile_register_actor_gallery_hint),
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

@Composable
private fun UserInfoInputComponent(
    modifier: Modifier = Modifier
) {

}

@Composable
private fun CareerInputComponent(
    modifier: Modifier = Modifier
) {

}

@Composable
private fun CategorySelectComponent(
    modifier: Modifier = Modifier
) {

}

@Preview(showBackground = true)
@Composable
private fun ActorProfileRegisterScreenPreview() {
    FilmOneTheme {
        ActorProfileRegisterScreen()
    }
}
