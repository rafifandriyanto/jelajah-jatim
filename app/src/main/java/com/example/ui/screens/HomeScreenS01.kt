package com.example.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.JelajahJatimTheme
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.data.AccessRequirement
import com.example.data.ContentSeedData
import com.example.data.DestinationSeed
import com.example.data.NatureStatusTone
import com.example.ui.components.ButtonPrimary
import com.example.ui.components.CategoryPill
import com.example.ui.components.EmptyState
import com.example.ui.components.LazyOptimizedImage
import com.example.ui.components.OfflineBanner
import com.example.ui.components.SkeletonLoader
import com.example.ui.components.UserAvatarImage
import com.example.ui.theme.JelajahColorTokens
import com.example.ui.theme.JelajahDimens
import com.example.ui.theme.JelajahTheme
import com.example.ui.theme.tabularNums
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.math.absoluteValue

private val HERO_FEATURED_PRIORITY = listOf(
    "dest-jember-001", // 1. Tanjung Papuma (Golden Hour / Sunny Coast)
    "dest-jatim-003",  // 2. Kawah Ijen & Blue Fire (Turquoise Crater)
    "dest-bromo-004",  // 3. Kaldera Bromo & Bukit Kingkong (Sunrise Caldera)
    "dest-jember-002"  // 4. Taman Nasional Meru Betiri & Teluk Bandealit (Tropical Bay)
)

