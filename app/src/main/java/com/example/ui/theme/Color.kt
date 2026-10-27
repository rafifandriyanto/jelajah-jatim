package com.example.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Token Warna & Rasio Kontras - Jelajah Jatim Design System
 * Sumber: design.md (Section 2), tokens.css, & Minimalist iOS Screen 3 Specification.
 * Seluruh konstanta warna heksadesimal wajib didefinisikan secara terpusat di objek ini.
 */
object JelajahColorTokens {
    // 1. Light Mode / Default Editorial (:root) — Dipertahankan untuk kompatibilitas unit test
    val LightBgCanvas = Color(0xFFF7F8FA)       // --bg-canvas: #F7F8FA
    val LightBgSurface = Color(0xFFFFFFFF)      // --bg-surface: #FFFFFF
    val LightBgElevated = Color(0xFFEEEEF2)     // --bg-elevated: #EEEEF2

    val LightTextPrimary = Color(0xFF16191D)    // --text-primary: #16191D (14.8:1 vs canvas)
    val LightTextSecondary = Color(0xFF4E555E)  // --text-secondary: #4E555E (6.7:1 vs canvas)
    val LightTextMuted = Color(0xFF717B87)      // --text-muted: #717B87 (4.6:1 vs canvas)

    val LightBorderSubtle = Color(22, 25, 29, 20) // --border-subtle: rgba(22, 25, 29, 0.08)
    val LightBorderStrong = Color(22, 25, 29, 41) // --border-strong: rgba(22, 25, 29, 0.16)
    val LightBorderFocus = Color(0xFF16191D)      // --border-focus: #16191D

    val LightAccent = Color(0xFF16191D)         // --accent: #16191D
    val LightAccentHover = Color(0xFF2C3138)    // --accent-hover: #2C3138
    val LightAccentFg = Color(0xFFFFFFFF)       // --accent-fg: #FFFFFF

    val LightSuccess = Color(0xFF23633E)        // --success: #23633E (5.2:1 vs success-surface)
    val LightSuccessSurface = Color(0xFFEBF5EF) // --success-surface: #EBF5EF
    val LightWarning = Color(0xFF82530E)        // --warning: #82530E (5.4:1 vs warning-surface)
    val LightWarningSurface = Color(0xFFFDF5E9) // --warning-surface: #FDF5E9
    val LightDanger = Color(0xFF962E25)         // --danger: #962E25
    val LightDangerSurface = Color(0xFFFBECEB)  // --danger-surface: #FBECEB

    // 2. Dark Mode Override ([data-theme="dark"] - Malam Blue Fire)
    val DarkBgCanvas = Color(0xFF0E1013)        // --bg-canvas: #0E1013
    val DarkBgSurface = Color(0xFF16191E)       // --bg-surface: #16191E
    val DarkBgElevated = Color(0xFF1F232B)      // --bg-elevated: #1F232B

    val DarkTextPrimary = Color(0xFFF0F2F5)     // --text-primary: #F0F2F5
    val DarkTextSecondary = Color(0xFFA4ACB9)   // --text-secondary: #A4ACB9
    val DarkTextMuted = Color(0xFF747D8C)       // --text-muted: #747D8C

    val DarkBorderSubtle = Color(240, 242, 245, 26) // --border-subtle: rgba(240, 242, 245, 0.1)
    val DarkBorderStrong = Color(240, 242, 245, 51) // --border-strong: rgba(240, 242, 245, 0.2)
    val DarkBorderFocus = Color(0xFFF0F2F5)         // --border-focus: #F0F2F5

    val DarkAccent = Color(0xFFF0F2F5)          // --accent: #F0F2F5
    val DarkAccentHover = Color(0xFFD8DCE3)     // --accent-hover: #D8DCE3
    val DarkAccentFg = Color(0xFF0E1013)        // --accent-fg: #0E1013

    val DarkSuccess = Color(0xFF5EA87A)               // --success: #5EA87A
    val DarkSuccessSurface = Color(94, 168, 122, 31)  // --success-surface: rgba(94, 168, 122, 0.12)
    val DarkWarning = Color(0xFFD4A359)               // --warning: #D4A359
    val DarkWarningSurface = Color(212, 163, 89, 31)  // --warning-surface: rgba(212, 163, 89, 0.12)
    val DarkDanger = Color(0xFFDF766D)                // --danger: #DF766D
    val DarkDangerSurface = Color(223, 118, 109, 31)  // --danger-surface: rgba(223, 118, 109, 0.12)

