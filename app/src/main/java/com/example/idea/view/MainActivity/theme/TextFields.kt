package com.example.idea.view.MainActivity.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneLineTextField(
    value: String,
    input: (String) -> Unit,
    modifier: Modifier
) {
    TextField(
        value,
        onValueChange = {newValue -> input(newValue)},
        singleLine = true,
        textStyle = IdeaTheme.typography.bodyMedium,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .drawCustomIndicatorLine(
                indicatorBorder = BorderStroke(6.dp, IdeaTheme.colors.primary),
                indicatorPadding = 15.dp
            ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = IdeaTheme.colors.onBackground,
            disabledTextColor = IdeaTheme.colors.secondary,
            focusedTextColor = IdeaTheme.colors.secondary,
            unfocusedTextColor = IdeaTheme.colors.secondary,
            cursorColor = IdeaTheme.colors.secondary,
            focusedIndicatorColor =  Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

fun Modifier.drawCustomIndicatorLine(
    indicatorBorder: BorderStroke,
    indicatorPadding : Dp = 0.dp
): Modifier {

    val strokeWidthDp = indicatorBorder.width
    return drawWithContent {
        drawContent()
        if (strokeWidthDp == Dp.Hairline) return@drawWithContent
        val strokeWidth = strokeWidthDp.value * density
        val y = size.height - strokeWidth / 2
        drawLine(
            indicatorBorder.brush,
            Offset((indicatorPadding).toPx(), y),
            Offset(size.width - indicatorPadding.toPx(), y),
            strokeWidth,
            StrokeCap.Round
        )
    }
}