/**
 * Layar Beranda (Screen S-01: Hero Stacked & Peeking Carousel 1:1 Gaya TripGlide)
 * Urutan vertikal bersih tanpa clutter bawah:
 * Header Profil -> Search Bar -> Pill Kategori Wilayah -> Stacked Peeking Hero Carousel -> Indikator Geser -> Floating Bottom Navigation Bar.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreenS01(
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedAccessFilter: AccessRequirement?,
    onSelectAccessFilter: (AccessRequirement?) -> Unit,
    savedDestinationIds: Set<String>,
    onToggleSaveDestination: (String) -> Unit,
    isOfflineMode: Boolean,
    onToggleOfflineMode: () -> Unit,
    isSimulatingSkeleton: Boolean,
    savedOfflineTourCount: Int,
    lastSyncedTimestamp: String,
    onOpenOfflineVault: () -> Unit,
    onNavigateToDestinationDetail: (String) -> Unit,
    onNavigateToTourItinerary: (String) -> Unit,
    onOpenProfileClick: () -> Unit = {},
    userName: String = "Vanessa",
    selectedThemePreset: com.example.ui.theme.JelajahThemePreset = com.example.ui.theme.JelajahThemePreset.MONO_EDITORIAL,
    onSelectThemePreset: (com.example.ui.theme.JelajahThemePreset) -> Unit = {},
    offlineCachedDestinations: List<DestinationSeed> = emptyList(),
    cachedImageTotalKb: String = "",
    modifier: Modifier = Modifier
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current
    val density = LocalDensity.current
    var showFilterModal by rememberSaveable { mutableStateOf(false) }
    var showThemeBar by rememberSaveable { mutableStateOf(false) }

    val activeSourceDestinations = remember(isOfflineMode, offlineCachedDestinations) {
        if (isOfflineMode && offlineCachedDestinations.isNotEmpty()) {
            offlineCachedDestinations
        } else {
            ContentSeedData.DESTINATIONS
        }
    }

    val filteredDestinations = remember(
        activeSourceDestinations,
        selectedCategory,
        searchQuery,
        selectedAccessFilter
    ) {
        val matching = activeSourceDestinations.filter { dest ->
            val matchesRegion = selectedCategory == "Semua" ||
                dest.regionCategory.equals(selectedCategory, ignoreCase = true)
            val matchesAccess = selectedAccessFilter == null ||
                dest.accessRequirement == selectedAccessFilter
            val q = searchQuery.trim()
            val matchesSearch = q.isEmpty() ||
                dest.name.contains(q, ignoreCase = true) ||
                dest.fullLocation.contains(q, ignoreCase = true) ||
                dest.description.contains(q, ignoreCase = true) ||
                dest.terrainType.label.contains(q, ignoreCase = true)
            matchesRegion && matchesAccess && matchesSearch
        }
        matching.sortedWith(
            compareBy<DestinationSeed> { dest ->
                val priorityIdx = HERO_FEATURED_PRIORITY.indexOf(dest.id)
                if (priorityIdx >= 0) priorityIdx else Int.MAX_VALUE
            }.thenByDescending { it.isFeaturedHero }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .widthIn(max = 600.dp)
            .testTag("home_screen_s01_list"),
        contentPadding = PaddingValues(
            top = JelajahDimens.Space4,
            bottom = 112.dp
        ),
        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space4)
    ) {
        // 1. Header Profil
        item {
            Box(modifier = Modifier.padding(horizontal = JelajahDimens.Space5)) {
                HeaderProfile(
                    userName = userName,
                    isOfflineMode = isOfflineMode,
                    selectedThemePreset = selectedThemePreset,
                    isThemeBarExpanded = showThemeBar,
                    onToggleThemeBar = { showThemeBar = !showThemeBar },
                    onOpenProfileClick = onOpenProfileClick
                )
            }
        }

        if (showThemeBar) {
            item {
                Box(modifier = Modifier.padding(horizontal = JelajahDimens.Space5)) {
                    EastJavaThemeQuickBar(
                        selectedThemePreset = selectedThemePreset,
                        onSelectThemePreset = onSelectThemePreset
                    )
                }
            }
        }

        if (isOfflineMode) {
            item {
                Box(modifier = Modifier.padding(horizontal = JelajahDimens.Space5)) {
                    OfflineBanner(
                        savedOfflineCount = savedOfflineTourCount,
                        lastSyncedTimestamp = lastSyncedTimestamp,
                        onOpenOfflineVault = onOpenOfflineVault,
                        cachedDestinationsCount = offlineCachedDestinations.size.coerceAtLeast(5),
                        cachedImageTotalKb = cachedImageTotalKb
                    )
                }
            }
        }

        // 2. Search Bar
        item {
            Box(modifier = Modifier.padding(horizontal = JelajahDimens.Space5)) {
                SearchInputBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    hasActiveFilter = selectedCategory != "Semua" || selectedAccessFilter != null,
                    onOpenFilterModal = { showFilterModal = true }
                )
            }
        }

        // 3. Judul Bagian "Pilih destinasi berikutnya" + Pill Kategori Wilayah
        item {
            Column(
                modifier = Modifier.padding(horizontal = JelajahDimens.Space5),
                verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = ContentSeedData.SECTION_NEXT_DESTINATION,
                        style = typography.heading1,
                        color = colors.textPrimary
                    )

                    val offlineInteraction = remember { MutableInteractionSource() }
                    Box(
                        modifier = Modifier
                            .testTag("toggle_offline_mode_chip")
                            .minimumInteractiveComponentSize()
                            .heightIn(min = JelajahDimens.MinTouchTarget)
                            .clickable(
                                interactionSource = offlineInteraction,
                                indication = null,
                                role = Role.Switch,
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onToggleOfflineMode()
                                }
                            ),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(JelajahDimens.ShapePill)
                                .background(
                                    if (isOfflineMode) colors.warningSurface else colors.bgSurface,
                                    JelajahDimens.ShapePill
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isOfflineMode) {
                                        colors.warning.copy(alpha = 0.35f)
                                    } else {
                                        colors.borderSubtle
                                    },
                                    shape = JelajahDimens.ShapePill
                                )
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = if (isOfflineMode) Icons.Filled.CloudOff else Icons.Filled.CloudDone,
                                contentDescription = "Simulasi Mode Luring",
                                tint = if (isOfflineMode) colors.warning else colors.success,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = if (isOfflineMode) "Luring" else "Online",
                                style = typography.captionMeta,
                                color = if (isOfflineMode) colors.warning else colors.textSecondary
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .testTag("category_scroll_row"),
                    horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ContentSeedData.REGION_CATEGORIES.forEach { category ->
                        CategoryPill(
                            label = category,
                            selected = selectedCategory == category,
                            onClick = { onSelectCategory(category) }
                        )
                    }
                }
            }
        }

        // 4. Stacked Peeking Hero Carousel + 5. Indikator Geser & Pagination Dots
        if (isSimulatingSkeleton) {
            item {
                Box(modifier = Modifier.padding(horizontal = JelajahDimens.Space5)) {
                    SkeletonLoader()
                }
            }
        } else if (filteredDestinations.isEmpty()) {
            item {
                Box(modifier = Modifier.padding(horizontal = JelajahDimens.Space5)) {
                    EmptyState(
                        searchQuery = searchQuery,
                        popularDestinations = ContentSeedData.DESTINATIONS,
                        onExploreJemberClick = {
                            onSearchQueryChange("")
                            onSelectAccessFilter(null)
                            onSelectCategory("Jember")
                        },
                        onDestinationClick = onNavigateToDestinationDetail
                    )
                }
            }
        } else {
            item {
                val pagerState = rememberPagerState(
                    initialPage = 0,
                    pageCount = { filteredDestinations.size }
                )

                // Reset ke halaman pertama saat kategori/pencarian berubah
                LaunchedEffect(selectedCategory, searchQuery, selectedAccessFilter) {
                    if (pagerState.currentPage != 0 && filteredDestinations.isNotEmpty()) {
                        pagerState.scrollToPage(0)
                    }
                }

                // Haptics.selectionAsync() saat pindah kartu (onSnapToItem)
                var lastSettledPage by remember { mutableIntStateOf(0) }
                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.settledPage }
                        .distinctUntilChanged()
                        .collect { settled ->
                            if (settled != lastSettledPage) {
                                lastSettledPage = settled
                                view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            }
                        }
                }

                // Initial swipe nudge: animasi spring halus bergeser 24dp lalu kembali saat layar pertama dibuka
                var hasPlayedSwipeNudge by rememberSaveable { mutableStateOf(false) }
                val swipeNudgeOffsetPx = remember { Animatable(0f) }
                LaunchedEffect(Unit) {
                    if (!hasPlayedSwipeNudge && filteredDestinations.size > 1) {
                        hasPlayedSwipeNudge = true
                        val targetNudgePx = with(density) { -24.dp.toPx() }
                        swipeNudgeOffsetPx.animateTo(
                            targetValue = targetNudgePx,
                            animationSpec = tween(durationMillis = 280)
                        )
                        swipeNudgeOffsetPx.animateTo(
                            targetValue = 0f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Stacked & Peeking Hero Carousel:
                    // Lebar kartu = lebar layar dikurangi 72dp (contentPadding horizontal = 36.dp),
                    // tinggi = 430.dp, borderRadius = 32.dp, kartu kiri & kanan mengintip jelas.
                    HorizontalPager(
                        state = pagerState,
                        key = { page -> filteredDestinations[page].id },
                        contentPadding = PaddingValues(horizontal = 36.dp),
                        pageSpacing = 14.dp,
                        beyondViewportPageCount = 1,
                        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(446.dp)
                            .testTag("hero_stacked_carousel")
                    ) { page ->
                        val destination = filteredDestinations[page]
                        val isCurrentPage = page == pagerState.currentPage
                        // Continuous signed offset: 0 = tengah, < 0 = kartu kiri, > 0 = kartu kanan
                        val rawOffset =
                            (page - pagerState.currentPage) - pagerState.currentPageOffsetFraction
                        val clampedOffset = rawOffset.coerceIn(-1f, 1f)
                        val absOffset = clampedOffset.absoluteValue

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(430.dp)
                                .graphicsLayer {
                                    // Scale: 1.0f (tengah) -> 0.92f (kiri & kanan)
                                    val cardScale = lerp(1.0f, 0.92f, absOffset)
                                    scaleX = cardScale
                                    scaleY = cardScale
                                    // RotateZ: 0deg (tengah) -> -2.5deg (kiri) / +2.5deg (kanan)
                                    rotationZ = clampedOffset * 2.5f
                                    // Opacity: 1.0f (tengah) -> 0.85f (kiri & kanan)
                                    alpha = lerp(1.0f, 0.85f, absOffset)
                                    // TranslateY: 0dp (tengah) -> 12dp (kiri & kanan)
                                    translationY = lerp(0f, 12.dp.toPx(), absOffset)
                                    // Initial swipe nudge offset
                                    translationX = swipeNudgeOffsetPx.value
                                }
                        ) {
                            FeaturedDestinationHeroCard(
                                destination = destination,
                                isSaved = savedDestinationIds.contains(destination.id),
                                isPrimaryTestTarget = isCurrentPage,
                                parallaxFraction = clampedOffset,
                                onToggleSave = {
                                    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onToggleSaveDestination(destination.id)
                                },
                                onViewDetailClick = {
                                    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onNavigateToDestinationDetail(destination.id)
                                }
                            )
                        }
                    }

                    // 5. Indikator Geser & Pagination Dots (Tepat 14dp di bawah karusel)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = JelajahDimens.Space5)
                            .testTag("hero_pagination_indicator_row"),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Sisi Kiri: Animated pagination dots (titik aktif memanjang halus 24dp #111111, sisanya 6dp #D4D4D8)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val dotCount = filteredDestinations.size.coerceAtMost(6)
                            val activeDotIndex = pagerState.currentPage % dotCount
                            val activeDotColor = if (colors.isDark) {
                                colors.accent
                            } else {
                                JelajahColorTokens.HeroDotActiveLight
                            }
                            val inactiveDotColor = if (colors.isDark) {
                                JelajahColorTokens.HeroDotInactiveDark
                            } else {
                                JelajahColorTokens.HeroDotInactiveLight
                            }

                            for (i in 0 until dotCount) {
                                val distance = (
                                    (activeDotIndex - i) + pagerState.currentPageOffsetFraction
                                ).absoluteValue.coerceIn(0f, 1f)
                                val dotWidthDp = lerp(24f, 6f, distance).dp
                                val isActiveDot = distance < 0.5f

                                Box(
                                    modifier = Modifier
                                        .height(6.dp)
                                        .width(dotWidthDp)
                                        .clip(JelajahDimens.ShapePill)
                                        .background(
                                            if (isActiveDot) activeDotColor else inactiveDotColor,
                                            JelajahDimens.ShapePill
                                        )
                                )
                            }
                        }

                        // Sisi Kanan: Teks petunjuk halus "Geser untuk eksplor (1/14)" (12sp, #71717A, tabular-nums)
                        Text(
                            text = "Geser untuk eksplor (${pagerState.currentPage + 1}/${filteredDestinations.size})",
                            style = typography.captionMeta.tabularNums().copy(
                                fontSize = 12.sp
                            ),
                            color = if (colors.isDark) {
                                colors.textSecondary
                            } else {
                                JelajahColorTokens.HeroSwipeHintText
                            },
                            modifier = Modifier.testTag("hero_swipe_hint_text")
                        )
                    }
                }
            }
        }
    }

    // Modal Filter Wilayah & Akses Medan Kendaraan
    if (showFilterModal) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showFilterModal = false },
            sheetState = sheetState,
            shape = JelajahDimens.ShapeSheet,
            containerColor = colors.bgSurface,
            contentColor = colors.textPrimary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = JelajahDimens.Space6,
                        end = JelajahDimens.Space6,
                        bottom = JelajahDimens.Space8
                    ),
                verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space4)
            ) {
                Text(
                    text = "Filter Wilayah & Akses Medan",
                    style = typography.heading1,
                    color = colors.textPrimary
                )

                Text(
                    text = "Pilih kawasan wisata Jawa Timur",
                    style = typography.smallLabel,
                    color = colors.textSecondary
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2),
                    verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space1)
                ) {
                    ContentSeedData.REGION_CATEGORIES.forEach { category ->
                        CategoryPill(
                            label = category,
                            selected = selectedCategory == category,
                            onClick = { onSelectCategory(category) },
                            testTag = "modal_category_pill_$category"
                        )
                    }
                }

                HorizontalDivider(color = colors.borderSubtle)

                Text(
                    text = "Persyaratan kendaraan & medan jalan",
                    style = typography.smallLabel,
                    color = colors.textSecondary
                )

                Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)) {
                    val options = listOf(
                        null to "Semua jenis akses kendaraan",
                        AccessRequirement.STANDARD to AccessRequirement.STANDARD.badgeLabel,
                        AccessRequirement.FOUR_WD_ONLY to AccessRequirement.FOUR_WD_ONLY.badgeLabel
                    )
                    options.forEach { (req, label) ->
                        val isSelected = selectedAccessFilter == req
                        CategoryPill(
                            label = label,
                            selected = isSelected,
                            onClick = { onSelectAccessFilter(req) },
                            testTag = "filter_access_${req?.code ?: "all"}"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(JelajahDimens.Space2))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                ) {
                    CategoryPill(
                        label = ContentSeedData.CTA_RESET_FILTER,
                        selected = false,
                        onClick = {
                            onSelectCategory("Semua")
                            onSelectAccessFilter(null)
                            onSearchQueryChange("")
                            showFilterModal = false
                        },
                        modifier = Modifier.weight(1f),
                        testTag = "btn_reset_filter"
                    )
                    ButtonPrimary(
                        text = ContentSeedData.CTA_APPLY_FILTER,
                        onClick = { showFilterModal = false },
                        modifier = Modifier.weight(1f),
                        testTag = "btn_apply_filter"
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderProfile(
    userName: String,
    isOfflineMode: Boolean,
    selectedThemePreset: com.example.ui.theme.JelajahThemePreset,
    isThemeBarExpanded: Boolean,
    onToggleThemeBar: () -> Unit,
    onOpenProfileClick: () -> Unit
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val avatarInteraction = remember { MutableInteractionSource() }
    val themeInteraction = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("header_profile"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = "Halo, $userName",
                style = typography.displayHero,
                color = colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = ContentSeedData.HOME_SUBTITLE,
                style = typography.bodyDefault,
                color = colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
        ) {
            Box(
                modifier = Modifier
                    .testTag("header_theme_switcher_button")
                    .minimumInteractiveComponentSize()
                    .heightIn(min = JelajahDimens.MinTouchTarget)
                    .clickable(
                        interactionSource = themeInteraction,
                        indication = null,
                        role = Role.Button,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onToggleThemeBar()
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = JelajahDimens.ShapePill,
                    color = if (isThemeBarExpanded) colors.accent else colors.bgSurface,
                    contentColor = if (isThemeBarExpanded) colors.accentFg else colors.textPrimary,
                    tonalElevation = 2.dp,
                    shadowElevation = 2.dp,
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isThemeBarExpanded) colors.accent else colors.borderSubtle
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isThemeBarExpanded) colors.accentFg else colors.accent,
                                    CircleShape
                                )
                        )
                        Icon(
                            imageVector = Icons.Filled.Palette,
                            contentDescription = "Ubah tema lanskap Jawa Timur",
                            tint = if (isThemeBarExpanded) colors.accentFg else colors.textPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = selectedThemePreset.shortName,
                            style = typography.captionMeta,
                            color = if (isThemeBarExpanded) colors.accentFg else colors.textPrimary
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .testTag("header_avatar_button")
                    .minimumInteractiveComponentSize()
                    .size(52.dp)
                    .clickable(
                        interactionSource = avatarInteraction,
                        indication = null,
                        role = Role.Button,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onOpenProfileClick()
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                UserAvatarImage(
                    modifier = Modifier.size(48.dp),
                    contentDescription = "Buka profil $userName"
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(2.dp)
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(if (isOfflineMode) colors.warning else colors.success, CircleShape)
                        .border(2.dp, colors.bgCanvas, CircleShape)
                )
            }
        }
    }
}

@Composable
private fun EastJavaThemeQuickBar(
    selectedThemePreset: com.example.ui.theme.JelajahThemePreset,
    onSelectThemePreset: (com.example.ui.theme.JelajahThemePreset) -> Unit
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_theme_selector_card"),
        shape = JelajahDimens.ShapeCard,
        color = colors.bgSurface,
        tonalElevation = 2.dp,
        shadowElevation = 3.dp,
        border = BorderStroke(1.dp, colors.borderSubtle)
    ) {
        Column(
            modifier = Modifier.padding(JelajahDimens.Space4),
            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Palet Lanskap Jawa Timur",
                    style = typography.heading2,
                    color = colors.textPrimary
                )
                Text(
                    text = selectedThemePreset.fullName,
                    style = typography.captionMeta,
                    color = colors.accent
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                com.example.ui.theme.JelajahThemePreset.entries.forEach { preset ->
                    val isSelected = preset == selectedThemePreset
                    val interaction = remember { MutableInteractionSource() }
                    Box(
                        modifier = Modifier
                            .testTag("home_theme_pill_${preset.id}")
                            .minimumInteractiveComponentSize()
                            .heightIn(min = JelajahDimens.MinTouchTarget)
                            .clickable(
                                interactionSource = interaction,
                                indication = null,
                                role = Role.RadioButton,
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onSelectThemePreset(preset)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .height(38.dp)
                                .clip(JelajahDimens.ShapePill)
                                .background(
                                    if (isSelected) colors.accent else colors.bgElevated,
                                    JelajahDimens.ShapePill
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) colors.accent else colors.borderSubtle,
                                    shape = JelajahDimens.ShapePill
                                )
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(preset.colors.accent, CircleShape)
                                    .border(1.5.dp, preset.colors.bgSurface, CircleShape)
                            )
                            Text(
                                text = preset.shortName,
                                style = typography.smallLabel,
                                color = if (isSelected) colors.accentFg else colors.textPrimary
                            )
                        }
                    }
                }
            }

            Text(
                text = selectedThemePreset.landscapeInspiration,
                style = typography.captionMeta,
                color = colors.textSecondary
            )
        }
    }
}

@Composable
private fun SearchInputBar(
    query: String,
    onQueryChange: (String) -> Unit,
    hasActiveFilter: Boolean,
    onOpenFilterModal: () -> Unit
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = JelajahDimens.ShapePill,
        color = colors.bgSurface,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, colors.borderSubtle)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 18.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = colors.textPrimary,
                modifier = Modifier.size(22.dp)
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (query.isEmpty()) {
                    Text(
                        text = ContentSeedData.SEARCH_PLACEHOLDER,
                        style = typography.bodyDefault,
                        color = colors.textMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    textStyle = typography.bodyDefault.copy(color = colors.textPrimary),
                    cursorBrush = SolidColor(colors.borderFocus),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_input_field")
                )
            }

            if (query.isNotEmpty()) {
                val clearInteraction = remember { MutableInteractionSource() }
                Box(
                    modifier = Modifier
                        .testTag("search_clear_button")
                        .size(40.dp)
                        .clickable(
                            interactionSource = clearInteraction,
                            indication = null,
                            role = Role.Button,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onQueryChange("")
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Hapus kata kunci",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            val filterInteraction = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .testTag("search_filter_button")
                    .minimumInteractiveComponentSize()
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (hasActiveFilter) colors.success else colors.accent,
                        CircleShape
                    )
                    .clickable(
                        interactionSource = filterInteraction,
                        indication = null,
                        role = Role.Button,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onOpenFilterModal()
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Tune,
                    contentDescription = "Filter wilayah dan medan",
                    tint = colors.accentFg,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * FeaturedDestinationHeroCard (1:1 Estetika Referensi TripGlide):
 * - Ukuran: Tinggi 430.dp, borderRadius 32.dp, overflow hidden.
 * - Inner image parallax: gambar bergeser pada translateX dari -20dp ke +20dp saat di-swipe.
 * - LinearGradient 3 titik: [0.0f: rgba(0,0,0,0.05), 0.45f: rgba(0,0,0,0.15), 1.0f: rgba(10,12,16,0.88)].
 * - Lencana Kiri Atas: "Kondisi Jalur Aman" dengan background rgba(255,255,255,0.88) dan titik hijau #16A34A.
 * - Tombol Wishlist Kanan Atas: Lingkaran 44x44dp frosted glass (bg rgba(255,255,255,0.22), border 1dp rgba(255,255,255,0.35)).
 * - Tipografi Bawah: Subtitle "Jember, Jawa Timur" (13sp, rgba(255,255,255,0.82)), Judul "Tanjung Papuma" (26sp, Bold, #FFFFFF),
 *   dan pil rating transparan (borderWidth 1dp, borderColor rgba(255,255,255,0.35), bg rgba(255,255,255,0.12)).
 * - Tombol Kapsul Bawah ("Lihat detail"): Tinggi 56dp, glass gelap transparan (rgba(24,24,27,0.68), border 1dp rgba(255,255,255,0.16))
 *   dengan lingkaran putih solid (44x44dp, #FFFFFF) di sisi kanan berisi ikon chevron-right hitam (#111111).
 */
