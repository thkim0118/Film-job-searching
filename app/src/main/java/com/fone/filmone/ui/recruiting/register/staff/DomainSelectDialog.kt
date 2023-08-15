package com.fone.filmone.ui.recruiting.register.staff

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.ui.common.tag.domain.DomainTags
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun DomainSelectDialog(
    modifier: Modifier = Modifier,
    selectedDomains: List<Domain>,
    onDismissRequest: (List<Domain>) -> Unit = {},
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false
    ),
) {
    var newSelectedDomains by remember { mutableStateOf(selectedDomains) }

    val dialogModifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .background(color = FColor.BgBase, shape = RoundedCornerShape(10.dp))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FColor.DimColorThin)
            .clickable(onClick = {
                onDismissRequest(newSelectedDomains)
            })
    ) {
        Dialog(
            onDismissRequest = {
                onDismissRequest(newSelectedDomains)
            },
            properties = properties,
        ) {
            Column(
                modifier = dialogModifier
                    .padding(horizontal = 20.dp)
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val (titleRef, imageRefs) = createRefs()

                    Text(
                        modifier = Modifier
                            .constrainAs(titleRef) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top, margin = 12.dp)
                            },
                        text = stringResource(id = R.string.recruiting_register_staff_domain_popup_title),
                        style = LocalTypography.current.h2()
                    )

                    Image(
                        modifier = Modifier
                            .constrainAs(imageRefs) {
                                top.linkTo(parent.top, margin = 13.dp)
                                end.linkTo(parent.end, margin = 20.dp)
                            }
                            .clickable { onDismissRequest(newSelectedDomains) },
                        imageVector = ImageVector.vectorResource(id = R.drawable.staff_recruiting_dialog_close),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                DomainTags(
                    currentDomains = newSelectedDomains,
                    onUpdateDomain = { domain, enable ->
                        newSelectedDomains = if (enable) {
                            newSelectedDomains + domain
                        } else {
                            newSelectedDomains.filter { it != domain }
                        }
                    },
                )

                Spacer(modifier = Modifier.height(79.dp))
            }
        }
    }
}