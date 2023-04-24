package com.fone.filmone.ui.main.my

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fone.filmone.BuildConfig
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.*
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.main.MainBottomSheetType
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun MyScreen(
    modifier: Modifier = Modifier,
    viewModel: MyViewModel = hiltViewModel(),
    onLogoutClick: () -> Unit,
    onWithdrawalClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.my_title),
                        style = LocalTypography.current.h2(),
                        color = FColor.TextPrimary
                    )
                },
                backgroundColor = FColor.White,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.main_notification),
                            contentDescription = null,
                            tint = FColor.DisablePlaceholder
                        )
                    }
                }
            )
        },
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            ProfileComponent(
                modifier = Modifier,
                nickname = uiState.nickname,
                onClick = {
                    FOneNavigator.navigateTo(
                        NavDestinationState(route = FOneDestinations.MyInfo.route)
                    )
                }
            )

            ScrapFavoriteComponent(
                onScrapClick = {
                    FOneNavigator.navigateTo(
                        NavDestinationState(route = FOneDestinations.Scrap.route)
                    )
                },
                onFavoriteClick = {
                    FOneNavigator.navigateTo(
                        NavDestinationState(route = FOneDestinations.Favorite.route)
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Divider(thickness = 8.dp, color = FColor.Gray50)

            MenuList(
                onRegisterListClick = {
                    FOneNavigator.navigateTo(
                        NavDestinationState(route = FOneDestinations.MyRegister.route)
                    )
                },
                onInquiryClick = {
                    FOneNavigator.navigateTo(
                        NavDestinationState(route = FOneDestinations.Inquiry.route)
                    )
                },
                onLogoutClick = {
                    onLogoutClick()
                },
                onWithdrawalClick = {
                    onWithdrawalClick()
                }
            )
        }
    }
}


@Composable
private fun ProfileComponent(
    modifier: Modifier = Modifier,
    nickname: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickableSingleWithNoRipple { onClick() }
            .padding(top = 16.dp, bottom = 15.dp, start = 24.dp, end = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(68.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.default_profile),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = nickname,
            style = LocalTypography.current.h1(),
            color = FColor.TextPrimary
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "STAFF",
            style = LocalTypography.current.subtitle2(),
            color = FColor.Primary
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.my_profile_right_arrow),
            contentDescription = null
        )
    }
}

@Composable
private fun ScrapFavoriteComponent(
    modifier: Modifier = Modifier,
    onScrapClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val scrapIcon = "scrap_icon"
    val favoriteIcon = "favorite_icon"

    val scrapTitle = buildAnnotatedString {
        appendInlineContent(id = scrapIcon)
        append(stringResource(id = R.string.my_scrap_title))
    }
    val favoriteTitle = buildAnnotatedString {
        appendInlineContent(id = favoriteIcon)
        append(stringResource(id = R.string.my_favorite_title))
    }
    val scrapInlineContentMap = mapOf(
        scrapIcon to InlineTextContent(
            Placeholder(width = 18.textDp, height = 18.textDp, PlaceholderVerticalAlign.Center)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.my_scrap),
                contentDescription = null
            )
        }
    )
    val favoriteInlineContentMap = mapOf(
        favoriteIcon to InlineTextContent(
            Placeholder(width = 18.textDp, height = 18.textDp, PlaceholderVerticalAlign.Center)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.my_favorite),
                contentDescription = null
            )
        }
    )

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(shape = RoundedCornerShape(5.dp), color = FColor.Divider2)
    ) {
        Text(
            modifier = Modifier
                .clickableSingle { onScrapClick() }
                .padding(vertical = 12.dp)
                .weight(1f),
            text = scrapTitle,
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextSecondary
            ),
            inlineContent = scrapInlineContentMap,
            textAlign = TextAlign.Center
        )

        Divider(
            color = FColor.Divider1,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 7.dp)
                .width(1.dp)
        )

        Text(
            modifier = Modifier
                .clickableSingle { onFavoriteClick() }
                .padding(vertical = 12.dp)
                .weight(1f),
            text = favoriteTitle,
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextSecondary
            ),
            inlineContent = favoriteInlineContentMap,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MenuList(
    onRegisterListClick: () -> Unit,
    onInquiryClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onWithdrawalClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle { onRegisterListClick() }
            .padding(top = 13.dp, bottom = 11.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.my_register_list),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.my_register_title),
            style = LocalTypography.current.subtitle1(),
            color = FColor.TextPrimary
        )

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.my_list_right_arrow),
            contentDescription = null
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle { onInquiryClick() }
            .padding(top = 13.dp, bottom = 11.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.my_inquiry),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.my_inquiry_title),
            style = LocalTypography.current.subtitle1(),
            color = FColor.TextPrimary
        )

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.my_list_right_arrow),
            contentDescription = null
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 13.dp, bottom = 11.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.my_app_version),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.my_app_version_title),
            style = LocalTypography.current.subtitle1(),
            color = FColor.TextPrimary
        )

        Text(
            text = BuildConfig.VERSION_NAME,
            style = fTextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 16.textDp,
                lineHeight = 16.textDp,
                color = FColor.Secondary1Light
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle { onLogoutClick() }
            .padding(top = 13.dp, bottom = 11.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.my_logout),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = stringResource(id = R.string.my_logout_title),
            style = LocalTypography.current.subtitle1(),
            color = FColor.TextPrimary
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle { onWithdrawalClick() }
            .padding(top = 13.dp, bottom = 11.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.my_withdrawal),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = stringResource(id = R.string.my_withdrawal_title),
            style = LocalTypography.current.subtitle1(),
            color = FColor.TextPrimary
        )
    }
}