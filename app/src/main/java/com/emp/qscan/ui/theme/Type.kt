package com.emp.qscan.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val Base = Typography()

val Typography = Typography(
    displaySmall = Base.displaySmall.copy(fontWeight = FontWeight.SemiBold, letterSpacing = (-0.5).sp),
    headlineSmall = Base.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
    labelMedium = Base.labelMedium.copy(letterSpacing = 1.2.sp),
)

/** Label-printer look for IDs and shelf locations. */
val CodeStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 20.sp, lineHeight = 26.sp, fontWeight = FontWeight.Medium)
