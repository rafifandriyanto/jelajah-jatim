package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.AccessRequirement
import com.example.data.ContentSeedData
import com.example.ui.components.FloatingNavBar
import com.example.ui.components.FloatingNavTab
import com.example.ui.screens.ConfirmedBookingRecord
import com.example.ui.screens.DestinationDetailScreenS02
import com.example.ui.screens.HomeScreenS01
import com.example.ui.screens.MenuAndQualityGateScreen
import com.example.ui.screens.SavedWishlistScreen
import com.example.ui.screens.TourItineraryScreenS03
import com.example.ui.screens.TripsAndOfflineVaultScreen
import com.example.ui.showcase.Phase0And1ShowcaseScreen
import com.example.ui.theme.JelajahDimens
import com.example.ui.theme.JelajahMotion
import com.example.ui.theme.JelajahTheme
import com.example.ui.theme.SafeAreaWrapper

sealed interface JelajahRoute {
    data object MainTabs : JelajahRoute
    data class DestinationDetailS02(val destinationId: String) : JelajahRoute
    data class TourItineraryS03(
        val tourId: String,
        val originDestinationId: String? = null
    ) : JelajahRoute
    data object Phase0And1Showcase : JelajahRoute
}

/**
 * Root Navigation & State Coordinator - Jelajah Jatim
 * Transisi antar layar sangat responsif (120ms) untuk pengalaman 60fps tanpa jeda.
 */