@Composable
private fun FeaturedDestinationHeroCard(
    destination: DestinationSeed,
    isSaved: Boolean,
    isPrimaryTestTarget: Boolean,
    parallaxFraction: Float,
    onToggleSave: () -> Unit,
    onViewDetailClick: () -> Unit
) {
    val typography = JelajahTheme.typography
    val cardShape = RoundedCornerShape(32.dp)
    val cardInteraction = remember { MutableInteractionSource() }
    val ctaInteraction = remember { MutableInteractionSource() }
    val isCtaPressed by ctaInteraction.collectIsPressedAsState()

    val threeStopGradient = remember {
        Brush.verticalGradient(
            colorStops = arrayOf(
                0.0f to JelajahColorTokens.HeroGradientStop0,
                0.45f to JelajahColorTokens.HeroGradientStop1,
                1.0f to JelajahColorTokens.HeroGradientStop2
            )
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(430.dp)
            .testTag("featured_card_${destination.id}")
            .clip(cardShape)
            .clickable(
                interactionSource = cardInteraction,
                indication = null,
                onClick = onViewDetailClick
            ),
        shape = cardShape,
        color = JelajahColorTokens.S03DarkSurfaceCard,
        tonalElevation = 4.dp,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(cardShape)
        ) {
            // 1. Foto Lanskap Cerah dengan Inner Image Parallax (-20dp s.d. +20dp) & Fade-in 200ms
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = 1.12f
                        scaleY = 1.12f
                        translationX = -parallaxFraction * 20.dp.toPx()
                    }
            ) {
                LazyOptimizedImage(
                    sceneType = destination.sceneType,
                    contentDescription = destination.name,
                    cornerRadius = 0.dp,
                    transitionDurationMs = 200,
                    placeholderBlurhash = "L6PZfSi_.AyE_3t7t7R**0o#DgR4",
                    shouldLoad = true,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // 2. LinearGradient 3 Titik [0.0f, 0.45f, 1.0f]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = threeStopGradient)
            )

            // 3. Baris Atas: Lencana Kiri Atas Glassmorphism & Tombol Wishlist Kanan Atas 44x44dp Frosted Glass
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Lencana Kiri Atas: "Kondisi Jalur Aman" dengan background semi-transparan rgba(255,255,255,0.88) dan titik hijau #16A34A
                val dotColor = if (destination.natureStatus.tone == NatureStatusTone.SAFE) {
                    JelajahColorTokens.HeroBadgeSafeDot
                } else {
                    JelajahColorTokens.HeroBadgeWarningDot
                }
                Row(
                    modifier = Modifier
                        .testTag(
                            if (isPrimaryTestTarget) {
                                "featured_status_badge"
                            } else {
                                "status_badge_${destination.id}"
                            }
                        )
                        .clip(JelajahDimens.ShapePill)
                        .background(JelajahColorTokens.HeroBadgeGlassBg, JelajahDimens.ShapePill)
                        .padding(horizontal = 12.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(dotColor, CircleShape)
                    )
                    Text(
                        text = destination.natureStatus.badgeText,
                        style = typography.captionMeta.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = JelajahColorTokens.HeroBadgeText
                    )
                }

                // Tombol Wishlist Kanan Atas: Lingkaran 44x44dp frosted glass (di dalam area sentuh >= 48dp)
                val heartInteraction = remember { MutableInteractionSource() }
                Box(
                    modifier = Modifier
                        .testTag(
                            if (isPrimaryTestTarget) {
                                "featured_wishlist_button"
                            } else {
                                "wishlist_button_${destination.id}"
                            }
                        )
                        .minimumInteractiveComponentSize()
                        .size(JelajahDimens.MinTouchTarget)
                        .clickable(
                            interactionSource = heartInteraction,
                            indication = null,
                            role = Role.Button,
                            onClick = onToggleSave
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(JelajahColorTokens.HeroWishlistGlassBg, CircleShape)
                            .border(
                                width = 1.dp,
                                color = JelajahColorTokens.HeroWishlistGlassBorder,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isSaved) "Hapus dari tersimpan" else "Simpan ke favorit",
                            tint = JelajahColorTokens.HeroTitleWhite,
                            modifier = Modifier.size(21.dp)
                        )
                    }
                }
            }

            // 4. Bagian Bawah Kartu: Tipografi Informasi & Tombol Kapsul Bawah "Lihat detail" (56dp)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    // Subtitle "Jember, Jawa Timur" (13sp, rgba(255,255,255,0.82))
                    Text(
                        text = destination.locationBadge,
                        style = typography.smallLabel.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = JelajahColorTokens.HeroSubtitleWhite82
                    )

                    // Judul "Tanjung Papuma" (26sp, Bold, #FFFFFF)
                    Text(
                        text = destination.name,
                        style = typography.displayHero.copy(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 32.sp
                        ),
                        color = JelajahColorTokens.HeroTitleWhite,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // Pil Rating Transparan (borderWidth: 1dp, borderColor: rgba(255,255,255,0.35), bg: rgba(255,255,255,0.12))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(JelajahDimens.ShapePill)
                                .background(
                                    JelajahColorTokens.HeroRatingPillBg,
                                    JelajahDimens.ShapePill
                                )
                                .border(
                                    width = 1.dp,
                                    color = JelajahColorTokens.HeroRatingPillBorder,
                                    shape = JelajahDimens.ShapePill
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.StarBorder,
                                contentDescription = null,
                                tint = JelajahColorTokens.HeroTitleWhite,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "${destination.rating}",
                                style = typography.smallLabel.tabularNums().copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = JelajahColorTokens.HeroTitleWhite
                            )
                        }

                        Text(
                            text = "${destination.reviewCount} ulasan",
                            style = typography.smallLabel.tabularNums().copy(
                                fontSize = 13.sp
                            ),
                            color = JelajahColorTokens.HeroSubtitleWhite82
                        )
                    }
                }

                // Tombol Kapsul Bawah ("Lihat detail"): Tinggi 56dp, glass gelap transparan rgba(24,24,27,0.68),
                // borderWidth 1dp rgba(255,255,255,0.16), lingkaran putih solid 44x44dp (#FFFFFF) berisi chevron-right hitam (#111111)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag(
                            if (isPrimaryTestTarget) {
                                "featured_cta_lihat_detail"
                            } else {
                                "cta_lihat_detail_${destination.id}"
                            }
                        )
                        .graphicsLayer {
                            alpha = if (isCtaPressed) 0.85f else 1f
                        }
                        .clip(JelajahDimens.ShapePill)
                        .background(
                            JelajahColorTokens.HeroCapsuleDarkGlassBg,
                            JelajahDimens.ShapePill
                        )
                        .border(
                            width = 1.dp,
                            color = JelajahColorTokens.HeroCapsuleDarkGlassBorder,
                            shape = JelajahDimens.ShapePill
                        )
                        .clickable(
                            interactionSource = ctaInteraction,
                            indication = null,
                            role = Role.Button,
                            onClick = onViewDetailClick
                        )
                        .padding(start = 22.dp, end = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ContentSeedData.CTA_VIEW_DETAIL,
                        style = typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = JelajahColorTokens.HeroTitleWhite
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(JelajahColorTokens.HeroCapsuleCircleBg, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = JelajahColorTokens.HeroCapsuleChevron,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    JelajahJatimTheme {
        HomeScreenS01(
            selectedCategory = "Semua",
            onSelectCategory = {},
            searchQuery = "",
            onSearchQueryChange = {},
            selectedAccessFilter = null,
            onSelectAccessFilter = {},
            savedDestinationIds = emptySet(),
            onToggleSaveDestination = {},
            isOfflineMode = false,
            onToggleOfflineMode = {},
            isSimulatingSkeleton = false,
            savedOfflineTourCount = 2,
            lastSyncedTimestamp = "10 Min lalu",
            onOpenOfflineVault = {},
            onNavigateToDestinationDetail = {},
            onNavigateToTourItinerary = {}
        )
    }
}

