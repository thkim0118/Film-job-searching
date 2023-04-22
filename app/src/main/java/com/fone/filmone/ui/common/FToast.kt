package com.fone.filmone.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import kotlinx.coroutines.delay

@Composable
fun FToast(
    modifier: Modifier = Modifier,
    baseViewModel: BaseViewModel,
    hostState: SnackbarHostState,
    onDismiss: () -> Unit = {}
) {
    val toastEvent by baseViewModel.toastEvent.collectAsState()

    if (toastEvent.isEmptyMessage()) {
        return
    }

    val message = toastEvent.getMessage()

    LaunchedEffect(key1 = message) {
        hostState.showSnackbar(message, duration = SnackbarDuration.Indefinite)
    }

    SnackbarHost(
        modifier = modifier
            .padding(horizontal = 16.dp),
        hostState = hostState,
    ) { snackbarData ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(5.dp))
                .background(color = FColor.Violet800, shape = RoundedCornerShape(5.dp))
                .padding(horizontal = 16.dp, vertical = 15.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = snackbarData.message,
                style = LocalTypography.current.b2(),
                color = FColor.White
            )
        }

        LaunchedEffect(true) {
            delay(2_000)
            snackbarData.dismiss()
            baseViewModel.clearToast()
            onDismiss()
        }
    }
}