package com.fone.filmone.ui.inquiry

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fone.filmone.R
import com.fone.filmone.ui.common.*
import com.fone.filmone.ui.common.ext.defaultSystemBarPadding
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme
import com.fone.filmone.ui.theme.LocalTypography

@Composable
fun InquiryScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Column(modifier = modifier.defaultSystemBarPadding()) {
        FTitleBar(
            titleType = TitleType.Close,
            titleText = stringResource(id = R.string.inquiry_title_text),
            onCloseClick = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.inquiry_guide),
                style = LocalTypography.current.b3,
                color = FColor.TextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            EmailInputComponent()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.inquiry_type_title),
                style = LocalTypography.current.subtitle1,
                color = FColor.TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            InquiryTypeComponent()

            Spacer(modifier = Modifier.height(20.dp))

            InquiryTitleComponent()

            Spacer(modifier = Modifier.height(20.dp))

            InquiryDescriptionComponent()

            Spacer(modifier = Modifier.height(38.dp))

            PrivacyTermComponent()

            Spacer(modifier = Modifier.height(68.dp))

            SubmissionButton()

            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}

@Composable
private fun EmailInputComponent() {
    FTextField(
        onValueChange = { value ->

        },
        topText = TopText(
            title = stringResource(id = R.string.inquiry_email_title),
            titleStar = false,
            titleSpace = 6.dp
        )
    )
}

@Composable
private fun InquiryTypeComponent() {
    Row {
        FBorderButton(
            text = stringResource(id = R.string.inquiry_type_using_service),
            enable = false,
            onClick = {}
        )

        Spacer(modifier = Modifier.width(8.dp))

        FBorderButton(
            text = stringResource(id = R.string.inquiry_type_partnership),
            enable = false,
            onClick = {}
        )

        Spacer(modifier = Modifier.width(8.dp))

        FBorderButton(
            text = stringResource(id = R.string.inquiry_type_user_report),
            enable = false,
            onClick = {}
        )
    }
}

@Composable
private fun InquiryTitleComponent() {
    FTextField(
        onValueChange = { value -> },
        topText = TopText(
            title = stringResource(id = R.string.inquiry_content_title),
            titleStar = false,
            titleSpace = 6.dp
        )
    )
}

@Composable
private fun InquiryDescriptionComponent() {
    FTextField(
        onValueChange = { value -> },
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
private fun PrivacyTermComponent() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        FRadioButton(enable = false)

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
private fun SubmissionButton() {
    FButton(
        title = stringResource(id = R.string.inquiry_button_title),
        enable = false
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun InquiryScreenPreview() {
    FilmOneTheme {
        InquiryScreen()
    }
}
