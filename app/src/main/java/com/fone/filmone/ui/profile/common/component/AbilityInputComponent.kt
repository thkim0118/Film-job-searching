package com.fone.filmone.ui.profile.common.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fone.filmone.R
import com.fone.filmone.ui.main.job.common.LeftTitleTextField

@Composable
fun AbilityInputComponent(
    modifier: Modifier = Modifier,
    ability: String,
    onUpdateAbility: (String) -> Unit
) {
    LeftTitleTextField(
        modifier = modifier,
        title = stringResource(id = R.string.profile_register_speciality_title),
        titleSpace = 36,
        placeholder = stringResource(id = R.string.profile_register_speciality_placeholder),
        isRequired = false,
        text = ability,
        onValueChanged = onUpdateAbility
    )
}
