package com.example.notify.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.notify.R

// Set of Material typography styles to start with
val fontFamily = FontFamily(
    androidx.compose.ui.text.font.Font(R.font.blinker_bold, FontWeight.W300),
    androidx.compose.ui.text.font.Font(R.font.blinker_semibold, FontWeight.W300),
    androidx.compose.ui.text.font.Font(R.font.blinker_regular, FontWeight.W200),
    androidx.compose.ui.text.font.Font(R.font.blinker_light, FontWeight.W100)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W200,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W100,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)