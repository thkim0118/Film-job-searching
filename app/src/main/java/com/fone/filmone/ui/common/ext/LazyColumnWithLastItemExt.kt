package com.fone.filmone.ui.common.ext

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LazyColumnLastItemCallback(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    onLastItemCallback: (index: Int) -> Unit,
    content: LazyListScope.() -> Unit
) {
    val itemStatus = rememberSaveable { mutableStateOf(ItemCallbackStatus.ITEM) }
    val layoutInfo = remember { derivedStateOf { state.layoutInfo } }

    fun isScrolledToTheEnd() =
        layoutInfo.value.visibleItemsInfo.lastOrNull()?.index == layoutInfo.value.totalItemsCount - 1


    Box {
        LazyColumn(
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            content = content
        )

        if (isScrolledToTheEnd().not()) {
            itemStatus.value = ItemCallbackStatus.ITEM
        }

        if (itemStatus.value == ItemCallbackStatus.ITEM && isScrolledToTheEnd()) {
            itemStatus.value = ItemCallbackStatus.LAST_ITEM_FOUND
        }

        if (itemStatus.value == ItemCallbackStatus.LAST_ITEM_FOUND && isScrolledToTheEnd()) {
            layoutInfo.value.visibleItemsInfo.lastOrNull()?.index?.let {
                onLastItemCallback.invoke(it)
            }
            itemStatus.value = ItemCallbackStatus.LAST_ITEM_INVOKED
        }
    }
}

private enum class ItemCallbackStatus {
    LAST_ITEM_FOUND, LAST_ITEM_INVOKED, ITEM
}