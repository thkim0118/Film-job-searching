package com.fone.filmone.ui.splash

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fone.filmone.BuildConfig
import com.fone.filmone.R
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    val remoteConfig = Firebase.remoteConfig
    val configSettings = FirebaseRemoteConfigSettings.Builder()
        .setMinimumFetchIntervalInSeconds(3600)
        .build()
    remoteConfig.setConfigSettingsAsync(configSettings)
    remoteConfig.setDefaultsAsync(
        mapOf(
            "requiredVersionName" to "1.0.0",
            "requiredVersionCode" to "5",
        )
    )

    fun isVersionOutdated(currentVersion: String, requiredVersion: String): Boolean {
        val currentParts = currentVersion.split(".").map { it.toIntOrNull() ?: 0 }
        val requiredParts = requiredVersion.split(".").map { it.toIntOrNull() ?: 0 }

        println("currentParts $currentParts")
        println("requiredParts $requiredParts")
        for (i in 0..2) {
            when {
                currentParts[i] < requiredParts[i] -> return true
                currentParts[i] > requiredParts[i] -> return false
            }
        }

        return false
    }

    var isOutdated by remember { mutableStateOf(false) }
    remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val requiredVersion = remoteConfig.getString("requiredVersionName")
            val requiredVersionCode = remoteConfig.getString("requiredVersionCode").toInt()
            val currentVersion = BuildConfig.VERSION_NAME
            val currentVersionCode = BuildConfig.VERSION_CODE

            if (isVersionOutdated(
                    currentVersion,
                    requiredVersion
                ) || currentVersionCode < requiredVersionCode
            ) {
                println("version outdated")

                isOutdated = true
            } else {
                println("version updated")
            }
        } else {
            println("remoteConfig fail")
        }
    }

    if (isOutdated) {
    } else {
        LaunchedEffect(key1 = true) {
            delay(2000L)
            when (uiState) {
                SplashUiState.AutoLoginUser -> {
                    FOneNavigator.navigateTo(
                        navDestinationState = NavDestinationState(
                            route = FOneDestinations.Main.route,
                            isPopAll = true
                        )
                    )
                }

                SplashUiState.NotLoginUser -> {
                    FOneNavigator.navigateTo(
                        navDestinationState = NavDestinationState(
                            route = FOneDestinations.Login.route,
                            isPopAll = true
                        )
                    )
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = FColor.Black),
        contentAlignment = Alignment.Center
    ) {
        if (isOutdated) {
            Card(
                modifier = Modifier
                    .width(323.dp)
                    .height(173.dp)
                    .background(Color.Black),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(42.dp))

                    Text(
                        text = "앱 버전이 다릅니다.\n보다 좋은 서비스를 위해 업데이트 해주세요",
                        style = TextStyle(fontSize = 14.sp),
                        color = FColor.Gray900,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(42.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.5.dp)
                            .background(FColor.Gray300)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        TextButton(
                            onClick = {
                                (context as Activity).finish()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "닫기", color = FColor.Gray900)
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.5.dp)
                                .background(FColor.Gray300)
                        )

                        TextButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse("market://details?id=com.fone.filmone")
                                    setPackage("com.android.vending")
                                }

                                context.startActivity(intent)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "업데이트", color = FColor.Primary)
                        }
                    }
                }
            }
        } else {
            Image(
                modifier = Modifier.align(Alignment.Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.splash_fone_logo),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    FilmOneTheme {
        SplashScreen()
    }
}
