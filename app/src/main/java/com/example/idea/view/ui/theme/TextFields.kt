package com.example.idea.view.ui.theme

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneLineTextField(
    value: String,
    input: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = IdeaTheme.typography.bodyMedium,
    borderStroke: Dp = 6.dp,
    indicatorY: Float = 0f,
    isPassword: Boolean = false
) {
    TextField(
        value,
        onValueChange = {newValue -> input(newValue)},
        singleLine = true,
        textStyle = textStyle,
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .drawCustomIndicatorLine(
                indicatorBorder = BorderStroke(borderStroke, IdeaTheme.colors.primary),
                indicatorPadding = 20.dp,
                indicatorY = indicatorY
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
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiLineTextField(
    value: String,
    input: (String) -> Unit,
    modifier: Modifier,
    textStyle: TextStyle = IdeaTheme.typography.bodyMedium,
    borderStroke: Dp = 6.dp,
    indicatorY: Float = 0f,
    isPassword: Boolean = false,
    backgroundColor: Color = IdeaTheme.colors.onBackground,
    enabled: Boolean = true
) {
    TextField(
        value,
        onValueChange = {newValue -> input(newValue)},
        textStyle = textStyle,
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .drawCustomIndicatorLine(
                indicatorBorder = BorderStroke(borderStroke, IdeaTheme.colors.primary),
                indicatorPadding = 20.dp,
                indicatorY = indicatorY
            ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = backgroundColor,
            disabledTextColor = IdeaTheme.colors.secondary,
            focusedTextColor = IdeaTheme.colors.secondary,
            unfocusedTextColor = IdeaTheme.colors.secondary,
            cursorColor = IdeaTheme.colors.secondary,
            focusedIndicatorColor =  Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        enabled = enabled,
    )
}

fun Modifier.drawCustomIndicatorLine(
    indicatorBorder: BorderStroke,
    indicatorPadding : Dp = 0.dp,
    indicatorY : Float = 0f
): Modifier {

    val strokeWidthDp = indicatorBorder.width
    return drawWithContent {
        drawContent()
        if (strokeWidthDp == Dp.Hairline) return@drawWithContent
        val strokeWidth = strokeWidthDp.value * density
        val y = size.height - strokeWidth / 1.5f + indicatorY
        drawLine(
            indicatorBorder.brush,
            Offset((indicatorPadding).toPx(), y),
            Offset(size.width - indicatorPadding.toPx(), y),
            strokeWidth,
            StrokeCap.Round
        )
    }
}
