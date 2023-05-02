package com.fone.filmone.ui.main.job

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.toastPadding

@Composable
fun JobScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .defaultSystemBarPadding()
            .toastPadding()
    ) {
        Text("JOB")
    }
}