    // 3. Screen 3 (Itinerary & Tour Schedule) Modern iOS Minimalist Light & Dark Tokens
    val S03LightCanvas = Color(0xFFFFFFFF)          // Canvas #FFFFFF
    val S03LightSurfaceCard = Color(0xFFFFFFFF)     // Surface Card #FFFFFF
    val S03LightBorderCard = Color(0xFFEAEAEA)      // 1px border tipis #EAEAEA
    val S03LightBorderSurface = Color(0xFFF0F0F2)   // Surface border #F0F0F2
    val S03LightActiveBg = Color(0xFF111111)        // CTA & Active Tab #111111
    val S03LightActiveText = Color(0xFFFFFFFF)      // Teks #FFFFFF
    val S03LightInactiveBg = Color(0xFFF4F4F5)      // Inactive Tab & Circle Buttons #F4F4F5
    val S03LightInactiveText = Color(0xFF71717A)    // Teks #71717A
    val S03LightTextPrimary = Color(0xFF18181B)     // Text Primary #18181B
    val S03LightTextSecondary = Color(0xFF71717A)   // Text Secondary #71717A

    val S03DarkCanvas = Color(0xFF0C0D0E)           // Canvas #0C0D0E
    val S03DarkSurfaceCard = Color(0xFF15171A)      // Surface Card #15171A
    val S03DarkBorderCard = Color(255, 255, 255, 20) // rgba(255,255,255,0.08)
    val S03DarkActiveBg = Color(0xFFF4F4F5)         // CTA & Active Tab #F4F4F5
    val S03DarkActiveText = Color(0xFF0C0D0E)       // Teks #0C0D0E
    val S03DarkInactiveBg = Color(0xFF1F2227)       // Inactive Tab & Circle Buttons #1F2227
    val S03DarkInactiveText = Color(0xFF8E95A2)     // Teks #8E95A2
    val S03DarkTextPrimary = Color(0xFFF4F4F5)      // Text Primary #F4F4F5
    val S03DarkTextSecondary = Color(0xFF8E95A2)    // Text Secondary #8E95A2

    // Blurhash 'L6PZfSi_.AyE_3t7t7R**0o#DgR4' Placeholder Palette
    val BlurhashLightPrimary = Color(0xFFD8D0C5)
    val BlurhashLightSecondary = Color(0xFFEAE4DC)
    val BlurhashLightTertiary = Color(0xFFC9BFB2)
    val BlurhashDarkPrimary = Color(0xFF23262C)
    val BlurhashDarkSecondary = Color(0xFF2D3139)
    val BlurhashDarkTertiary = Color(0xFF1B1E23)

    // 3b. Screen 1 (Hero Stacked Carousel) TripGlide Glassmorphism & 3-Stop Contrast Gradient Tokens
    val HeroGradientStop0 = Color(0, 0, 0, 13)              // rgba(0, 0, 0, 0.05) at 0.0f
    val HeroGradientStop1 = Color(0, 0, 0, 38)              // rgba(0, 0, 0, 0.15) at 0.45f
    val HeroGradientStop2 = Color(10, 12, 16, 224)          // rgba(10, 12, 16, 0.88) at 1.0f

    val HeroBadgeGlassBg = Color(255, 255, 255, 224)        // rgba(255, 255, 255, 0.88)
    val HeroBadgeSafeDot = Color(0xFF16A34A)                // #16A34A
    val HeroBadgeWarningDot = Color(0xFFD97706)             // #D97706
    val HeroBadgeText = Color(0xFF111111)                   // #111111

    val HeroWishlistGlassBg = Color(255, 255, 255, 56)      // rgba(255, 255, 255, 0.22)
    val HeroWishlistGlassBorder = Color(255, 255, 255, 89)  // rgba(255, 255, 255, 0.35)

    val HeroSubtitleWhite82 = Color(255, 255, 255, 209)     // rgba(255, 255, 255, 0.82)
    val HeroTitleWhite = Color(0xFFFFFFFF)                  // #FFFFFF
    val HeroRatingPillBg = Color(255, 255, 255, 31)         // rgba(255, 255, 255, 0.12)
    val HeroRatingPillBorder = Color(255, 255, 255, 89)     // rgba(255, 255, 255, 0.35)

    val HeroCapsuleDarkGlassBg = Color(24, 24, 27, 173)     // rgba(24, 24, 27, 0.68)
    val HeroCapsuleDarkGlassBorder = Color(255, 255, 255, 41) // rgba(255, 255, 255, 0.16)
    val HeroCapsuleCircleBg = Color(0xFFFFFFFF)             // #FFFFFF
    val HeroCapsuleChevron = Color(0xFF111111)              // #111111

