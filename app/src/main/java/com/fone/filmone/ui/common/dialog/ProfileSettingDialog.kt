package com.fone.filmone.ui.common.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.ui.common.ext.clickableSingle
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun ProfileSettingDialog(
    onAlbumClick: () -> Unit,
    onDefaultProfileClick: () -> Unit,
    onCancelButtonClick: () -> Unit,
) {
    ListDialog(
        titleText = stringResource(id = R.string.dialog_profile_setting_title),
        buttonText = stringResource(id = R.string.dialog_profile_setting_button_title),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableSingle { onAlbumClick() }
                        .padding(vertical = 15.5.dp),
                    text = stringResource(id = R.string.dialog_profile_setting_album),
                    style = LocalTypography.current.subtitle1,
                    color = FColor.Divider2
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableSingle { onDefaultProfileClick() }
                        .padding(vertical = 15.5.dp),
                    text = stringResource(id = R.string.dialog_profile_setting_default_image),
                    style = LocalTypography.current.subtitle1,
                    color = FColor.Divider2
                )
            }
        },
        onButtonClick = onCancelButtonClick,
        onDismissRequest = onCancelButtonClick
    )
}