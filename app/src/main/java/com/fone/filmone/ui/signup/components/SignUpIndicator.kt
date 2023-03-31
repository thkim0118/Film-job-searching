package com.fone.filmone.ui.signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fone.filmone.ui.theme.FColor
import com.fone.filmone.ui.theme.FilmOneTheme

@Composable
fun SignUpIndicator(
    modifier: Modifier = Modifier,
    indicatorType: IndicatorType
) {
    val indicatorSelectedStates = MutableList(3) { false }
    when (indicatorType) {
        IndicatorType.First -> indicatorSelectedStates[0] = true
        IndicatorType.Second -> indicatorSelectedStates[1] = true
        IndicatorType.Third -> indicatorSelectedStates[2] = true
    }

    Row(modifier = modifier) {
        Indicator(isSelected = indicatorSelectedStates[0])
        Spacer(modifier = Modifier.width(8.dp))
        Indicator(isSelected = indicatorSelectedStates[1])
        Spacer(modifier = Modifier.width(8.dp))
        Indicator(isSelected = indicatorSelectedStates[2])
    }
}

@Composable
private fun Indicator(
    isSelected: Boolean
) {
    if (isSelected) {
        EnabledIndicator()
    } else {
        DisabledIndicator()
    }
}

@Composable
private fun EnabledIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 18.dp, height = 6.dp)
            .clip(shape = RoundedCornerShape(3.dp))
            .background(color = FColor.Primary, shape = RoundedCornerShape(3.dp))
    )
}

@Composable
private fun DisabledIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(color = FColor.Divider1, shape = CircleShape)
    )
}

sealed interface IndicatorType {
    object First : IndicatorType
    object Second : IndicatorType
    object Third : IndicatorType
}

@Preview(showBackground = true)
@Composable
private fun TitleIndicatorPreview() {
    FilmOneTheme {
        Column {
            SignUpIndicator(indicatorType = IndicatorType.First)

//            DisabledIndicator()
//            EnabledIndicator()
        }
    }
}