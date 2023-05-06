package com.fone.filmone.ui.main

sealed interface MainBottomSheetType {
    object Logout : MainBottomSheetType
    object Withdrawal : MainBottomSheetType

    object JobTabJopOpeningsFilter : MainBottomSheetType

    object JobTabProfileFilter : MainBottomSheetType
}