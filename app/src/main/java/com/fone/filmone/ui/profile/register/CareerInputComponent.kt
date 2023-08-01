package com.fone.filmone.ui.profile.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fone.filmone.R
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.ui.common.FTextField
import com.fone.filmone.ui.common.tag.career.CareerTags
import com.fone.filmone.ui.main.job.common.TextLimitComponent

@Composable
fun CareerInputComponent(
    modifier: Modifier = Modifier,
    currentCareer: Career?,
    careerDetail: String,
    careerDetailTextLimit: Int,
    onUpdateCareer: (Career, Boolean) -> Unit,
    onUpdateCareerDetail: (String) -> Unit
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        CareerTags(currentCareers = currentCareer, onUpdateCareer = onUpdateCareer)

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
            fixedHeight = 139.dp
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}