    val HeroDotActiveLight = Color(0xFF111111)              // #111111
    val HeroDotInactiveLight = Color(0xFFD4D4D8)            // #D4D4D8
    val HeroDotInactiveDark = Color(0xFF3F3F46)             // #3F3F46
    val HeroSwipeHintText = Color(0xFF71717A)               // #71717A

    // 4. Palet Lanskap Jawa Timur Opsional
    val MeruBgCanvas = Color(0xFFF2F7F4)
    val MeruBgSurface = Color(0xFFFFFFFF)
    val MeruBgElevated = Color(0xFFE3EFE9)
    val MeruTextPrimary = Color(0xFF10241C)
    val MeruTextSecondary = Color(0xFF3B564B)
    val MeruTextMuted = Color(0xFF607B70)
    val MeruBorderSubtle = Color(16, 36, 28, 22)
    val MeruBorderStrong = Color(16, 36, 28, 44)
    val MeruAccent = Color(0xFF116149)
    val MeruAccentHover = Color(0xFF187A5D)

    val IjenBgCanvas = Color(0xFFF1F7FA)
    val IjenBgSurface = Color(0xFFFFFFFF)
    val IjenBgElevated = Color(0xFFE1EEF4)
    val IjenTextPrimary = Color(0xFF102129)
    val IjenTextSecondary = Color(0xFF3E5661)
    val IjenTextMuted = Color(0xFF627B87)
    val IjenBorderSubtle = Color(16, 33, 41, 22)
    val IjenBorderStrong = Color(16, 33, 41, 44)
    val IjenAccent = Color(0xFF0C5D73)
    val IjenAccentHover = Color(0xFF137690)

    val BromoBgCanvas = Color(0xFFFAF5F0)
    val BromoBgSurface = Color(0xFFFFFFFF)
    val BromoBgElevated = Color(0xFFF2E7DC)
    val BromoTextPrimary = Color(0xFF221612)
    val BromoTextSecondary = Color(0xFF5C483F)
    val BromoTextMuted = Color(0xFF826C62)
    val BromoBorderSubtle = Color(34, 22, 18, 22)
    val BromoBorderStrong = Color(34, 22, 18, 44)
    val BromoAccent = Color(0xFF9E3D16)
    val BromoAccentHover = Color(0xFFB84A1E)
}

/**
 * Token khusus Screen 3 (Itinerary & Tour Schedule) sesuai spesifikasi Light/Dark Zero-Lag Switch
 */
@Immutable
data class S03ThemeTokens(
    val canvas: Color,
    val surfaceCard: Color,
    val cardBorder: Color,
    val surfaceBorder: Color,
    val ctaAndActiveTabBg: Color,
    val ctaAndActiveTabText: Color,
    val inactiveTabBg: Color,
    val inactiveTabText: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val isDark: Boolean
)

val LightS03ThemeTokens = S03ThemeTokens(
    canvas = JelajahColorTokens.S03LightCanvas,
    surfaceCard = JelajahColorTokens.S03LightSurfaceCard,
    cardBorder = JelajahColorTokens.S03LightBorderCard,
    surfaceBorder = JelajahColorTokens.S03LightBorderSurface,
    ctaAndActiveTabBg = JelajahColorTokens.S03LightActiveBg,
    ctaAndActiveTabText = JelajahColorTokens.S03LightActiveText,
    inactiveTabBg = JelajahColorTokens.S03LightInactiveBg,
    inactiveTabText = JelajahColorTokens.S03LightInactiveText,
    textPrimary = JelajahColorTokens.S03LightTextPrimary,
    textSecondary = JelajahColorTokens.S03LightTextSecondary,
    isDark = false
)

val DarkS03ThemeTokens = S03ThemeTokens(
    canvas = JelajahColorTokens.S03DarkCanvas,
    surfaceCard = JelajahColorTokens.S03DarkSurfaceCard,
    cardBorder = JelajahColorTokens.S03DarkBorderCard,
    surfaceBorder = JelajahColorTokens.S03DarkBorderCard,
    ctaAndActiveTabBg = JelajahColorTokens.S03DarkActiveBg,
    ctaAndActiveTabText = JelajahColorTokens.S03DarkActiveText,
    inactiveTabBg = JelajahColorTokens.S03DarkInactiveBg,
    inactiveTabText = JelajahColorTokens.S03DarkInactiveText,
    textPrimary = JelajahColorTokens.S03DarkTextPrimary,
    textSecondary = JelajahColorTokens.S03DarkTextSecondary,
    isDark = true
)

