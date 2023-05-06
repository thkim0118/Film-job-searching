package com.fone.filmone.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.bottomsheet.PairButtonBottomSheet
import com.fone.filmone.ui.common.dialog.SingleButtonDialog
import com.fone.filmone.ui.common.ext.*
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.main.chat.ChatScreen
import com.fone.filmone.ui.main.home.HomeScreen
import com.fone.filmone.ui.main.job.JobScreen
import com.fone.filmone.ui.main.model.BottomNavItem
import com.fone.filmone.ui.main.my.MyScreen
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    var bottomSheetType: MainBottomSheetType by remember { mutableStateOf(MainBottomSheetType.Logout) }
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var selectedScreen by rememberSaveable { mutableStateOf(BottomNavItem.Home) }
    val uiState by viewModel.uiState.collectAsState()

    BackHandler(true) {
        if (bottomSheetState.isVisible) {
            coroutineScope.launch {
                bottomSheetState.hide()
            }
        } else {
            navController.popBackStack()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(10.dp),
        sheetContent = {
            when (bottomSheetType) {
                MainBottomSheetType.Logout -> LogoutBottomSheet(
                    coroutineScope = coroutineScope,
                    bottomSheetState = bottomSheetState,
                    onLogoutClick = {
                        coroutineScope.launch {
                            viewModel.logout()
                        }
                    }
                )
                MainBottomSheetType.Withdrawal -> WithdrawalBottomSheet(
                    coroutineScope = coroutineScope,
                    bottomSheetState = bottomSheetState,
                    onSignOutClick = {
                        coroutineScope.launch {
                            viewModel.signOut()
                        }
                    }
                )
                MainBottomSheetType.JobTabJopOpeningsFilter -> JobTabJobOpeningsFilterBottomSheet(
                    coroutineScope = coroutineScope,
                    bottomSheetState = bottomSheetState,
                )
                MainBottomSheetType.JobTabProfileFilter -> JobTabProfileFilterBottomSheet(
                    coroutineScope = coroutineScope,
                    bottomSheetState = bottomSheetState,
                )
            }
        }
    ) {
        Scaffold(
            modifier = modifier
                .dimBackground(uiState.isFloatingClick)
                .defaultSystemBarPadding()
                .toastPadding(),
            floatingActionButton = {
                when (selectedScreen) {
                    BottomNavItem.Home -> Unit
                    BottomNavItem.Job -> {
                        JobFloatingButton(
                            isFloatingClick = uiState.isFloatingClick,
                            onFloatingClick = { isClick ->
                                if (isClick) {
                                    viewModel.showFloatingDimBackground()
                                } else {
                                    viewModel.hideFloatingDimBackground()
                                }
                            }
                        )
                    }
                    BottomNavItem.Chat -> Unit
                    BottomNavItem.My -> Unit
                }
            },
            bottomBar = {
                Box {
                    MainBottomNavigation(
                        bottomNavItems = BottomNavItem.values(),
                        selectedScreen = selectedScreen,
                        onItemSelected = {
                            if (selectedScreen != BottomNavItem.Job) {
                                viewModel.hideFloatingDimBackground()
                            }

                            selectedScreen = it
                        }
                    )

                    if (uiState.isFloatingClick) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(color = FColor.DimColorThin)
                                .clickableWithNoRipple {
                                    viewModel.hideFloatingDimBackground()
                                }
                        )
                    }
                }
            },
            snackbarHost = {
                FToast(baseViewModel = viewModel, hostState = it)
            }
        ) {
            Box(modifier = modifier.padding(it)) {
                when (selectedScreen) {
                    BottomNavItem.Home -> HomeScreen()
                    BottomNavItem.Job -> JobScreen(
                        onJobOpeningsFilterClick = {
                            bottomSheetType = MainBottomSheetType.JobTabJopOpeningsFilter
                            coroutineScope.launch { bottomSheetState.show() }
                        },
                        onProfileFilterClick = {
                            bottomSheetType = MainBottomSheetType.JobTabProfileFilter
                            coroutineScope.launch { bottomSheetState.show() }
                        }
                    )
                    BottomNavItem.Chat -> ChatScreen()
                    BottomNavItem.My -> MyScreen(
                        onLogoutClick = {
                            bottomSheetType = MainBottomSheetType.Logout
                            coroutineScope.launch { bottomSheetState.show() }
                        },
                        onWithdrawalClick = {
                            bottomSheetType = MainBottomSheetType.Withdrawal
                            coroutineScope.launch { bottomSheetState.show() }
                        }
                    )
                }

                if (uiState.isFloatingClick) {
                    FloatingDimBackground(viewModel)
                }

                MainDialog(
                    dialogState = uiState.mainDialogState,
                    onDismiss = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                        viewModel.clearDialog()
                        FOneNavigator.navigateTo(
                            navDestinationState = NavDestinationState(
                                route = FOneDestinations.Login.route,
                                isPopAll = true
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun FloatingDimBackground(viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = FColor.DimColorThin)
            .clickableWithNoRipple {
                viewModel.hideFloatingDimBackground()
            }
    )
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
                    fontSize = 12.textDp,
                    lineHeight = 12.textDp,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LogoutBottomSheet(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    onLogoutClick: () -> Unit
) {
    PairButtonBottomSheet(
        modifier = modifier,
        content = {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.my_logout_sheet_title),
                style = com.fone.filmone.ui.theme.LocalTypography.current.h2(),
                color = FColor.TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.my_logout_sheet_subtitle),
                style = com.fone.filmone.ui.theme.LocalTypography.current.h5(),
                color = FColor.TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))
        },
        onLeftButtonClick = {
            coroutineScope.launch {
                bottomSheetState.hide()
            }
        },
        onRightButtonClick = onLogoutClick

    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun WithdrawalBottomSheet(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    onSignOutClick: () -> Unit
) {
    PairButtonBottomSheet(
        modifier = modifier,
        content = {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.my_withdrawal_sheet_title),
                style = com.fone.filmone.ui.theme.LocalTypography.current.h2(),
                color = FColor.TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.my_withdrawal_sheet_subtitle),
                style = com.fone.filmone.ui.theme.LocalTypography.current.h5(),
                color = FColor.TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))
        },
        onLeftButtonClick = {
            coroutineScope.launch {
                bottomSheetState.hide()
            }
        },
        onRightButtonClick = onSignOutClick
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun JobTabJobOpeningsFilterBottomSheet(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(shape = RoundedCornerShape(10.dp), color = FColor.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Spacer(
            modifier = Modifier
                .width(45.dp)
                .height(4.dp)
                .clip(shape = RoundedCornerShape(2.dp))
                .background(shape = RoundedCornerShape(2.dp), color = FColor.Divider1)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 22.dp),
            text = stringResource(id = R.string.job_tab_filter_title),
            style = com.fone.filmone.ui.theme.LocalTypography.current.b3(),
            color = FColor.DisablePlaceholder
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle { }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = R.string.job_tab_filter_recent),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextSecondary
            )
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle { }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = R.string.job_tab_filter_view),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextSecondary
            )
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle { }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = R.string.job_tab_filter_deadline),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextSecondary
            )
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun JobTabProfileFilterBottomSheet(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(shape = RoundedCornerShape(10.dp), color = FColor.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Spacer(
            modifier = Modifier
                .width(45.dp)
                .height(4.dp)
                .clip(shape = RoundedCornerShape(2.dp))
                .background(shape = RoundedCornerShape(2.dp), color = FColor.Divider1)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 22.dp),
            text = stringResource(id = R.string.job_tab_filter_title),
            style = com.fone.filmone.ui.theme.LocalTypography.current.b3(),
            color = FColor.DisablePlaceholder
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle { }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = R.string.job_tab_filter_recent),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextSecondary
            )
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle { }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = R.string.job_tab_filter_view),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = FColor.TextSecondary
            )
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun JobFloatingButton(
    modifier: Modifier = Modifier,
    isFloatingClick: Boolean,
    onFloatingClick: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            if (isFloatingClick) {
                Column(
                    modifier = Modifier
                        .width(106.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                            .background(
                                shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp),
                                color = FColor.Primary
                            )
                            .clickableSingle { }
                            .padding(horizontal = 17.dp, vertical = 11.dp),
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.job_tab_fab_actor),
                            style = fTextStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 14.textDp,
                                lineHeight = 18.textDp,
                                color = FColor.BgBase
                            ),
                            textAlign = TextAlign.Center
                        )
                    }

                    Divider(color = FColor.ColorF43663)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomStart = 6.dp,
                                    bottomEnd = 6.dp
                                )
                            )
                            .background(
                                shape = RoundedCornerShape(
                                    bottomStart = 6.dp,
                                    bottomEnd = 6.dp
                                ),
                                color = FColor.Primary
                            )
                            .clickableSingle { }
                            .padding(horizontal = 17.dp, vertical = 11.dp),
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.job_tab_fab_staff),
                            style = fTextStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 14.textDp,
                                lineHeight = 18.textDp,
                                color = FColor.BgBase
                            ),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            IconButton(
                modifier = modifier
                    .clip(shape = CircleShape)
                    .background(shape = CircleShape, color = FColor.Primary),
                onClick = {
                    onFloatingClick(isFloatingClick.not())
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.job_tab_floating_image),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun MainDialog(
    dialogState: MainDialogState,
    onDismiss: () -> Unit
) {
    when (dialogState) {
        MainDialogState.Clear -> Unit
        MainDialogState.WithdrawalComplete -> WithdrawalCompleteDialog(onDismiss = onDismiss)
    }
}

@Composable
private fun WithdrawalCompleteDialog(
    onDismiss: () -> Unit
) {
    SingleButtonDialog(
        titleText = stringResource(id = R.string.main_withdrawal_dialog_title),
        buttonText = stringResource(id = R.string.confirm)
    ) {
        onDismiss()
    }
}