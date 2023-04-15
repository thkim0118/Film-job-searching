package com.fone.filmone.ui.inquiry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.domain.model.inquiry.InquiryType
import com.fone.filmone.ui.common.*
import com.fone.filmone.ui.common.ext.clickableWithNoRipple
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.common.ext.toastPadding
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun InquiryScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: InquiryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .defaultSystemBarPadding()
            .toastPadding()
    ) {
        Column(
            modifier = Modifier
        ) {
            FTitleBar(
                titleType = TitleType.Close,
                titleText = stringResource(id = R.string.inquiry_title_text),
                onCloseClick = {
                    navController.popBackStack()
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = stringResource(id = R.string.inquiry_guide),
                        style = LocalTypography.current.b3,
                        color = FColor.TextSecondary
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    EmailInputComponent(
                        uiState = uiState,
                        onValueChanged = viewModel::updateEmail
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(id = R.string.inquiry_type_title),
                        style = LocalTypography.current.subtitle1,
                        color = FColor.TextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    InquiryTypeComponent(
                        uiState = uiState,
                        onUpdateInquiryType = viewModel::updateInquiryType
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    InquiryTitleComponent(
                        uiState = uiState,
                        onValueChanged = viewModel::updateTitle
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    InquiryDescriptionComponent(
                        uiState = uiState,
                        onValueChanged = viewModel::updateDescription
                    )

                    Spacer(modifier = Modifier.height(38.dp))

                    PrivacyTermComponent(
                        uiState = uiState,
                        onClick = viewModel::updatePrivacyInformation
                    )

                    Spacer(modifier = Modifier.height(68.dp))
                }

                SubmissionButton(
                    onClick = { viewModel.submitInquiry() }
                )

                Spacer(modifier = Modifier.height(38.dp))
            }
        }

        InquiryToast(
            viewModel = viewModel,
            uiState = uiState,
            navController = navController
        )
    }
}

@Composable
private fun BoxScope.InquiryToast(
    viewModel: InquiryViewModel,
    uiState: InquiryUiState,
    navController: NavHostController
) {
    FToast(baseViewModel = viewModel) {
        if (uiState.isInquirySuccess) {
            navController.popBackStack()
        }
    }
}

@Composable
private fun EmailInputComponent(
    uiState: InquiryUiState,
    onValueChanged: (String) -> Unit
) {
    FTextField(
        text = uiState.email,
        onValueChange = onValueChanged,
        topText = TopText(
            title = stringResource(id = R.string.inquiry_email_title),
            titleStar = false,
            titleSpace = 6.dp
        )
    )
}

@Composable
private fun InquiryTypeComponent(
    uiState: InquiryUiState,
    onUpdateInquiryType: (InquiryType) -> Unit
) {
    Row {
        FBorderButton(
            text = stringResource(id = InquiryType.USE_QUESTION.titleRes),
            enable = uiState.inquiryType == InquiryType.USE_QUESTION,
            onClick = { onUpdateInquiryType.invoke(InquiryType.USE_QUESTION) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        FBorderButton(
            text = stringResource(id = InquiryType.ALLIANCE.titleRes),
            enable = uiState.inquiryType == InquiryType.ALLIANCE,
            onClick = { onUpdateInquiryType.invoke(InquiryType.ALLIANCE) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        FBorderButton(
            text = stringResource(id = InquiryType.VOICE_OF_THE_CUSTOMER.titleRes),
            enable = uiState.inquiryType == InquiryType.VOICE_OF_THE_CUSTOMER,
            onClick = { onUpdateInquiryType.invoke(InquiryType.VOICE_OF_THE_CUSTOMER) }
        )
    }
}

@Composable
private fun InquiryTitleComponent(
    uiState: InquiryUiState,
    onValueChanged: (String) -> Unit
) {
    FTextField(
        text = uiState.title,
        onValueChange = onValueChanged,
        topText = TopText(
            title = stringResource(id = R.string.inquiry_content_title),
            titleStar = false,
            titleSpace = 6.dp
        )
    )
}

@Composable
private fun InquiryDescriptionComponent(
    uiState: InquiryUiState,
    onValueChanged: (String) -> Unit
) {
    FTextField(
        text = uiState.description,
        onValueChange = onValueChanged,
        topText = TopText(
            title = stringResource(id = R.string.inquiry_content_description),
            titleStar = false,
            titleSpace = 6.dp
        ),
        placeholder = stringResource(id = R.string.inquiry_content_description_placeholder),
        singleLine = false,
        maxLines = Int.MAX_VALUE,
        fixedHeight = 134.dp
    )
}

@Composable
private fun PrivacyTermComponent(
    uiState: InquiryUiState,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickableWithNoRipple { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FRadioButton(
            enable = uiState.isAgreePersonalInformation,
            onClick = onClick
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = stringResource(id = R.string.inquiry_privacy_term_title),
            style = LocalTypography.current.subtitle1,
            color = FColor.TextSecondary
        )
    }

    Spacer(modifier = Modifier.height(6.dp))

    Text(
        modifier = Modifier
            .padding(start = (16 + 6).dp),
        text = stringResource(id = R.string.inquiry_privacy_term_subtitle),
        style = LocalTypography.current.label,
        color = FColor.DisablePlaceholder
    )
}

@Composable
private fun ColumnScope.SubmissionButton(
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.weight(1f))

    FButton(
        title = stringResource(id = R.string.inquiry_button_title),
        enable = true
    ) {
        onClick.invoke()
    }
}

@Preview(showBackground = true)
@Composable
fun InquiryScreenPreview() {
    FilmOneTheme {
        InquiryScreen()
    }
}