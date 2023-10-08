package com.fone.filmone.ui.profile.common.component

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.tag.career.CareerTags
import com.fone.filmone.ui.main.job.common.TextLimitComponent
import com.fone.filmone.ui.main.job.common.TextWithRequired
import com.fone.filmone.ui.profile.common.actor.model.ActorProfileFocusEvent
import com.fone.filmone.ui.profile.common.staff.model.StaffProfileFocusEvent

@Composable
fun CareerInputComponent(
    modifier: Modifier = Modifier,
    currentCareer: Career?,
    careerDetail: String,
    careerDetailTextLimit: Int,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerDetail: (String) -> Unit,
    actorFocusEvent: ActorProfileFocusEvent?,
    staffFocusEvent: StaffProfileFocusEvent?
) {
    val careerRequester = remember { FocusRequester() }
    val careerDetailRequester = remember { FocusRequester() }

    LaunchedEffect(actorFocusEvent) {
        when (actorFocusEvent) {
            ActorProfileFocusEvent.Career -> careerRequester.requestFocus()
            ActorProfileFocusEvent.CareerDetail -> careerDetailRequester.requestFocus()
            else -> Unit
        }
    }

    LaunchedEffect(staffFocusEvent) {
        when (staffFocusEvent) {
            StaffProfileFocusEvent.Career -> careerRequester.requestFocus()
            StaffProfileFocusEvent.CareerDetail -> careerDetailRequester.requestFocus()
            else -> Unit
        }
    }

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        TextWithRequired(
            title = stringResource(id = R.string.profile_register_career_title),
            isRequired = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        CareerTags(currentCareers = currentCareer, onUpdateCareer = onUpdateCareer, modifier = Modifier.focusRequester(careerRequester).focusable())

        Spacer(modifier = Modifier.height(10.dp))

        FTextField(
            text = careerDetail,
            onValueChange = onUpdateCareerDetail,
            placeholder = stringResource(id = R.string.profile_register_career_placeholder),
            textLimit = careerDetailTextLimit,
            singleLine = false,
            maxLines = Int.MAX_VALUE,
            bottomComponent = {
                Spacer(modifier = Modifier.height(2.dp))

                TextLimitComponent(
                    modifier = Modifier.align(Alignment.End),
                    currentTextSize = careerDetail.length,
                    maxTextSize = careerDetailTextLimit
                )
            },
            fixedHeight = 139.dp,
            modifier = Modifier.focusRequester(careerDetailRequester),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}
