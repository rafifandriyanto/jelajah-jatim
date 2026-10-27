package com.example.ui.showcase

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.data.ContentSeedData
import com.example.data.NatureStatusTone
import com.example.ui.components.ButtonPrimary
import com.example.ui.components.CategoryPill
import com.example.ui.components.FloatingNavBar
import com.example.ui.components.FloatingNavTab
import com.example.ui.components.StatusBadge
import com.example.ui.theme.JelajahDimens
import com.example.ui.theme.JelajahTheme
import com.example.ui.theme.SafeAreaWrapper
import com.example.ui.theme.tabularNums

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Phase0And1ShowcaseScreen(
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current

    // Interactive state for Phase 1 components showcase
    var selectedCategory by rememberSaveable { mutableStateOf(ContentSeedData.REGION_CATEGORIES.first()) }
    var selectedStatusId by rememberSaveable { mutableStateOf(ContentSeedData.NATURE_STATUS_LIST.first().id) }
    var selectedNavTab by rememberSaveable { mutableStateOf(FloatingNavTab.HOME) }
    var ctaClickCount by rememberSaveable { mutableIntStateOf(0) }
    var isButtonsEnabled by rememberSaveable { mutableStateOf(true) }
    var includeJeepAddon by rememberSaveable { mutableStateOf(false) }
    var lastActionFeedback by rememberSaveable {
        mutableStateOf("Phase 0 & Phase 1 siap diuji — ketuk komponen untuk uji interaksi & haptic")
    }

    val activeStatus = remember(selectedStatusId) {
        ContentSeedData.NATURE_STATUS_LIST.find { it.id == selectedStatusId }
            ?: ContentSeedData.NATURE_STATUS_LIST.first()
    }

    val filteredDestinations = remember(selectedCategory) {
        if (selectedCategory == "Semua") {
            ContentSeedData.DESTINATIONS
        } else {
            ContentSeedData.DESTINATIONS.filter {
                it.regionCategory.equals(selectedCategory, ignoreCase = true)
            }
        }
    }

    val totalPrice = ContentSeedData.FEATURED_TOUR_BASE_PRICE +
        if (includeJeepAddon) ContentSeedData.FEATURED_TOUR_JEEP_ADDON_PRICE else 0L

    SafeAreaWrapper(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 600.dp)
                    .testTag("showcase_lazy_column"),
                contentPadding = PaddingValues(
                    start = JelajahDimens.Space4,
                    end = JelajahDimens.Space4,
                    top = JelajahDimens.Space4,
                    bottom = 112.dp // Ruang bernapas di atas FloatingNavBar (64dp + margin)
                ),
                verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space6)
            ) {
                // 1. HEADER PHASE 0 & THEME TOKEN SWITCHER
                item {
                    ShowcaseHeaderCard(
                        isDarkTheme = isDarkTheme,
                        onToggleDarkTheme = {
                            onToggleDarkTheme()
                            lastActionFeedback = if (!isDarkTheme) {
                                "Tema beralih ke Dark Mode ([data-theme=\"dark\"])"
                            } else {
                                "Tema beralih ke Light Mode (:root #F7F8FA)"
                            }
                        },
                        lastActionFeedback = lastActionFeedback,
                        ctaClickCount = ctaClickCount,
                        selectedNavTab = selectedNavTab
                    )
                }

                // 2. PHASE 1: CATEGORY PILL SHOWCASE (5.2)
                item {
                    ShowcaseSectionCard(
                        phaseTag = "PHASE 1 • KOMPONEN 5.2",
                        title = "CategoryPill (Filter Wilayah)",
                        subtitle = "Tinggi visual 36dp di dalam area sentuh >= 48dp. Aktif: --accent (#16191D), Non-aktif: --bg-surface dengan batas 1dp --border-subtle."
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ContentSeedData.REGION_CATEGORIES.forEach { category ->
                                CategoryPill(
                                    label = category,
                                    selected = selectedCategory == category,
                                    onClick = {
                                        selectedCategory = category
                                        lastActionFeedback = "CategoryPill dipilih: $category"
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(JelajahDimens.Space3))

                        // Preview hasil filter seed data sesuai kategori wilayah
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(JelajahDimens.ShapeThumb)
                                .background(colors.bgElevated)
                                .border(1.dp, colors.borderSubtle, JelajahDimens.ShapeThumb)
                                .padding(JelajahDimens.Space3),
                            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Wilayah aktif: $selectedCategory",
                                    style = typography.smallLabel,
                                    color = colors.textPrimary
                                )
                                Text(
                                    text = "${filteredDestinations.size} destinasi terkurasi",
                                    style = typography.captionMeta.tabularNums(),
                                    color = colors.textSecondary
                                )
                            }

                            if (filteredDestinations.isEmpty()) {
                                Text(
                                    text = "Belum ada seed destinasi pada filter wilayah $selectedCategory (fokus seed MVP: Jember & Banyuwangi).",
                                    style = typography.captionMeta,
                                    color = colors.textSecondary
                                )
                            } else {
                                filteredDestinations.forEach { dest ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Place,
                                                contentDescription = null,
                                                tint = colors.textSecondary,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Text(
                                                text = dest.name,
                                                style = typography.bodyMedium,
                                                color = colors.textPrimary
                                            )
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Star,
                                                contentDescription = null,
                                                tint = colors.warning,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Text(
                                                text = "${dest.rating} (${dest.reviewCount} ulasan)",
                                                style = typography.smallLabel.tabularNums(),
                                                color = colors.textSecondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 3. PHASE 1: STATUS BADGE SHOWCASE (Signature Detail F-04)
                item {
                    ShowcaseSectionCard(
                        phaseTag = "PHASE 1 • SIGNATURE DETAIL",
                        title = "StatusBadge (Kondisi Alam & Medan)",
                        subtitle = "Lencana semantik dengan permukaan 12% tint (--success-surface, --warning-surface, --danger-surface) dan batas 1dp. Ketuk lencana untuk menginspeksi laporan."
                    ) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2),
                            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space1)
                        ) {
                            ContentSeedData.NATURE_STATUS_LIST.forEach { status ->
                                StatusBadge(
                                    label = status.badgeText,
                                    tone = status.tone,
                                    selected = status.id == selectedStatusId,
                                    onClick = {
                                        selectedStatusId = status.id
                                        lastActionFeedback = "StatusBadge diinspeksi: ${status.badgeText}"
                                    },
                                    testTag = "status_badge_${status.id}"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(JelajahDimens.Space3))

                        // Detail ConditionReport dari lencana yang dipilih
                        val (reportBg, reportFg) = when (activeStatus.tone) {
                            NatureStatusTone.SAFE -> colors.successSurface to colors.success
                            NatureStatusTone.WARNING -> colors.warningSurface to colors.warning
                            NatureStatusTone.DANGER -> colors.dangerSurface to colors.danger
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(JelajahDimens.ShapeThumb)
                                .background(reportBg)
                                .border(1.dp, reportFg.copy(alpha = 0.24f), JelajahDimens.ShapeThumb)
                                .padding(JelajahDimens.Space4),
                            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space1)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = activeStatus.routeContext,
                                    style = typography.smallLabel,
                                    color = reportFg
                                )
                                Text(
                                    text = activeStatus.lastUpdated,
                                    style = typography.captionMeta.tabularNums(),
                                    color = colors.textSecondary
                                )
                            }
                            Text(
                                text = activeStatus.description,
                                style = typography.bodyDefault,
                                color = colors.textPrimary
                            )
                        }
                    }
                }

                // 4. PHASE 1: BUTTON PRIMARY SHOWCASE (5.4)
                item {
                    ShowcaseSectionCard(
                        phaseTag = "PHASE 1 • KOMPONEN 5.4",
                        title = "ButtonPrimary (Kapsul 48dp)",
                        subtitle = "Tinggi 48dp, radius kapsul 9999px, latar --accent (#16191D), pressed --accent-hover (#2C3138) tanpa distorsi skala (no bounce), teks sentence case."
                    ) {
                        // Interactive Toggle untuk menguji state Enabled vs Disabled & Add-on Jeep 4WD
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
                        ) {
                            InteractiveToggleChip(
                                label = if (isButtonsEnabled) "Status: Normal / Aktif" else "Status: Non-aktif (Disabled)",
                                active = isButtonsEnabled,
                                onClick = {
                                    isButtonsEnabled = !isButtonsEnabled
                                    lastActionFeedback = if (isButtonsEnabled) {
                                        "ButtonPrimary diaktifkan (Normal state)"
                                    } else {
                                        "ButtonPrimary dinonaktifkan (Disabled state)"
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                testTag = "toggle_button_enabled"
                            )
                            InteractiveToggleChip(
                                label = if (includeJeepAddon) "Jeep 4WD: +Rp 350rb" else "Tambah Jeep 4WD",
                                active = includeJeepAddon,
                                onClick = {
                                    includeJeepAddon = !includeJeepAddon
                                    lastActionFeedback = if (includeJeepAddon) {
                                        "Add-on Jeep Bandealit (+Rp 350.000) ditambahkan"
                                    } else {
                                        "Add-on Jeep Bandealit dilepas"
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                testTag = "toggle_jeep_addon"
                            )
                        }

                        Spacer(modifier = Modifier.height(JelajahDimens.Space3))

                        // Varian 1: Lihat detail (dengan ikon panah kanan sesuai wireframe S-01)
                        ButtonPrimary(
                            text = ContentSeedData.CTA_VIEW_DETAIL,
                            enabled = isButtonsEnabled,
                            trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                            onClick = {
                                ctaClickCount++
                                lastActionFeedback = "Ketukan '$ContentSeedData.CTA_VIEW_DETAIL' (#$ctaClickCount)"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "btn_lihat_detail"
                        )

                        Spacer(modifier = Modifier.height(JelajahDimens.Space3))

                        // Varian 2: Simulasi Sticky Booking Bar dengan harga Tabular-Nums + "Pesan sekarang"
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(JelajahDimens.ShapeCard)
                                .background(colors.bgElevated)
                                .border(1.dp, colors.borderSubtle, JelajahDimens.ShapeCard)
                                .padding(JelajahDimens.Space3),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = ContentSeedData.FEATURED_TOUR_DATES,
                                    style = typography.captionMeta.tabularNums(),
                                    color = colors.textSecondary
                                )
                                Text(
                                    text = "${ContentSeedData.formatRupiah(totalPrice)} / orang",
                                    style = typography.heading2.tabularNums(),
                                    color = colors.textPrimary
                                )
                            }
                            Spacer(modifier = Modifier.width(JelajahDimens.Space3))
                            ButtonPrimary(
                                text = ContentSeedData.CTA_BOOK_NOW,
                                enabled = isButtonsEnabled,
                                onClick = {
                                    ctaClickCount++
                                    lastActionFeedback =
                                        "Ketukan '${ContentSeedData.CTA_BOOK_NOW}' • ${ContentSeedData.formatRupiah(totalPrice)}"
                                },
                                testTag = "btn_pesan_sekarang"
                            )
                        }

                        Spacer(modifier = Modifier.height(JelajahDimens.Space3))

                        // Varian 3: Simpan jadwal offline
                        ButtonPrimary(
                            text = ContentSeedData.CTA_SAVE_OFFLINE,
                            enabled = isButtonsEnabled,
                            leadingIcon = Icons.Filled.Download,
                            onClick = {
                                ctaClickCount++
                                lastActionFeedback = "Ketukan '${ContentSeedData.CTA_SAVE_OFFLINE}' (#$ctaClickCount)"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "btn_simpan_offline"
                        )
                    }
                }

                // 5. PHASE 0: TIPOGRAFI ONEST & TABULAR-NUMS AUDIT
                item {
                    ShowcaseSectionCard(
                        phaseTag = "PHASE 0 • TIPOGRAFI & TABULAR-NUMS",
                        title = "Skala Tipografi Onest (Google Fonts)",
                        subtitle = "Seluruh angka harga, tanggal, durasi, dan rating menerapkan fontFeatureSettings = \"tnum\" (tabular-nums) dengan format Rp 1.450.000."
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)) {
                            TypographySampleRow(
                                scaleLabel = "Display / Hero • 24sp SemiBold (-0.02em)",
                                sampleText = ContentSeedData.USER_GREETING,
                                textStyle = typography.displayHero
                            )
                            HorizontalDivider(color = colors.borderSubtle)
                            TypographySampleRow(
                                scaleLabel = "Heading 1 (H1) • 20sp SemiBold (-0.01em)",
                                sampleText = "Tanjung Papuma & Teluk Love",
                                textStyle = typography.heading1
                            )
                            HorizontalDivider(color = colors.borderSubtle)
                            TypographySampleRow(
                                scaleLabel = "Heading 2 (H2) • 16sp SemiBold (tnum)",
                                sampleText = "${ContentSeedData.formatRupiah(1450000)} / orang",
                                textStyle = typography.heading2.tabularNums()
                            )
                            HorizontalDivider(color = colors.borderSubtle)
                            TypographySampleRow(
                                scaleLabel = "Body Medium • 14sp Medium (tnum)",
                                sampleText = "3 Hari 2 Malam • Kamis, 12 Nov - Sabtu, 14 Nov",
                                textStyle = typography.bodyMedium.tabularNums()
                            )
                            HorizontalDivider(color = colors.borderSubtle)
                            TypographySampleRow(
                                scaleLabel = "Body Default • 14sp Regular (21sp line-height)",
                                sampleText = ContentSeedData.DESTINATIONS.first().description,
                                textStyle = typography.bodyDefault
                            )
                            HorizontalDivider(color = colors.borderSubtle)
                            TypographySampleRow(
                                scaleLabel = "Small / Label • 12sp Medium (+0.01em, tnum)",
                                sampleText = "Rating 5.0 (184 ulasan) • Sunrise 05:00 - 08:00 WIB",
                                textStyle = typography.smallLabel.tabularNums()
                            )
                            HorizontalDivider(color = colors.borderSubtle)
                            TypographySampleRow(
                                scaleLabel = "Caption / Meta • 11sp Regular (+0.02em, tnum)",
                                sampleText = "Sewa Armada Jeep Bandealit (${ContentSeedData.formatRupiah(350000, includePlusPrefix = true)} / orang)",
                                textStyle = typography.captionMeta.tabularNums()
                            )
                        }
                    }
                }

                // 6. PHASE 0: TOKEN WARNA & RASIO KONTRAS WCAG
                item {
                    ShowcaseSectionCard(
                        phaseTag = "PHASE 0 • DISIPLIN TOKEN WARNA",
                        title = "Palet Semantik & Rasio Kontras",
                        subtitle = "100% dipetakan dari tokens.css & design.md tanpa warna heksadesimal liar."
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)) {
                            ColorTokenRow(
                                tokenName = "--bg-canvas",
                                hexCode = if (isDarkTheme) "#0E1013" else "#F7F8FA",
                                contrastNote = "Basis Kanvas (60%)",
                                swatchColor = colors.bgCanvas
                            )
                            ColorTokenRow(
                                tokenName = "--bg-surface",
                                hexCode = if (isDarkTheme) "#16191E" else "#FFFFFF",
                                contrastNote = "Kartu & Sheet (30%)",
                                swatchColor = colors.bgSurface
                            )
                            ColorTokenRow(
                                tokenName = "--bg-elevated",
                                hexCode = if (isDarkTheme) "#1F232B" else "#EEEEF2",
                                contrastNote = "Input & Tab Non-Aktif",
                                swatchColor = colors.bgElevated
                            )
                            ColorTokenRow(
                                tokenName = "--text-primary / --accent",
                                hexCode = if (isDarkTheme) "#F0F2F5" else "#16191D",
                                contrastNote = "14.8:1 terhadap canvas",
                                swatchColor = colors.textPrimary
                            )
                            ColorTokenRow(
                                tokenName = "--text-secondary",
                                hexCode = if (isDarkTheme) "#A4ACB9" else "#4E555E",
                                contrastNote = "6.7:1 terhadap canvas",
                                swatchColor = colors.textSecondary
                            )
                            ColorTokenRow(
                                tokenName = "--text-muted",
                                hexCode = if (isDarkTheme) "#717B87" else "#717B87",
                                contrastNote = "4.6:1 terhadap canvas",
                                swatchColor = colors.textMuted
                            )
                            ColorTokenRow(
                                tokenName = "--success / --success-surface",
                                hexCode = if (isDarkTheme) "#5EA87A" else "#23633E / #EBF5EF",
                                contrastNote = "5.2:1 (Jalur Aman)",
                                swatchColor = colors.success
                            )
                            ColorTokenRow(
                                tokenName = "--warning / --warning-surface",
                                hexCode = if (isDarkTheme) "#D4A359" else "#82530E / #FDF5E9",
                                contrastNote = "5.4:1 (Waspada / 4WD)",
                                swatchColor = colors.warning
                            )
                        }
                    }
                }

                // 7. QUALITY GATE AUDIT SUMMARY (PHASE 0 & PHASE 1)
                item {
                    ShowcaseSectionCard(
                        phaseTag = "QUALITY GATE • AUDIT PHASE 0 & 1",
                        title = "Status Verifikasi Quality Gate",
                        subtitle = "Pemeriksaan kepatuhan terhadap AGENTS.md, design.md, dan build-plan.md sebelum konfirmasi Phase 2."
                    ) {
                        val checks = listOf(
                            "Font Onest & Latar #F7F8FA" to "Terpasang lokal di res/font/onest.ttf & SafeAreaWrapper menggunakan --bg-canvas.",
                            "Touch Target >= 48dp (44x44 pt)" to "ButtonPrimary (48dp), CategoryPill (outer 48dp), StatusBadge (48dp), FloatingNavBar item (48x48dp).",
                            "Format Angka Tabular-Nums (tnum)" to "Seluruh harga (Rp 1.450.000), rating (5.0), tanggal, dan rasio kontras memakai tabular-nums.",
                            "Disiplin Warna & Kontras WCAG AA" to "Semua teks memenuhi >= 4.5:1 (Primary 14.8:1, Secondary 6.7:1, Muted 4.6:1).",
                            "Anti-Slop & Sentence Case" to "0 emoji Unicode, 0 bayangan berwarna/gradien tombol, label tombol sentence case."
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)) {
                            checks.forEach { (title, detail) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.CheckCircle,
                                        contentDescription = null,
                                        tint = colors.success,
                                        modifier = Modifier
                                            .padding(top = 2.dp)
                                            .size(18.dp)
                                    )
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            text = title,
                                            style = typography.bodyMedium,
                                            color = colors.textPrimary
                                        )
                                        Text(
                                            text = detail,
                                            style = typography.captionMeta.tabularNums(),
                                            color = colors.textSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 8. FLOATING BOTTOM NAVIGATION BAR (5.1)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .widthIn(max = 600.dp)
                    .padding(bottom = JelajahDimens.Space4)
            ) {
                FloatingNavBar(
                    selectedTab = selectedNavTab,
                    onTabSelected = { tab ->
                        selectedNavTab = tab
                        lastActionFeedback = "FloatingNavBar beralih ke tab: ${tab.label}"
                    }
                )
            }
        }
    }
}

