package com.fone.filmone.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography
import kotlinx.coroutines.delay

@Composable
fun BoxScope.FToast(
    modifier: Modifier = Modifier,
    baseViewModel: BaseViewModel,
    hostState: SnackbarHostState = SnackbarHostState(),
    onDismiss: () -> Unit = {}
) {
    val toastEvent by baseViewModel.toastEvent.collectAsState()

    if (toastEvent.isEmptyMessage()) {
        return
    }

    val message = stringResource(id = toastEvent.messageRes)

    LaunchedEffect(key1 = message) {
        hostState.showSnackbar(message)
    }

    SnackbarHost(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .align(Alignment.BottomCenter),
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
                style = LocalTypography.current.b2,
                color = FColor.White
            )
        }

        LaunchedEffect(true) {
            when (snackbarData.duration) {
                SnackbarDuration.Short -> delay(4_000)
                SnackbarDuration.Long -> delay(10_000)
                SnackbarDuration.Indefinite -> Unit
            }
            baseViewModel.clearToast()
            onDismiss()
        }
    }
}