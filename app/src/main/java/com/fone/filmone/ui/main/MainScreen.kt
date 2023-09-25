package com.fone.filmone.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.dimBackground
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.main.chat.ChatScreen
import com.fone.filmone.ui.main.home.HomeScreen
import com.fone.filmone.ui.main.job.JobFilterType
import com.fone.filmone.ui.main.job.JobScreen
import com.fone.filmone.ui.main.job.JobTab
import com.fone.filmone.ui.main.model.MainBottomNavItem
import com.fone.filmone.ui.main.my.MyScreen
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    initialJobTab: JobTab = JobTab.JOB_OPENING,
    initialScreen: MainBottomNavItem = MainBottomNavItem.Home,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    var bottomSheetType: MainBottomSheetType by remember { mutableStateOf(MainBottomSheetType.Logout) }
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var selectedScreen by rememberSaveable { mutableStateOf(initialScreen) }
    var jobTabScreen by remember { mutableStateOf(initialJobTab) }
    val uiState by mainViewModel.uiState.collectAsState()

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
                            mainViewModel.logout()
                        }
                    },
                )

                MainBottomSheetType.Withdrawal -> WithdrawalBottomSheet(
                    coroutineScope = coroutineScope,
                    bottomSheetState = bottomSheetState,
                    onSignOutClick = {
                        coroutineScope.launch {
                            mainViewModel.signOut()
                        }
                    },
                )

                MainBottomSheetType.JobTabJopOpeningsFilter -> JobTabJobOpeningsFilterBottomSheet(
                    coroutineScope = coroutineScope,
                    bottomSheetState = bottomSheetState,
                    currentJobFilterType = uiState.currentJobSorting.currentJobFilterType,
                    onJobFilterTypeClick = {
                        mainViewModel.updateJobFilter(it)
                    },
                )

                MainBottomSheetType.JobTabProfileFilter -> JobTabProfileFilterBottomSheet(
                    coroutineScope = coroutineScope,
                    bottomSheetState = bottomSheetState,
                    currentJobFilterType = uiState.currentJobSorting.currentJobFilterType,
                    onJobFilterTypeClick = {
                        mainViewModel.updateJobFilter(it)
                    },
                )
            }
        },
    ) {
        Scaffold(
            modifier = modifier
                .dimBackground(uiState.isFloatingClick)
                .defaultSystemBarPadding()
                .toastPadding(),
            floatingActionButton = {
                when (selectedScreen) {
                    MainBottomNavItem.Home -> Unit
                    MainBottomNavItem.Job -> {
                        JobFloatingButton(
                            currentJobTab = jobTabScreen,
                            isFloatingClick = uiState.isFloatingClick,
                            onFloatingClick = { isClick ->
                                if (isClick) {
                                    mainViewModel.showFloatingDimBackground()
                                } else {
                                    mainViewModel.hideFloatingDimBackground()
                                }
                            },
                        )
                    }

                    MainBottomNavItem.Chat -> Unit
                    MainBottomNavItem.My -> Unit
                }
            },
            bottomBar = {
                Box {
                    MainBottomNavigation(
                        mainBottomNavItems = MainBottomNavItem.values(),
                        selectedScreen = selectedScreen,
                        onItemSelected = {
                            if (selectedScreen != MainBottomNavItem.Job) {
                                mainViewModel.hideFloatingDimBackground()
                            }

                            selectedScreen = it
                        },
                    )

                    if (uiState.isFloatingClick) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(color = FColor.DimColorThin)
                                .clickableWithNoRipple {
                                    mainViewModel.hideFloatingDimBackground()
                                },
                        )
                    }
                }
            },
            snackbarHost = {
                FToast(baseViewModel = mainViewModel, hostState = it)
            },
        ) {
            Box(modifier = modifier.padding(it)) {
                when (selectedScreen) {
                    MainBottomNavItem.Home -> HomeScreen(onJobButtonClick = {
                        selectedScreen = MainBottomNavItem.Job
                    })
                    MainBottomNavItem.Job -> JobScreen(
                        currentJobSorting = uiState.currentJobSorting,
                        userType = uiState.type,
                        initialJobTab = initialJobTab,
                        onJobOpeningsFilterClick = {
                            bottomSheetType = MainBottomSheetType.JobTabJopOpeningsFilter
                            coroutineScope.launch { bottomSheetState.show() }
                        },
                        onProfileFilterClick = {
                            bottomSheetType = MainBottomSheetType.JobTabProfileFilter
                            coroutineScope.launch { bottomSheetState.show() }
                        },
                        onJobPageChanged = { jobTab ->
                            jobTabScreen = jobTab
                        },
                        onUpdateUserType = { type ->
                            mainViewModel.updateJobTabUserType(type)
                        },
                        key = System.currentTimeMillis(),
                    )

                    MainBottomNavItem.Chat -> ChatScreen()
                    MainBottomNavItem.My -> MyScreen(
                        onLogoutClick = {
                            bottomSheetType = MainBottomSheetType.Logout
                            coroutineScope.launch { bottomSheetState.show() }
                        },
                        onWithdrawalClick = {
                            bottomSheetType = MainBottomSheetType.Withdrawal
                            coroutineScope.launch { bottomSheetState.show() }
                        },
                    )
                }

                if (uiState.isFloatingClick) {
                    FloatingDimBackground(mainViewModel)
                }

                MainDialog(dialogState = uiState.mainDialogState, onDismiss = {
                    hideBottomSheet(coroutineScope, bottomSheetState)
                    mainViewModel.clearDialog()
                    FOneNavigator.navigateTo(
                        navDestinationState = NavDestinationState(
                            route = FOneDestinations.Login.route,
                            isPopAll = true,
                        ),
                    )
                })
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
            },
    )
}

