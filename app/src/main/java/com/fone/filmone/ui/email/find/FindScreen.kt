package com.fone.filmone.ui.email.find

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.FToast
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.dialog.SingleButtonDialog
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FindScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: FindViewModel = hiltViewModel(),
) {
    val pagerState = rememberPagerState(pageCount = { FindTab.values().size })
    val coroutineScope = rememberCoroutineScope()
    val dialogState by viewModel.dialogState.collectAsState()

    Scaffold(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding(),
        snackbarHost = {
            FToast(baseViewModel = viewModel, hostState = it)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                FTitleBar(
                    titleType = TitleType.Back,
                    titleText = stringResource(id = R.string.find_title),
                    onBackClick = {
                        navController.popBackStack()
                    }
                )

                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = FColor.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = FColor.Primary
                        )
                    }
                ) {
                    repeat(FindTab.values().size) { index ->
                        val findTab = FindTab.values().find { it.index == index } ?: return@repeat
                        val selected = pagerState.currentPage == index
                        Tab(
                            text = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 10.dp),
                                    text = stringResource(id = findTab.titleRes),
                                    style = fTextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.textDp,
                                        lineHeight = 16.8.textDp,
                                        color = if (selected) {
                                            FColor.Primary
                                        } else {
                                            FColor.DisablePlaceholder
                                        },
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            },
                            selected = selected,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            }
                        )
                    }
                }

                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> FindIdScreen(navController = navController)
                        1 -> FindPasswordScreen()
                    }
                }
            }

            DialogScreen(
                dialogState = dialogState,
                onPasswordChangingConfirmClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun DialogScreen(
    dialogState: FindDialogState,
    onPasswordChangingConfirmClick: () -> Unit,
) {
    when (dialogState) {
        FindDialogState.Clear -> Unit
        FindDialogState.PasswordChangingComplete -> {
            PasswordChangingCompleteDialog(onClick = onPasswordChangingConfirmClick)
        }
    }
}

@Composable
private fun PasswordChangingCompleteDialog(
    onClick: () -> Unit,
) {
    LocalFocusManager.current.clearFocus()

    SingleButtonDialog(
        titleText = stringResource(id = R.string.find_password_complete_dialog_message),
        buttonText = stringResource(id = R.string.confirm)
    ) {
        onClick()
    }
}

private enum class FindTab(
    @StringRes val titleRes: Int,
    val index: Int,
) {
    FIND_ID(R.string.find_id_tab_title, 0),
    FIND_PASSWORD(R.string.find_password_tab_title, 1)
}
