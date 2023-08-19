package com.fone.filmone.ui.main.job.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.theme.FColor

@Composable
fun DomainInputComponent(
    modifier: Modifier = Modifier,
    selectedDomains: List<Domain>,
    onUpdateDomains: () -> Unit
) {
    Row(
        modifier = modifier
            .background(color = FColor.BgGroupedBase)
            .padding(vertical = 13.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextWithRequired(
            title = stringResource(id = R.string.recruiting_register_staff_domain_title),
            isRequired = true,
        )

        Spacer(modifier = Modifier.width(28.dp))

        SelectedDomainComponent(
            selectedDomains = selectedDomains,
            onDomainClick = onUpdateDomains
        )
    }
}

@Composable
private fun SelectedDomainComponent(
    modifier: Modifier = Modifier,
    selectedDomains: List<Domain>,
    onDomainClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 42.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(shape = RoundedCornerShape(5.dp), color = FColor.BgBase)
            .clickableSingle { onDomainClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))

        when {
            selectedDomains.size == Domain.values().size -> {
                TagComponent(
                    title = stringResource(id = R.string.recruiting_register_staff_domain_select_all),
                    enable = true,
                    clickable = false,
                    isDomain = true
                )
            }

            selectedDomains.size >= 3 -> {
                TagComponent(
                    title = stringResource(
                        id = R.string.recruiting_register_staff_domain_select_items,
                        selectedDomains.size
                    ),
                    enable = true,
                    clickable = false,
                    isDomain = true
                )
            }

            else -> {
                selectedDomains.forEach {
                    TagComponent(
                        title = stringResource(id = it.stringRes),
                        enable = true,
                        clickable = false,
                        isDomain = true
                    )

                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.staff_recruting_right_arrow),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(13.dp))
    }
}
