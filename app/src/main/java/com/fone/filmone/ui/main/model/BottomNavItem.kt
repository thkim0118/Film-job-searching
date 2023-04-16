package com.fone.filmone.ui.main.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fone.filmone.R

enum class BottomNavItem(
    val index: Int,
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    val label: String,
    @StringRes val title: Int,
) {
    Home(
        index = 0,
        selectedIconRes = R.drawable.main_home_selected,
        unselectedIconRes = R.drawable.main_home_unselected,
        label = "Home",
        title = R.string.main_home_title
    ),
    Job(
        index = 1,
        selectedIconRes = R.drawable.main_job_selected,
        unselectedIconRes = R.drawable.main_job_unselected,
        label = "Job",
        title = R.string.main_job_title,
    ),
    Chat(
        index = 2,
        selectedIconRes = R.drawable.main_chat_selected,
        unselectedIconRes = R.drawable.main_chat_unselected,
        label = "Chat",
        title = R.string.main_chat_title,
    ),
    My(
        index = 3,
        selectedIconRes = R.drawable.main_my_selected,
        unselectedIconRes = R.drawable.main_my_unselected,
        label = "My",
        title = R.string.main_my_title,
    )
}
