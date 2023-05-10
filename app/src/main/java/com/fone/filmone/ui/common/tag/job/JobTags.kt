package com.fone.filmone.ui.common.tag.job

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.ui.common.tag.SingleSelectTag

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobTags(
    modifier: Modifier = Modifier,
    currentJob: Job?,
    onUpdateJob: (Job) -> Unit
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {
        Job.values().forEach { job ->
            SingleSelectTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                type = job,
                isSelected = currentJob == job,
                onClick = onUpdateJob
            )
        }
    }
}
