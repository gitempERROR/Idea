package com.example.idea.view.MainActivity.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.material3.Typography

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    onBackground = OnBackground
)

val LocalColors = staticCompositionLocalOf { DarkColorScheme }
val LocalTypography = staticCompositionLocalOf { Typography }

@Composable
fun IdeaTheme(
    typography: Typography = IdeaTheme.typography,
    content: @Composable () -> Unit
) {
    val colors = DarkColorScheme

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        content = content
    )
}

object IdeaTheme {
    val colors : ColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalColors.current
    val typography : Typography
        @Composable @ReadOnlyComposable
        get() = LocalTypography.current
}

