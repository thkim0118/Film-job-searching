package com.fone.filmone.ui.common.tag.interests

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fone.filmone.data.datamodel.response.common.user.Category
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor

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
            InterestsTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                category = interests,
                isSelected = currentInterests.find { it == interests } != null,
                onClick = onUpdateInterests
            )
        }
    }
}

@Composable
fun InterestsTag(
    modifier: Modifier = Modifier,
    category: Category,
    isSelected: Boolean,
    onClick: (Category, Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(90.dp))
            .background(
                color = if (isSelected) {
                    FColor.Red50
                } else {
                    FColor.BgGroupedBase
                },
                shape = RoundedCornerShape(90.dp)
            )
            .clickable { onClick(category, isSelected.not()) },
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = stringResource(id = category.stringRes),
            style = if (isSelected) {
                fTextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.textDp,
                    lineHeight = 16.8.textDp,
                    color = FColor.Primary
                )
            } else {
                fTextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 14.textDp,
                    lineHeight = 16.8.textDp,
                    color = FColor.DisablePlaceholder
                )
            }
        )
    }
}