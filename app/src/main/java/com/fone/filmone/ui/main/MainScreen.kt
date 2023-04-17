package com.fone.filmone.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.main.chat.ChatScreen
import com.fone.filmone.ui.main.home.HomeScreen
import com.fone.filmone.ui.main.job.JobScreen
import com.fone.filmone.ui.main.model.BottomNavItem
import com.fone.filmone.ui.main.my.MyScreen
import com.fone.filmone.ui.theme.FColor

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    var selectedScreen by rememberSaveable { mutableStateOf(BottomNavItem.Home) }

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding(),
        bottomBar = {
            MainBottomNavigation(
                bottomNavItems = BottomNavItem.values(),
                selectedScreen = selectedScreen,
                onItemSelected = {
                    selectedScreen = it
                }
            )
        },
    ) {
        Box(modifier = modifier.padding(it)) {
            when (selectedScreen) {
                BottomNavItem.Home -> HomeScreen()
                BottomNavItem.Job -> JobScreen()
                BottomNavItem.Chat -> ChatScreen()
                BottomNavItem.My -> MyScreen()
            }
        }
    }
}

@Composable
private fun MainBottomNavigation(
    bottomNavItems: Array<BottomNavItem>,
    selectedScreen: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    BottomNavigation(
        backgroundColor = FColor.White,
    ) {
        bottomNavItems.forEach { bottomNavItem ->
            MainBottomNavigationItem(
                bottomNavItems = bottomNavItem,
                selectedBottomNavItem = selectedScreen,
                onItemClick = onItemSelected
            )
        }
    }
}

@Composable
fun RowScope.MainBottomNavigationItem(
    bottomNavItems: BottomNavItem,
    selectedBottomNavItem: BottomNavItem,
    onItemClick: (BottomNavItem) -> Unit
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (selectedBottomNavItem == bottomNavItems) {
                        bottomNavItems.selectedIconRes
                    } else {
                        bottomNavItems.unselectedIconRes
                    }
                ),
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = stringResource(id = bottomNavItems.title),
                style = fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    color = if (selectedBottomNavItem == bottomNavItems) {
                        FColor.Primary
                    } else {
                        FColor.DisablePlaceholder
                    },
                )
            )
        },
        alwaysShowLabel = true,
        selectedContentColor = FColor.Primary,
        unselectedContentColor = FColor.DisablePlaceholder,
        selected = selectedBottomNavItem == bottomNavItems,
        onClick = { onItemClick(bottomNavItems) }
    )
}