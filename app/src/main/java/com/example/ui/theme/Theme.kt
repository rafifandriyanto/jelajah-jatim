package com.example.ui.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Sistem Jarak (Grid Base 4px), Radius Konsentris, dan Motion Token
 * Sumber: design.md (Section 4) & tokens.css
 */
object JelajahDimens {
    // Spacing Scale (4px base)
    val Space1 = 4.dp
    val Space2 = 8.dp
    val Space3 = 12.dp
    val Space4 = 16.dp
    val Space5 = 20.dp
    val Space6 = 24.dp
    val Space8 = 32.dp
    val Space12 = 48.dp
    val Space16 = 64.dp

    // Concentric Radii Scale (R_outer = R_inner + padding)
    val RadiusSm = 8.dp
    val RadiusThumb = 14.dp
    val RadiusCard = 20.dp
    val RadiusHero = 28.dp
    val RadiusSheet = 32.dp

    val ShapeSm = RoundedCornerShape(RadiusSm)
    val ShapeThumb = RoundedCornerShape(RadiusThumb)
    val ShapeCard = RoundedCornerShape(RadiusCard)
    val ShapeHero = RoundedCornerShape(RadiusHero)
    val ShapeSheet = RoundedCornerShape(topStart = RadiusSheet, topEnd = RadiusSheet)
    val ShapePill = CircleShape // radius: 9999px (bentuk pil kapsul penuh)

    // Component ergonomic heights & touch targets
    val MinTouchTarget = 48.dp // >= 44x44 pt mobile standard
    val ButtonPrimaryHeight = 48.dp
    val CategoryPillHeight = 36.dp
    val FloatingNavHeight = 64.dp
}

object JelajahMotion {
    const val DurFastMs = 150
    const val DurBaseMs = 200
    val EaseStandard = CubicBezierEasing(0.2f, 0f, 0f, 1f)
}

enum class ThemeMode(val id: String) {
    LIGHT("light"),
    DARK("dark")
}

val LocalJelajahColors = staticCompositionLocalOf { LightJelajahColors }
val LocalS03ThemeTokens = staticCompositionLocalOf { LightS03ThemeTokens }
val LocalJelajahThemePreset = staticCompositionLocalOf { JelajahThemePreset.MONO_EDITORIAL }
val LocalJelajahTypography = staticCompositionLocalOf { JelajahTypography }

object JelajahTheme {
    val colors: JelajahColors
        @Composable
        @ReadOnlyComposable
        get() = LocalJelajahColors.current

    val s03: S03ThemeTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalS03ThemeTokens.current

    val preset: JelajahThemePreset
        @Composable
        @ReadOnlyComposable
        get() = LocalJelajahThemePreset.current

    val typography: JelajahTypographyScale
        @Composable
        @ReadOnlyComposable
        get() = LocalJelajahTypography.current
}

private fun JelajahColors.toMaterial3Scheme(): ColorScheme = if (isDark) {
    darkColorScheme(
        background = bgCanvas,
        surface = bgSurface,
        surfaceVariant = bgElevated,
        primary = accent,
        onPrimary = accentFg,
        onBackground = textPrimary,
        onSurface = textPrimary,
        onSurfaceVariant = textSecondary,
        outline = borderSubtle,
        error = danger,
        onError = accentFg
    )
} else {
    lightColorScheme(
        background = bgCanvas,
        surface = bgSurface,
        surfaceVariant = bgElevated,
        primary = accent,
        onPrimary = accentFg,
        onBackground = textPrimary,
        onSurface = textPrimary,
        onSurfaceVariant = textSecondary,
        outline = borderSubtle,
        error = danger,
        onError = accentFg
    )
}

@Composable
fun JelajahJatimTheme(
    darkTheme: Boolean = false,
    themePreset: JelajahThemePreset? = null,
    content: @Composable () -> Unit
) {
    val resolvedPreset = when {
        themePreset != null -> themePreset
        darkTheme -> JelajahThemePreset.MALAM_JATIM
        else -> JelajahThemePreset.MONO_EDITORIAL
    }
    val jelajahColors = resolvedPreset.colors
    val s03Tokens = if (jelajahColors.isDark) DarkS03ThemeTokens else LightS03ThemeTokens
    val m3Scheme = jelajahColors.toMaterial3Scheme()

    CompositionLocalProvider(
        LocalJelajahColors provides jelajahColors,
        LocalS03ThemeTokens provides s03Tokens,
        LocalJelajahThemePreset provides resolvedPreset,
        LocalJelajahTypography provides JelajahTypography
    ) {
        MaterialTheme(
            colorScheme = m3Scheme,
            typography = MaterialOnestTypography,
            content = content
        )
    }
}

/**
 * Konfigurasi Global SafeAreaWrapper (Phase 0)
 * Mematuhi safe area notch/dynamic island di bagian atas dan navigation bar / home indicator di bagian bawah,
 * dengan latar belakang kanvas token (--bg-canvas).
 */
@Composable
fun SafeAreaWrapper(
    modifier: Modifier = Modifier,
    applySafeDrawingInsets: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val colors = JelajahTheme.colors
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.bgCanvas)
            .then(
                if (applySafeDrawingInsets) {
                    Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
                } else {
                    Modifier
                }
            ),
        content = content
    )
}
