package com.fone.filmone.ui.common.tag.interests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.ui.common.tag.ToggleSelectTag
import com.fone.filmone.ui.theme.FilmOneTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsTags(
    modifier: Modifier = Modifier,
    currentInterests: List<Category>,
    onUpdateInterests: (Category, Boolean) -> Unit
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        Category.values().forEach { interests ->
            ToggleSelectTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                type = interests,
                title = stringResource(id = interests.stringRes),
                isSelected = currentInterests.find { it == interests } != null,
                onClick = onUpdateInterests
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InterestsTagsPreview() {
    FilmOneTheme {
        Column {
            InterestsTags(
                currentInterests = listOf(),
                onUpdateInterests = { _, _ -> }
            )
        }
    }
}