package com.example.zinotes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.zinotes.R

val Calligraffitti = FontFamily(
    Font(R.font.calligraffitti_regular),
)

// Set of Material typography styles to start with
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Calligraffitti,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.5.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = Calligraffitti,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
    ),

    bodySmall = TextStyle(fontFamily = Calligraffitti,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp,
    ),

    displayLarge = TextStyle(
        fontFamily = Calligraffitti,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        letterSpacing = 0.5.sp

    ),
    displayMedium = TextStyle(
        fontFamily = Calligraffitti,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.5.sp

    ),
    labelSmall = TextStyle(
        fontFamily = Calligraffitti,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp

    ),

    labelMedium = TextStyle(
        fontFamily = Calligraffitti,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.5.sp

    ),

    titleLarge = TextStyle(
        fontFamily = Calligraffitti,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
    ),

    titleSmall = TextStyle(
        fontFamily = Calligraffitti,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )

)