@Composable
private fun MainBottomNavigation(
    mainBottomNavItems: Array<MainBottomNavItem>,
    selectedScreen: MainBottomNavItem,
    onItemSelected: (MainBottomNavItem) -> Unit,
) {
    BottomNavigation(
        backgroundColor = FColor.White,
    ) {
        mainBottomNavItems.forEach { bottomNavItem ->
            MainBottomNavigationItem(
                mainBottomNavItems = bottomNavItem,
                selectedMainBottomNavItem = selectedScreen,
                onItemClick = onItemSelected,
            )
        }
    }
}

@Composable
fun RowScope.MainBottomNavigationItem(
    mainBottomNavItems: MainBottomNavItem,
    selectedMainBottomNavItem: MainBottomNavItem,
    onItemClick: (MainBottomNavItem) -> Unit,
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (selectedMainBottomNavItem == mainBottomNavItems) {
                        mainBottomNavItems.selectedIconRes
                    } else {
                        mainBottomNavItems.unselectedIconRes
                    },
                ),
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = stringResource(id = mainBottomNavItems.title),
                style = fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 12.textDp,
                    lineHeight = 12.textDp,
                    color = if (selectedMainBottomNavItem == mainBottomNavItems) {
                        FColor.Primary
                    } else {
                        FColor.DisablePlaceholder
                    },
                ),
            )
        },
        alwaysShowLabel = true,
        selectedContentColor = FColor.Primary,
        unselectedContentColor = FColor.DisablePlaceholder,
        selected = selectedMainBottomNavItem == mainBottomNavItems,
        onClick = { onItemClick(mainBottomNavItems) },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LogoutBottomSheet(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    onLogoutClick: () -> Unit,
) {
    PairButtonBottomSheet(
        modifier = modifier,
        content = {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.my_logout_sheet_title),
                style = LocalTypography.current.h2(),
                color = FColor.TextPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.my_logout_sheet_subtitle),
                style = LocalTypography.current.subtitle2(),
                color = FColor.TextSecondary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(44.dp))
        },
        onLeftButtonClick = {
            hideBottomSheet(coroutineScope, bottomSheetState)
        },
        onRightButtonClick = onLogoutClick,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun WithdrawalBottomSheet(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    onSignOutClick: () -> Unit,
) {
    PairButtonBottomSheet(
        modifier = modifier,
        content = {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.my_withdrawal_sheet_title),
                style = LocalTypography.current.h2(),
                color = FColor.TextPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.my_withdrawal_sheet_subtitle),
                style = LocalTypography.current.h5(),
                color = FColor.TextSecondary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(40.dp))
        },
        onLeftButtonClick = {
            hideBottomSheet(coroutineScope, bottomSheetState)
        },
        onRightButtonClick = onSignOutClick,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun JobTabJobOpeningsFilterBottomSheet(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    currentJobFilterType: JobFilterType,
    onJobFilterTypeClick: (JobFilterType) -> Unit,
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(shape = RoundedCornerShape(10.dp), color = FColor.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Spacer(
            modifier = Modifier
                .width(45.dp)
                .height(4.dp)
                .clip(shape = RoundedCornerShape(2.dp))
                .background(shape = RoundedCornerShape(2.dp), color = FColor.Divider1),
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 22.dp),
            text = stringResource(id = R.string.job_tab_filter_title),
            style = LocalTypography.current.b4(),
            color = FColor.DisablePlaceholder,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle {
                    onJobFilterTypeClick(JobFilterType.Recent)
                    hideBottomSheet(coroutineScope, bottomSheetState)
                }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = JobFilterType.Recent.titleRes),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = if (currentJobFilterType == JobFilterType.Recent) {
                    FColor.Primary
                } else {
                    FColor.TextSecondary
                },
            ),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle {
                    onJobFilterTypeClick(JobFilterType.View)
                    hideBottomSheet(coroutineScope, bottomSheetState)
                }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = JobFilterType.View.titleRes),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = if (currentJobFilterType == JobFilterType.View) {
                    FColor.Primary
                } else {
                    FColor.TextSecondary
                },
            ),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle {
                    onJobFilterTypeClick(JobFilterType.Deadline)
                    hideBottomSheet(coroutineScope, bottomSheetState)
                }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = JobFilterType.Deadline.titleRes),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = if (currentJobFilterType == JobFilterType.Deadline) {
                    FColor.Primary
                } else {
                    FColor.TextSecondary
                },
            ),
        )

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun JobTabProfileFilterBottomSheet(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    currentJobFilterType: JobFilterType,
    onJobFilterTypeClick: (JobFilterType) -> Unit,
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(shape = RoundedCornerShape(10.dp), color = FColor.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Spacer(
            modifier = Modifier
                .width(45.dp)
                .height(4.dp)
                .clip(shape = RoundedCornerShape(2.dp))
                .background(shape = RoundedCornerShape(2.dp), color = FColor.Divider1),
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 22.dp),
            text = stringResource(id = R.string.job_tab_filter_title),
            style = LocalTypography.current.b4(),
            color = FColor.DisablePlaceholder,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle {
                    onJobFilterTypeClick(JobFilterType.Recent)
                    hideBottomSheet(coroutineScope, bottomSheetState)
                }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = JobFilterType.Recent.titleRes),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = if (currentJobFilterType == JobFilterType.Recent) {
                    FColor.Primary
                } else {
                    FColor.TextSecondary
                },
            ),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle {
                    onJobFilterTypeClick(JobFilterType.View)
                    hideBottomSheet(coroutineScope, bottomSheetState)
                }
                .padding(vertical = 12.dp, horizontal = 22.dp),
            text = stringResource(id = JobFilterType.View.titleRes),
            style = fTextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 14.textDp,
                lineHeight = 18.textDp,
                color = if (currentJobFilterType == JobFilterType.View) {
                    FColor.Primary
                } else {
                    FColor.TextSecondary
                },
            ),
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun hideBottomSheet(
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
) {
    coroutineScope.launch {
        bottomSheetState.hide()
    }
}

