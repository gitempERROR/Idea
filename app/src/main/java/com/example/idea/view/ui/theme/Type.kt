package com.example.idea.view.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography: Typography
    get() = Typography(
        bodyLarge = TextStyle(
            fontFamily = Futura,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = Futura,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 17.sp,
            letterSpacing = 0.5.sp
        ),
        labelLarge = TextStyle(
            fontFamily = Candice,
            fontWeight = FontWeight.Normal,
            fontSize = 66.sp,
            lineHeight = 88.sp,
            letterSpacing = 0.5.sp
        ),
        labelMedium = TextStyle(
            fontFamily = Candice,
            fontWeight = FontWeight.Normal,
            fontSize = 31.sp,
            lineHeight = 43.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = Futura,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.5.sp
        ),
        displayMedium = TextStyle(
            fontFamily = Candice,
            fontWeight = FontWeight.Normal,
            fontSize = 26.sp,
            lineHeight = 29.sp,
            letterSpacing = 0.5.sp
        )
    )