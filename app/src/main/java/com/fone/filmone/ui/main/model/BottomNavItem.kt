package com.fone.filmone.ui.main.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fone.filmone.R

enum class BottomNavItem(
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    @StringRes val title: Int,
) {
    Home(
        selectedIconRes = R.drawable.main_home_selected,
        unselectedIconRes = R.drawable.main_home_unselected,
        title = R.string.main_home_title
    ),
    Job(
        selectedIconRes = R.drawable.main_job_selected,
        unselectedIconRes = R.drawable.main_job_unselected,
        title = R.string.main_job_title,
    ),
    Chat(
        selectedIconRes = R.drawable.main_chat_selected,
        unselectedIconRes = R.drawable.main_chat_unselected,
        title = R.string.main_chat_title,
    ),
    My(
        selectedIconRes = R.drawable.main_my_selected,
        unselectedIconRes = R.drawable.main_my_unselected,
        title = R.string.main_my_title,
    )
}
