package com.example

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.data.ContentSeedData
import com.example.ui.theme.JelajahColorTokens
import com.example.ui.theme.JelajahDimens
import com.example.ui.theme.JelajahTypography
import com.example.ui.theme.tabularNums
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Quality Gate Unit Tests for Phases 0 - 6 (Design Tokens, Typography, Ergonomics, Seed Data, & Pricing).
 */
class ExampleUnitTest {

    @Test
    fun rupiahFormatter_matchesAgentsMdSpecification() {
        assertEquals("Rp 1.450.000", ContentSeedData.formatRupiah(1450000L))
        assertEquals("+Rp 350.000", ContentSeedData.formatRupiah(350000L, includePlusPrefix = true))
        assertEquals("Rp 1.800.000", ContentSeedData.formatRupiah(1450000L + 350000L))
    }

    @Test
    fun colorTokens_matchDesignMdAndTokensCss() {
        assertEquals(Color(0xFFF7F8FA), JelajahColorTokens.LightBgCanvas)
        assertEquals(Color(0xFFFFFFFF), JelajahColorTokens.LightBgSurface)
        assertEquals(Color(0xFFEEEEF2), JelajahColorTokens.LightBgElevated)
        assertEquals(Color(0xFF16191D), JelajahColorTokens.LightTextPrimary)
        assertEquals(Color(0xFF4E555E), JelajahColorTokens.LightTextSecondary)
        assertEquals(Color(0xFF717B87), JelajahColorTokens.LightTextMuted)
        assertEquals(Color(0xFF16191D), JelajahColorTokens.LightAccent)
        assertEquals(Color(0xFF2C3138), JelajahColorTokens.LightAccentHover)
        assertEquals(Color(0xFF23633E), JelajahColorTokens.LightSuccess)
        assertEquals(Color(0xFFEBF5EF), JelajahColorTokens.LightSuccessSurface)
        assertEquals(Color(0xFF82530E), JelajahColorTokens.LightWarning)
        assertEquals(Color(0xFFFDF5E9), JelajahColorTokens.LightWarningSurface)
    }

    @Test
    fun touchTargetsAndConcentricRadii_meetSpecification() {
        // Minimum touch target >= 44pt (48dp on Android)
        assertTrue(JelajahDimens.MinTouchTarget >= 44.dp)
        assertEquals(48.dp, JelajahDimens.ButtonPrimaryHeight)
        assertEquals(36.dp, JelajahDimens.CategoryPillHeight)
        assertEquals(64.dp, JelajahDimens.FloatingNavHeight)

        // Concentric radii rule: R_outer (20dp) = R_inner (14dp) + 6dp padding
        assertEquals(20.dp, JelajahDimens.RadiusCard)
        assertEquals(14.dp, JelajahDimens.RadiusThumb)
        assertEquals(28.dp, JelajahDimens.RadiusHero)
        assertEquals(32.dp, JelajahDimens.RadiusSheet)
    }

    @Test
    fun tabularNumsHelper_setsTnumFontFeature() {
        val styleWithTnum = JelajahTypography.heading2.tabularNums()
        assertEquals("tnum", styleWithTnum.fontFeatureSettings)
    }

    @Test
    fun seedDestinationsAndTourItinerary_matchContentMd() {
        val papuma = ContentSeedData.getDestinationById("dest-jember-001")
        assertEquals("Tanjung Papuma", papuma.name)
        assertEquals(5.0, papuma.rating, 0.001)
        assertEquals(184, papuma.reviewCount)

        val meruBetiri = ContentSeedData.getDestinationById("dest-jember-002")
        assertEquals("Taman Nasional Meru Betiri & Teluk Bandealit", meruBetiri.name)

        val ijen = ContentSeedData.getDestinationById("dest-jatim-003")
        assertEquals("Kawah Ijen & Blue Fire", ijen.name)

        val featuredTour = ContentSeedData.getTourById("tour-jbr-3d2n")
        assertEquals("Eksplor Jember", featuredTour.title)
        assertEquals("Kamis, 12 Nov - Sabtu, 14 Nov", featuredTour.departureDatesLabel)
        assertEquals(3, featuredTour.itineraryDays.size)
        assertEquals("Kedatangan & Teluk Love", featuredTour.itineraryDays[0].title)
        assertEquals("Sunrise Papuma & Rimba Bandealit", featuredTour.itineraryDays[1].title)
        assertEquals("Agrowisata & Pusat Suwar-Suwir", featuredTour.itineraryDays[2].title)
        assertEquals(1450000L, featuredTour.pricePerPerson)
        assertEquals(350000L, featuredTour.addonOption.pricePerPerson)
    }

    @Test
    fun ctaMicrocopy_usesSentenceCaseAndNoEmoji() {
        val ctas = listOf(
            ContentSeedData.CTA_VIEW_DETAIL,
            ContentSeedData.CTA_BOOK_NOW,
            ContentSeedData.CTA_BOOK_THIS_TOUR,
            ContentSeedData.CTA_READ_MORE,
            ContentSeedData.CTA_HIDE,
            ContentSeedData.CTA_SAVE_OFFLINE,
            ContentSeedData.CTA_OPEN_OFFLINE,
            ContentSeedData.CTA_EXPLORE_JEMBER
        )
        ctas.forEach { label ->
            assertFalse("CTA should not be ALL CAPS: $label", label == label.uppercase())
            assertFalse("CTA should not end with exclamation mark: $label", label.endsWith("!"))
        }
        assertEquals("Pesan tur ini", ContentSeedData.CTA_BOOK_THIS_TOUR)
        assertEquals(listOf("Jadwal tur", "Akomodasi", "Info penting"), ContentSeedData.TOUR_TABS)
    }

    @Test
    fun eastJavaLandscapeThemes_provideDistinctVibrantPalettes() {
        val presets = com.example.ui.theme.JelajahThemePreset.entries
        assertEquals(5, presets.size)
        assertEquals(Color(0xFF111111), com.example.ui.theme.JelajahThemePreset.MONO_EDITORIAL.colors.accent)
        assertEquals(Color(0xFF116149), com.example.ui.theme.JelajahThemePreset.MERU_EMERALD.colors.accent)
        assertEquals(Color(0xFF0C5D73), com.example.ui.theme.JelajahThemePreset.IJEN_CRATER.colors.accent)
        assertEquals(Color(0xFF9E3D16), com.example.ui.theme.JelajahThemePreset.BROMO_SUNRISE.colors.accent)
        assertTrue(com.example.ui.theme.JelajahThemePreset.MALAM_JATIM.colors.isDark)
    }
}