@Composable
fun JelajahJatimAppRoot(
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    selectedThemePreset: com.example.ui.theme.JelajahThemePreset = com.example.ui.theme.JelajahThemePreset.MONO_EDITORIAL,
    onSelectThemePreset: (com.example.ui.theme.JelajahThemePreset) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var currentRoute by remember { mutableStateOf<JelajahRoute>(JelajahRoute.MainTabs) }
    var selectedNavTab by rememberSaveable { mutableStateOf(FloatingNavTab.HOME) }
    var userFullName by rememberSaveable { mutableStateOf("Vanessa Putri") }

    // Filter & Search state pada Screen S-01
    var selectedCategory by rememberSaveable { mutableStateOf("Semua") }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedAccessFilter by remember { mutableStateOf<AccessRequirement?>(null) }

    // State Mode Luring & Skeleton
    var isOfflineMode by rememberSaveable { mutableStateOf(false) }
    var isSimulatingSkeleton by rememberSaveable { mutableStateOf(false) }
    var lastSyncedTimestamp by rememberSaveable { mutableStateOf("09:41 WIB") }

    // Wishlist & Offline Vault
    val savedDestinationIds = remember {
        mutableStateListOf("dest-jember-001")
    }
    val savedTourIds = remember {
        mutableStateListOf("tour-jbr-3d2n")
    }
    val offlineVaultTourIds = remember {
        mutableStateListOf("tour-jbr-3d2n", "tour-meru-2d1n")
    }
    val confirmedBookings = remember {
        mutableStateListOf<ConfirmedBookingRecord>()
    }

    val userFirstName = remember(userFullName) {
        userFullName.trim().substringBefore(" ").ifBlank { "Vanessa" }
    }

    // Penyimpanan Lokal Room + Cache Gambar untuk 5 Destinasi Wisata Utama (Tetap dapat diakses saat Offline)
    val offlineDestViewModel: com.example.data.OfflineDestinationViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel()
    val cachedDestinationEntities by offlineDestViewModel.cachedEntities.collectAsStateWithLifecycle()
    val cachedDestinationSeeds by offlineDestViewModel.cachedDestinationSeeds.collectAsStateWithLifecycle()
    val totalCachedImageBytes by offlineDestViewModel.totalCachedImageBytes.collectAsStateWithLifecycle()
    val formattedCacheKb = remember(totalCachedImageBytes) {
        com.example.data.OfflineDestinationRepository.formatCacheSizeKb(totalCachedImageBytes)
    }

    AnimatedContent(
        targetState = currentRoute,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 120,
                    easing = JelajahMotion.EaseStandard
                )
            ) togetherWith fadeOut(
                animationSpec = tween(
                    durationMillis = 100,
                    easing = JelajahMotion.EaseStandard
                )
            )
        },
        label = "JelajahScreenTransition",
        modifier = modifier.fillMaxSize()
    ) { route ->
        when (route) {
            is JelajahRoute.MainTabs -> {
                SafeAreaWrapper {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        when (selectedNavTab) {
                            FloatingNavTab.HOME -> {
                                HomeScreenS01(
                                    selectedCategory = selectedCategory,
                                    onSelectCategory = { selectedCategory = it },
                                    searchQuery = searchQuery,
                                    onSearchQueryChange = { searchQuery = it },
                                    selectedAccessFilter = selectedAccessFilter,
                                    onSelectAccessFilter = { selectedAccessFilter = it },
                                    savedDestinationIds = savedDestinationIds.toSet(),
                                    onToggleSaveDestination = { id ->
                                        if (savedDestinationIds.contains(id)) {
                                            savedDestinationIds.remove(id)
                                        } else {
                                            savedDestinationIds.add(id)
                                        }
                                    },
                                    isOfflineMode = isOfflineMode,
                                    onToggleOfflineMode = { isOfflineMode = !isOfflineMode },
                                    isSimulatingSkeleton = isSimulatingSkeleton,
                                    savedOfflineTourCount = offlineVaultTourIds.size,
                                    lastSyncedTimestamp = lastSyncedTimestamp,
                                    onOpenOfflineVault = {
                                        selectedNavTab = FloatingNavTab.TRIPS
                                    },
                                    onNavigateToDestinationDetail = { destId ->
                                        currentRoute = JelajahRoute.DestinationDetailS02(destId)
                                    },
                                    onNavigateToTourItinerary = { tourId ->
                                        currentRoute = JelajahRoute.TourItineraryS03(
                                            tourId = tourId,
                                            originDestinationId = null
                                        )
                                    },
                                    onOpenProfileClick = {
                                        selectedNavTab = FloatingNavTab.MENU
                                    },
                                    userName = userFirstName,
                                    selectedThemePreset = selectedThemePreset,
                                    onSelectThemePreset = onSelectThemePreset,
                                    offlineCachedDestinations = cachedDestinationSeeds,
                                    cachedImageTotalKb = formattedCacheKb
                                )
                            }

                            FloatingNavTab.SAVED -> {
                                BackHandler { selectedNavTab = FloatingNavTab.HOME }
                                val savedDestinations = ContentSeedData.DESTINATIONS.filter {
                                    savedDestinationIds.contains(it.id)
                                }
                                val savedTours = ContentSeedData.TOUR_PACKAGES.filter {
                                    savedTourIds.contains(it.id)
                                }
                                SavedWishlistScreen(
                                    savedDestinations = savedDestinations,
                                    savedTours = savedTours,
                                    onRemoveDestination = { savedDestinationIds.remove(it) },
                                    onOpenDestination = { destId ->
                                        currentRoute = JelajahRoute.DestinationDetailS02(destId)
                                    },
                                    onOpenTour = { tourId ->
                                        currentRoute = JelajahRoute.TourItineraryS03(tourId)
                                    },
                                    onExploreHome = {
                                        selectedNavTab = FloatingNavTab.HOME
                                    }
                                )
                            }

                            FloatingNavTab.TRIPS -> {
                                BackHandler { selectedNavTab = FloatingNavTab.HOME }
                                val vaultTours = ContentSeedData.TOUR_PACKAGES.filter {
                                    offlineVaultTourIds.contains(it.id)
                                }
                                TripsAndOfflineVaultScreen(
                                    offlineSavedTours = vaultTours,
                                    confirmedBookings = confirmedBookings,
                                    lastSyncedTimestamp = lastSyncedTimestamp,
                                    onOpenTourItinerary = { tourId ->
                                        currentRoute = JelajahRoute.TourItineraryS03(tourId)
                                    },
                                    cachedTopDestinations = cachedDestinationEntities,
                                    totalCachedBytes = totalCachedImageBytes,
                                    onOpenCachedDestination = { destId ->
                                        currentRoute = JelajahRoute.DestinationDetailS02(destId)
                                    },
                                    onResyncOfflineCache = {
                                        offlineDestViewModel.resyncTop5Destinations()
                                        lastSyncedTimestamp = "Baru saja"
                                    }
                                )
                            }

                            FloatingNavTab.MENU -> {
                                BackHandler { selectedNavTab = FloatingNavTab.HOME }
                                MenuAndQualityGateScreen(
                                    isDarkTheme = isDarkTheme,
                                    onToggleDarkTheme = onToggleDarkTheme,
                                    isOfflineMode = isOfflineMode,
                                    onToggleOfflineMode = { isOfflineMode = !isOfflineMode },
                                    isSimulatingSkeleton = isSimulatingSkeleton,
                                    onToggleSkeletonSimulation = {
                                        isSimulatingSkeleton = !isSimulatingSkeleton
                                    },
                                    onOpenPhase0And1Showcase = {
                                        currentRoute = JelajahRoute.Phase0And1Showcase
                                    },
                                    savedDestinationsCount = savedDestinationIds.size,
                                    offlineVaultCount = offlineVaultTourIds.size,
                                    confirmedBookingsCount = confirmedBookings.size,
                                    userFullName = userFullName,
                                    onUpdateUserFullName = { userFullName = it },
                                    selectedThemePreset = selectedThemePreset,
                                    onSelectThemePreset = onSelectThemePreset
                                )
                            }
                        }

                        // Floating Bottom Navigation Bar (Gaya TripGlide)
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .widthIn(max = 600.dp)
                                .padding(bottom = JelajahDimens.Space4)
                        ) {
                            FloatingNavBar(
                                selectedTab = selectedNavTab,
                                onTabSelected = { tab -> selectedNavTab = tab }
                            )
                        }
                    }
                }
            }

            is JelajahRoute.DestinationDetailS02 -> {
                val destination = cachedDestinationSeeds.find { it.id == route.destinationId }
                    ?: ContentSeedData.getDestinationById(route.destinationId)
                val relatedTours = ContentSeedData.getToursForDestination(route.destinationId)

                DestinationDetailScreenS02(
                    destination = destination,
                    relatedTours = relatedTours,
                    isSaved = savedDestinationIds.contains(destination.id),
                    onToggleSave = {
                        if (savedDestinationIds.contains(destination.id)) {
                            savedDestinationIds.remove(destination.id)
                        } else {
                            savedDestinationIds.add(destination.id)
                        }
                    },
                    onBackClick = {
                        currentRoute = JelajahRoute.MainTabs
                    },
                    onSelectTourPackage = { tourId ->
                        currentRoute = JelajahRoute.TourItineraryS03(
                            tourId = tourId,
                            originDestinationId = destination.id
                        )
                    },
                    onSwitchToAlternateDestination = { altDestId ->
                        currentRoute = JelajahRoute.DestinationDetailS02(altDestId)
                    }
                )
            }

            is JelajahRoute.TourItineraryS03 -> {
                val tour = ContentSeedData.getTourById(route.tourId)

                TourItineraryScreenS03(
                    tour = tour,
                    isTourSaved = savedTourIds.contains(tour.id),
                    onToggleSaveTour = {
                        if (savedTourIds.contains(tour.id)) {
                            savedTourIds.remove(tour.id)
                        } else {
                            savedTourIds.add(tour.id)
                        }
                    },
                    isOfflineVaultSaved = offlineVaultTourIds.contains(tour.id),
                    onSaveToOfflineVault = {
                        if (!offlineVaultTourIds.contains(tour.id)) {
                            offlineVaultTourIds.add(tour.id)
                        }
                        lastSyncedTimestamp = "Baru saja"
                    },
                    isOfflineMode = isOfflineMode,
                    savedOfflineCount = offlineVaultTourIds.size,
                    lastSyncedTimestamp = lastSyncedTimestamp,
                    onOpenOfflineVault = {
                        selectedNavTab = FloatingNavTab.TRIPS
                        currentRoute = JelajahRoute.MainTabs
                    },
                    onBackClick = {
                        val originId = route.originDestinationId
                        currentRoute = if (originId != null) {
                            JelajahRoute.DestinationDetailS02(originId)
                        } else {
                            JelajahRoute.MainTabs
                        }
                    },
                    onConfirmBooking = { booking ->
                        confirmedBookings.add(0, booking)
                        if (!offlineVaultTourIds.contains(tour.id)) {
                            offlineVaultTourIds.add(tour.id)
                        }
                        selectedNavTab = FloatingNavTab.TRIPS
                        currentRoute = JelajahRoute.MainTabs
                    },
                    isDarkTheme = isDarkTheme,
                    onToggleDarkTheme = onToggleDarkTheme
                )
            }

            is JelajahRoute.Phase0And1Showcase -> {
                BackHandler { currentRoute = JelajahRoute.MainTabs }
                val colors = JelajahTheme.colors
                val typography = JelajahTheme.typography

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.bgCanvas)
                ) {
                    SafeAreaWrapper(applySafeDrawingInsets = false) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = JelajahDimens.Space4,
                                        end = JelajahDimens.Space4,
                                        top = JelajahDimens.Space8
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                            ) {
                                val backInteraction = remember { MutableInteractionSource() }
                                Box(
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .size(JelajahDimens.MinTouchTarget)
                                        .clip(CircleShape)
                                        .background(colors.bgSurface, CircleShape)
                                        .border(1.dp, colors.borderSubtle, CircleShape)
                                        .clickable(
                                            interactionSource = backInteraction,
                                            indication = null,
                                            role = Role.Button,
                                            onClick = { currentRoute = JelajahRoute.MainTabs }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Kembali ke Menu",
                                        tint = colors.textPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = "Kembali ke Aplikasi Utama",
                                    style = typography.heading2,
                                    color = colors.textPrimary
                                )
                            }

                            Phase0And1ShowcaseScreen(
                                isDarkTheme = isDarkTheme,
                                onToggleDarkTheme = onToggleDarkTheme,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