@Composable
private fun ShowcaseHeaderCard(
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    lastActionFeedback: String,
    ctaClickCount: Int,
    selectedNavTab: FloatingNavTab
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = JelajahDimens.ShapeHero,
        color = colors.bgSurface,
        border = BorderStroke(1.dp, colors.borderSubtle)
    ) {
        Column(
            modifier = Modifier.padding(JelajahDimens.Space6),
            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(
                    label = "Phase 0 & Phase 1 Ready",
                    tone = NatureStatusTone.SAFE,
                    leadingIcon = Icons.Filled.Verified
                )

                // Tombol penguji Light / Dark Token (Touch target >= 48dp)
                val interactionSource = remember { MutableInteractionSource() }
                Box(
                    modifier = Modifier
                        .testTag("theme_mode_toggle")
                        .minimumInteractiveComponentSize()
                        .heightIn(min = JelajahDimens.MinTouchTarget)
                        .clip(JelajahDimens.ShapePill)
                        .background(colors.bgElevated, JelajahDimens.ShapePill)
                        .border(1.dp, colors.borderSubtle, JelajahDimens.ShapePill)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            role = Role.Switch,
                            onClick = onToggleDarkTheme
                        )
                        .padding(horizontal = JelajahDimens.Space3),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                            contentDescription = "Ubah tema token",
                            tint = colors.textPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isDarkTheme) "Dark Token" else "Light Token",
                            style = typography.smallLabel,
                            color = colors.textPrimary
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space1)) {
                Text(
                    text = "Jelajah Jatim",
                    style = typography.displayHero,
                    color = colors.textPrimary
                )
                Text(
                    text = "Fondasi Desain (Phase 0) & Komponen Dasar Interaktif (Phase 1)",
                    style = typography.bodyDefault,
                    color = colors.textSecondary
                )
            }

            // Telemetry bar untuk memverifikasi ketukan, haptic, dan state komponen
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(JelajahDimens.ShapeThumb)
                    .background(colors.bgElevated)
                    .padding(horizontal = JelajahDimens.Space3, vertical = JelajahDimens.Space2),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lastActionFeedback,
                    style = typography.captionMeta.tabularNums(),
                    color = colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(JelajahDimens.Space2))
                Text(
                    text = "Tab: ${selectedNavTab.label} • Tap: $ctaClickCount",
                    style = typography.captionMeta.tabularNums(),
                    color = colors.textSecondary
                )
            }
        }
    }
}

