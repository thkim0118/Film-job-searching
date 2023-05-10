package com.fone.filmone.ui.common.tag.job

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fone.filmone.data.datamodel.response.user.Job
import com.fone.filmone.ui.common.ext.textDp
import com.fone.filmone.ui.common.fTextStyle
import com.fone.filmone.ui.theme.FColor

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
            JobTag(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                job = job,
                isSelected = currentJob == job,
                onClick = onUpdateJob
            )
        }
    }
}

@Composable
fun JobTag(
    modifier: Modifier = Modifier,
    job: Job,
    isSelected: Boolean,
    onClick: (Job) -> Unit
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
            .clickable { onClick(job) },
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = job.name,
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