@Composable
private fun JobFloatingButton(
    modifier: Modifier = Modifier,
    currentJobTab: JobTab,
    isFloatingClick: Boolean,
    onFloatingClick: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            if (isFloatingClick) {
                Column(
                    modifier = Modifier.width(106.dp),
                    horizontalAlignment = Alignment.End,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                            .background(
                                shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp),
                                color = FColor.Primary,
                            )
                            .clickableSingle {
                                FOneNavigator.navigateTo(
                                    navDestinationState = NavDestinationState(
                                        route = if (currentJobTab == JobTab.JOB_OPENING) {
                                            FOneDestinations.ActorRecruitingRegister.route
                                        } else {
                                            FOneDestinations.ActorProfileRegister.route
                                        },
                                    ),
                                )
                                onFloatingClick(isFloatingClick.not())
                            }
                            .padding(horizontal = 17.dp, vertical = 11.dp),
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(
                                id = if (currentJobTab == JobTab.JOB_OPENING) {
                                    R.string.job_tab_fab_actor
                                } else {
                                    R.string.job_tab_profile_fab_actor
                                },
                            ),
                            style = fTextStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 14.textDp,
                                lineHeight = 18.textDp,
                                color = FColor.BgBase,
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }

                    Divider(color = FColor.ColorF43663)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomStart = 6.dp,
                                    bottomEnd = 6.dp,
                                ),
                            )
                            .background(
                                shape = RoundedCornerShape(
                                    bottomStart = 6.dp,
                                    bottomEnd = 6.dp,
                                ),
                                color = FColor.Primary,
                            )
                            .clickableSingle {
                                FOneNavigator.navigateTo(
                                    navDestinationState = NavDestinationState(
                                        route = if (currentJobTab == JobTab.JOB_OPENING) {
                                            FOneDestinations.StaffRecruitingRegister.route
                                        } else {
                                            FOneDestinations.StaffProfileRegister.route
                                        },
                                    ),
                                )
                                onFloatingClick(isFloatingClick.not())
                            }
                            .padding(horizontal = 17.dp, vertical = 11.dp),
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(
                                id = if (currentJobTab == JobTab.JOB_OPENING) {
                                    R.string.job_tab_fab_staff
                                } else {
                                    R.string.job_tab_profile_fab_staff
                                },
                            ),
                            style = fTextStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 14.textDp,
                                lineHeight = 18.textDp,
                                color = FColor.BgBase,
                            ),
                            textAlign = TextAlign.Center,
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
                },
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        id = if (currentJobTab == JobTab.JOB_OPENING) {
                            R.drawable.job_tab_floating_image
                        } else {
                            R.drawable.job_tab_profile_floating_image
                        },
                    ),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun MainDialog(
    dialogState: MainDialogState,
    onDismiss: () -> Unit,
) {
    when (dialogState) {
        MainDialogState.Clear -> Unit
        MainDialogState.WithdrawalComplete -> WithdrawalCompleteDialog(onDismiss = onDismiss)
    }
}

@Composable
private fun WithdrawalCompleteDialog(
    onDismiss: () -> Unit,
) {
    SingleButtonDialog(
        titleText = stringResource(id = R.string.main_withdrawal_dialog_title),
        buttonText = stringResource(id = R.string.confirm),
    ) {
        onDismiss()
    }
}
