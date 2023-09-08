package com.fone.filmone.ui.profile.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.ui.common.tag.categories.CategoryTags
import com.fone.filmone.ui.main.job.common.TextWithRequiredTag

@Composable
fun CategorySelectComponent(
    modifier: Modifier = Modifier,
    categoryTagEnable: Boolean,
    currentCategory: List<Category>,
    onCategoryTagClick: (Boolean) -> Unit,
    onUpdateCategory: (Category, Boolean) -> Unit
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequiredTag(
            title = stringResource(R.string.profile_register_category_title),
            tagTitle = stringResource(id = R.string.profile_register_category_all),
            isRequired = false,
            tagEnable = categoryTagEnable,
            onTagClick = {
                onCategoryTagClick(categoryTagEnable.not())
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        CategoryTags(
            currentCategories = currentCategory,
            onUpdateCategories = onUpdateCategory
        )

        Spacer(modifier = Modifier.height(58.dp))
    }
}