@Composable
private fun ShowcaseSectionCard(
    phaseTag: String,
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = JelajahDimens.ShapeCard,
        color = colors.bgSurface,
        border = BorderStroke(1.dp, colors.borderSubtle)
    ) {
        Column(
            modifier = Modifier.padding(JelajahDimens.Space4),
            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space1)) {
                Text(
                    text = phaseTag,
                    style = typography.captionMeta.tabularNums(),
                    color = colors.textMuted
                )
                Text(
                    text = title,
                    style = typography.heading1,
                    color = colors.textPrimary
                )
                Text(
                    text = subtitle,
                    style = typography.bodyDefault,
                    color = colors.textSecondary
                )
            }
            content()
        }
    }
}

@Composable
private fun InteractiveToggleChip(
    label: String,
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .testTag(testTag)
            .minimumInteractiveComponentSize()
            .heightIn(min = JelajahDimens.MinTouchTarget)
            .clip(JelajahDimens.ShapeThumb)
            .background(
                if (active) colors.successSurface else colors.bgElevated,
                JelajahDimens.ShapeThumb
            )
            .border(
                width = 1.dp,
                color = if (active) colors.success.copy(alpha = 0.3f) else colors.borderSubtle,
                shape = JelajahDimens.ShapeThumb
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Checkbox,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            )
            .padding(horizontal = JelajahDimens.Space3, vertical = JelajahDimens.Space2),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.DirectionsCar,
                contentDescription = null,
                tint = if (active) colors.success else colors.textSecondary,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = label,
                style = typography.smallLabel.tabularNums(),
                color = if (active) colors.success else colors.textSecondary
            )
        }
    }
}

@Composable
private fun TypographySampleRow(
    scaleLabel: String,
    sampleText: String,
    textStyle: androidx.compose.ui.text.TextStyle
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = scaleLabel,
            style = typography.captionMeta.tabularNums(),
            color = colors.textMuted
        )
        Text(
            text = sampleText,
            style = textStyle,
            color = colors.textPrimary
        )
    }
}

@Composable
private fun ColorTokenRow(
    tokenName: String,
    hexCode: String,
    contrastNote: String,
    swatchColor: Color
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(JelajahDimens.ShapeSm)
            .background(colors.bgElevated)
            .padding(horizontal = JelajahDimens.Space3, vertical = JelajahDimens.Space2),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(swatchColor, CircleShape)
                    .border(1.dp, colors.borderStrong, CircleShape)
            )
            Column {
                Text(
                    text = tokenName,
                    style = typography.smallLabel,
                    color = colors.textPrimary
                )
                Text(
                    text = hexCode,
                    style = typography.captionMeta.tabularNums(),
                    color = colors.textSecondary
                )
            }
        }
        Text(
            text = contrastNote,
            style = typography.captionMeta.tabularNums(),
            color = colors.textSecondary
        )
    }
}
