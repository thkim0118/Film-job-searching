package com.fone.filmone.ui.common.tag.categories

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
fun CategoryTags(
    modifier: Modifier = Modifier,
    currentCategories: List<Category>,
    onUpdateCategories: (Category, Boolean) -> Unit
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        Category.values().forEach { category ->
            ToggleSelectTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                type = category,
                title = stringResource(id = category.stringRes),
                isSelected = currentCategories.find { it == category } != null,
                onClick = onUpdateCategories
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InterestsTagsPreview() {
    FilmOneTheme {
        Column {
            CategoryTags(
                currentCategories = listOf(),
                onUpdateCategories = { _, _ -> }
            )
        }
    }
}