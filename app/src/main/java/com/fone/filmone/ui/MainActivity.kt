package com.fone.filmone.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarTransparent()

        setContent {
            FOneApp()
        }
    }

    private fun Activity.setStatusBarTransparent() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
