package com.fone.filmone.ui.common.tag.domain

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.ui.common.tag.ToggleSelectTag
import com.fone.filmone.ui.theme.FilmOneTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DomainTags(
    modifier: Modifier = Modifier,
    currentDomains: List<Domain>,
    onUpdateDomain: (Domain, Boolean) -> Unit
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3,
    ) {
        Domain.values().forEachIndexed { index, domain ->
            ToggleSelectTag(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp, bottom = 8.dp),
                type = domain,
                title = stringResource(id = domain.stringRes),
                isSelected = currentDomains.find { it == domain } != null,
                onClick = onUpdateDomain
            )

            if (Domain.values().lastIndex == index) {
                Box(Modifier.weight(1f))
                Box(Modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DomainTagsPreview() {
    FilmOneTheme {
        Column {
            DomainTags(
                currentDomains = listOf(),
                onUpdateDomain = { _, _ -> }
            )
        }
    }
}