@Immutable
data class JelajahColors(
    val bgCanvas: Color,
    val bgSurface: Color,
    val bgElevated: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val borderSubtle: Color,
    val borderStrong: Color,
    val borderFocus: Color,
    val accent: Color,
    val accentHover: Color,
    val accentFg: Color,
    val success: Color,
    val successSurface: Color,
    val warning: Color,
    val warningSurface: Color,
    val danger: Color,
    val dangerSurface: Color,
    val isDark: Boolean
)

val LightJelajahColors = JelajahColors(
    bgCanvas = JelajahColorTokens.S03LightCanvas,
    bgSurface = JelajahColorTokens.S03LightSurfaceCard,
    bgElevated = JelajahColorTokens.S03LightInactiveBg,
    textPrimary = JelajahColorTokens.S03LightTextPrimary,
    textSecondary = JelajahColorTokens.S03LightTextSecondary,
    textMuted = JelajahColorTokens.S03LightInactiveText,
    borderSubtle = JelajahColorTokens.S03LightBorderCard,
    borderStrong = JelajahColorTokens.LightBorderStrong,
    borderFocus = JelajahColorTokens.S03LightActiveBg,
    accent = JelajahColorTokens.S03LightActiveBg,
    accentHover = JelajahColorTokens.LightAccentHover,
    accentFg = JelajahColorTokens.S03LightActiveText,
    success = JelajahColorTokens.LightSuccess,
    successSurface = JelajahColorTokens.LightSuccessSurface,
    warning = JelajahColorTokens.LightWarning,
    warningSurface = JelajahColorTokens.LightWarningSurface,
    danger = JelajahColorTokens.LightDanger,
    dangerSurface = JelajahColorTokens.LightDangerSurface,
    isDark = false
)

val DarkJelajahColors = JelajahColors(
    bgCanvas = JelajahColorTokens.S03DarkCanvas,
    bgSurface = JelajahColorTokens.S03DarkSurfaceCard,
    bgElevated = JelajahColorTokens.S03DarkInactiveBg,
    textPrimary = JelajahColorTokens.S03DarkTextPrimary,
    textSecondary = JelajahColorTokens.S03DarkTextSecondary,
    textMuted = JelajahColorTokens.S03DarkInactiveText,
    borderSubtle = JelajahColorTokens.S03DarkBorderCard,
    borderStrong = JelajahColorTokens.DarkBorderStrong,
    borderFocus = JelajahColorTokens.S03DarkActiveBg,
    accent = JelajahColorTokens.S03DarkActiveBg,
    accentHover = JelajahColorTokens.DarkAccentHover,
    accentFg = JelajahColorTokens.S03DarkActiveText,
    success = JelajahColorTokens.DarkSuccess,
    successSurface = JelajahColorTokens.DarkSuccessSurface,
    warning = JelajahColorTokens.DarkWarning,
    warningSurface = JelajahColorTokens.DarkWarningSurface,
    danger = JelajahColorTokens.DarkDanger,
    dangerSurface = JelajahColorTokens.DarkDangerSurface,
    isDark = true
)

val MeruEmeraldJelajahColors = JelajahColors(
    bgCanvas = JelajahColorTokens.MeruBgCanvas,
    bgSurface = JelajahColorTokens.MeruBgSurface,
    bgElevated = JelajahColorTokens.MeruBgElevated,
    textPrimary = JelajahColorTokens.MeruTextPrimary,
    textSecondary = JelajahColorTokens.MeruTextSecondary,
    textMuted = JelajahColorTokens.MeruTextMuted,
    borderSubtle = JelajahColorTokens.MeruBorderSubtle,
    borderStrong = JelajahColorTokens.MeruBorderStrong,
    borderFocus = JelajahColorTokens.MeruAccent,
    accent = JelajahColorTokens.MeruAccent,
    accentHover = JelajahColorTokens.MeruAccentHover,
    accentFg = JelajahColorTokens.LightAccentFg,
    success = JelajahColorTokens.LightSuccess,
    successSurface = JelajahColorTokens.LightSuccessSurface,
    warning = JelajahColorTokens.LightWarning,
    warningSurface = JelajahColorTokens.LightWarningSurface,
    danger = JelajahColorTokens.LightDanger,
    dangerSurface = JelajahColorTokens.LightDangerSurface,
    isDark = false
)

