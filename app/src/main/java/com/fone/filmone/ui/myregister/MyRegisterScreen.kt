package com.fone.filmone.ui.myregister

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fone.filmone.R
import com.fone.filmone.ui.common.FTitleBar
import com.fone.filmone.ui.common.TitleType
import com.fone.filmone.ui.common.dialog.PairButtonDialog
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyRegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MyRegisterViewModel = hiltViewModel(),
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.fetchMyRegisterContents()
    }

    Column(modifier = modifier.defaultSystemBarPadding()) {
        FTitleBar(
            titleType = TitleType.Back,
            titleText = stringResource(id = R.string.my_register_title_text),
            onBackClick = {
                navController.popBackStack()
            },
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = FColor.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = FColor.Primary,
                )
            },
        ) {
            repeat(MyRegisterTab.values().size) { index ->
                val myRegisterTab =
                    MyRegisterTab.values().find { it.index == index } ?: return@repeat
                val selected = pagerState.currentPage == index
                Tab(
                    text = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            text = stringResource(id = myRegisterTab.titleRes),
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
                            textAlign = TextAlign.Center,
                        )
                    },
                    selected = selected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> MyRecruitmentScreen(registerPosts = uiState.registerPosts)
                1 -> MyProfileScreen(profilePosts = uiState.profilePosts)
            }
        }

        MyRegisterDialog(dialogState = dialogState)
    }
}

@Composable
private fun MyRegisterDialog(
    dialogState: MyRegisterDialogState,
    viewModel: MyRegisterViewModel = hiltViewModel(),
) {
    when (dialogState) {
        MyRegisterDialogState.Clear -> Unit
        is MyRegisterDialogState.RemoveContent -> {
            PairButtonDialog(
                titleText = stringResource(id = R.string.my_register_dialog_delete_title),
                leftButtonText = stringResource(id = R.string.no),
                rightButtonText = stringResource(id = R.string.yes),
                onLeftButtonClick = {
                    viewModel.updateDialogState(MyRegisterDialogState.Clear)
                },
                onRightButtonClick = {
                    viewModel.removeJobOpeningContent(dialogState.jobContentId)
                },
            )
        }

        is MyRegisterDialogState.RemoveProfileContent -> {
            PairButtonDialog(
                titleText = stringResource(id = R.string.my_register_dialog_delete_title),
                leftButtonText = stringResource(id = R.string.no),
                rightButtonText = stringResource(id = R.string.yes),
                onLeftButtonClick = {
                    viewModel.updateDialogState(MyRegisterDialogState.Clear)
                },
                onRightButtonClick = {
                    viewModel.removeProfile(dialogState.profileId)
                },
            )
        }
    }
}

private enum class MyRegisterTab(
    @StringRes val titleRes: Int,
    val index: Int,
) {
    RECRUITMENT(R.string.my_register_tab_recruitment_title, 0),
    PROFILE(R.string.my_register_tab_profile_title, 1),
}
