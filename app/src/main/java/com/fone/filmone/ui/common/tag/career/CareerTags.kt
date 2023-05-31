package com.fone.filmone.ui.common.tag.career

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.ui.common.tag.ToggleSelectTag

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CareerTags(
    modifier: Modifier = Modifier,
    currentCareers: Career?,
    onUpdateCareer: (Career, Boolean) -> Unit
) {
    val careerTags = Career.values().dropLast(1)

    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        careerTags.forEach { career ->
            ToggleSelectTag(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp, bottom = 8.dp),
                type = career,
                title = stringResource(id = career.titleRes),
                isSelected = currentCareers == career,
                onClick = onUpdateCareer
            )
        }
    }
}
