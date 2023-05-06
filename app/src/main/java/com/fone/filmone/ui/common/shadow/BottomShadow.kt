package com.fone.filmone.ui.common.shadow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.fone.filmone.ui.theme.FColor

@Composable
fun ConstraintLayoutScope.BottomShadow(
    modifier: Modifier,
    shadowReference: ConstrainedLayoutReference,
    targetReference: ConstrainedLayoutReference,
    topColor: Color = FColor.ShadowColor,
    bottomColor: Color = FColor.Transparent
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        topColor,
                        bottomColor,
                    )
                ),
                shape = RectangleShape
            )
            .constrainAs(shadowReference) {
                top.linkTo(targetReference.bottom)
                start.linkTo(targetReference.start)
                end.linkTo(targetReference.end)
            }
    )
}