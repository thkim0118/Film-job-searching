package com.fone.filmone.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
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
            }
        }
    ) {
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
            snackbarHost = {
                FToast(baseViewModel = viewModel, hostState = it)
            }
        ) {
            Box(modifier = modifier.padding(it)) {
                when (selectedScreen) {
                    BottomNavItem.Home -> HomeScreen()
                    BottomNavItem.Job -> JobScreen()
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

@Composable
private fun MainDialog(
    modifier: Modifier = Modifier,
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