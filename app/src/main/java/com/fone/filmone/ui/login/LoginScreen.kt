package com.fone.filmone.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.ui.theme.FilmOneTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = "test")
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    FilmOneTheme {
        LoginScreen()
    }
}