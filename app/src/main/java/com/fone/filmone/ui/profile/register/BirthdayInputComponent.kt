package com.fone.filmone.ui.profile.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.FBorderButton
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.main.job.common.TextWithRequiredTag
import com.fone.filmone.ui.theme.FColor
import java.util.regex.Pattern

@Composable
fun BirthdayInputComponent(
    modifier: Modifier = Modifier,
    birthday: String,
    genderTagEnable: Boolean,
    currentGender: Gender?,
    onUpdateBirthday: (String) -> Unit,
    onUpdateGenderTag: (Boolean) -> Unit,
    onUpdateGender: (Gender, Boolean) -> Unit,
) {
    Column(
        modifier = modifier

    ) {
        TextWithRequiredTag(
            title = stringResource(id = R.string.profile_register_birth_title),
            tagTitle = stringResource(id = R.string.profile_register_gender_irrelevant),
            isRequired = true,
            tagEnable = genderTagEnable,
            onTagClick = {
                onUpdateGenderTag(genderTagEnable.not())
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        FTextField(
            text = birthday,
            placeholder = stringResource(id = R.string.profile_register_birth_placeholder),
            placeholderTextColor = FColor.DisablePlaceholder,
            onValueChange = onUpdateBirthday,
            pattern = Pattern.compile(PatternUtil.dateRegex),
            onTextChanged = { before, after ->
                if (before.text.length < after.text.length) {
                    when (after.text.length) {
                        5, 8 -> after.copy(
                            text = "${before.text}-${after.text.last()}",
                            selection = TextRange(after.text.length + 1)
                        )

                        else -> after
                    }
                } else {
                    when (after.text.length) {
                        5, 8 -> after.copy(
                            text = after.text.dropLast(1),
                        )

                        else -> after
                    }
                }
            },
            rightComponents = {
                Spacer(modifier = Modifier.width(8.dp))

                FBorderButton(
                    text = stringResource(id = R.string.gender_man),
                    enable = currentGender == Gender.MAN,
                    onClick = {
                        onUpdateGender(Gender.MAN, (currentGender == Gender.MAN).not())
                    }
                )

                Spacer(modifier = Modifier.width(6.dp))

                FBorderButton(
                    text = stringResource(id = R.string.gender_woman),
                    enable = currentGender == Gender.WOMAN,
                    onClick = {
                        onUpdateGender(Gender.WOMAN, (currentGender == Gender.WOMAN).not())
                    }
                )
            },
            textLimit = 10,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )
    }
}