val IjenTurquoiseJelajahColors = JelajahColors(
    bgCanvas = JelajahColorTokens.IjenBgCanvas,
    bgSurface = JelajahColorTokens.IjenBgSurface,
    bgElevated = JelajahColorTokens.IjenBgElevated,
    textPrimary = JelajahColorTokens.IjenTextPrimary,
    textSecondary = JelajahColorTokens.IjenTextSecondary,
    textMuted = JelajahColorTokens.IjenTextMuted,
    borderSubtle = JelajahColorTokens.IjenBorderSubtle,
    borderStrong = JelajahColorTokens.IjenBorderStrong,
    borderFocus = JelajahColorTokens.IjenAccent,
    accent = JelajahColorTokens.IjenAccent,
    accentHover = JelajahColorTokens.IjenAccentHover,
    accentFg = JelajahColorTokens.LightAccentFg,
    success = JelajahColorTokens.LightSuccess,
    successSurface = JelajahColorTokens.LightSuccessSurface,
    warning = JelajahColorTokens.LightWarning,
    warningSurface = JelajahColorTokens.LightWarningSurface,
    danger = JelajahColorTokens.LightDanger,
    dangerSurface = JelajahColorTokens.LightDangerSurface,
    isDark = false
)

val BromoSunriseJelajahColors = JelajahColors(
    bgCanvas = JelajahColorTokens.BromoBgCanvas,
    bgSurface = JelajahColorTokens.BromoBgSurface,
    bgElevated = JelajahColorTokens.BromoBgElevated,
    textPrimary = JelajahColorTokens.BromoTextPrimary,
    textSecondary = JelajahColorTokens.BromoTextSecondary,
    textMuted = JelajahColorTokens.BromoTextMuted,
    borderSubtle = JelajahColorTokens.BromoBorderSubtle,
    borderStrong = JelajahColorTokens.BromoBorderStrong,
    borderFocus = JelajahColorTokens.BromoAccent,
    accent = JelajahColorTokens.BromoAccent,
    accentHover = JelajahColorTokens.BromoAccentHover,
    accentFg = JelajahColorTokens.LightAccentFg,
    success = JelajahColorTokens.LightSuccess,
    successSurface = JelajahColorTokens.LightSuccessSurface,
    warning = JelajahColorTokens.LightWarning,
    warningSurface = JelajahColorTokens.LightWarningSurface,
    danger = JelajahColorTokens.LightDanger,
    dangerSurface = JelajahColorTokens.LightDangerSurface,
    isDark = false
)

/**
 * Pilihan Tema Aplikasi (Default: MONO_EDITORIAL Light #FFFFFF & #111111)
 */
enum class JelajahThemePreset(
    val id: String,
    val shortName: String,
    val fullName: String,
    val landscapeInspiration: String,
    val colors: JelajahColors
) {
    MONO_EDITORIAL(
        id = "mono_editorial",
        shortName = "Light",
        fullName = "Minimalis Terang (#FFFFFF & #111111)",
        landscapeInspiration = "Kanvas putih bersih #FFFFFF dengan aksen hitam pekat #111111 bergaya iOS modern",
        colors = LightJelajahColors
    ),
    MALAM_JATIM(
        id = "malam_jatim",
        shortName = "Dark",
        fullName = "Minimalis Gelap (#0C0D0E & #15171A)",
        landscapeInspiration = "Kanvas malam #0C0D0E dengan kartu #15171A dan aksen terang #F4F4F5",
        colors = DarkJelajahColors
    ),
    IJEN_CRATER(
        id = "ijen_crater",
        shortName = "Kawah Ijen",
        fullName = "Toska Kawah Ijen & Baluran",
        landscapeInspiration = "Biru toska mineral danau kawah vulkanik Ijen & pesisir Banyuwangi",
        colors = IjenTurquoiseJelajahColors
    ),
    BROMO_SUNRISE(
        id = "bromo_sunrise",
        shortName = "Senja Bromo",
        fullName = "Senja Kaldera Tengger Bromo",
        landscapeInspiration = "Terakota hangat matahari terbit Gunung Bromo & lautan pasir",
        colors = BromoSunriseJelajahColors
    ),
    MERU_EMERALD(
        id = "meru_emerald",
        shortName = "Hutan Meru",
        fullName = "Hutan Meru & Pesisir Papuma",
        landscapeInspiration = "Hijau zamrud tropis Taman Nasional Meru Betiri & Tanjung Papuma",
        colors = MeruEmeraldJelajahColors
    )
}
