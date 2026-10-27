package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

/**
 * Tipografi Jelajah Jatim - Keluarga Font: Onest (Google Fonts)
 * Sumber: design.md (Section 3)
 * Seluruh angka harga, tanggal, durasi, dan rating menggunakan aturan font-variant-numeric: tabular-nums ("tnum").
 */
val OnestFontFamily = FontFamily(
    Font(R.font.onest, FontWeight.Normal),
    Font(R.font.onest, FontWeight.Medium),
    Font(R.font.onest, FontWeight.SemiBold)
)

/**
 * Mengaktifkan tabular-nums ("tnum") untuk elemen numerik (harga rupiah, tanggal, rating, durasi).
 */
fun TextStyle.tabularNums(): TextStyle = this.copy(fontFeatureSettings = "tnum")

@Immutable
data class JelajahTypographyScale(
    // Display / Hero: 24px | 600 (Semi-bold) | Line Height 30px | Tracking -0.02em (-0.48sp)
    val displayHero: TextStyle = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = (-0.48).sp
    ),
    // Heading 1 (H1): 20px | 600 (Semi-bold) | Line Height 26px | Tracking -0.01em (-0.20sp)
    val heading1: TextStyle = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = (-0.20).sp
    ),
    // Heading 2 (H2): 16px | 600 (Semi-bold) | Line Height 22px | Tracking 0
    val heading2: TextStyle = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
    // Body Default: 14px | 400 (Regular) | Line Height 21px | Tracking 0
    val bodyDefault: TextStyle = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp
    ),
    // Body Medium: 14px | 500 (Medium) | Line Height 21px | Tracking 0
    val bodyMedium: TextStyle = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp
    ),
    // Small / Label: 12px | 500 (Medium) | Line Height 16px | Tracking +0.01em (+0.12sp)
    val smallLabel: TextStyle = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.12.sp
    ),
    // Caption / Meta: 11px | 400 (Regular) | Line Height 14px | Tracking +0.02em (+0.22sp)
    val captionMeta: TextStyle = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.22.sp
    )
)

val JelajahTypography = JelajahTypographyScale()

val MaterialOnestTypography = Typography(
    displayLarge = JelajahTypography.displayHero,
    headlineLarge = JelajahTypography.displayHero,
    headlineMedium = JelajahTypography.heading1,
    titleLarge = JelajahTypography.heading1,
    titleMedium = JelajahTypography.heading2,
    bodyLarge = JelajahTypography.bodyMedium,
    bodyMedium = JelajahTypography.bodyDefault,
    labelLarge = JelajahTypography.bodyMedium,
    labelMedium = JelajahTypography.smallLabel,
    labelSmall = JelajahTypography.captionMeta
)
