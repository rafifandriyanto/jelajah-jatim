package com.example.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.JelajahJatimTheme
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ContentSeedData
import com.example.data.DaySchedule
import com.example.data.NatureStatusTone
import com.example.data.PaymentMethodCategory
import com.example.data.PaymentMethodOption
import com.example.data.TourPackage
import com.example.ui.components.ButtonPrimary
import com.example.ui.components.CategoryPill
import com.example.ui.components.LazyTourThumbnailImage
import com.example.ui.components.OfflineBanner
import com.example.ui.components.StatusBadge
import com.example.ui.theme.JelajahColorTokens
import com.example.ui.theme.JelajahDimens
import com.example.ui.theme.JelajahTheme
import com.example.ui.theme.OnestFontFamily
import com.example.ui.theme.tabularNums

data class ConfirmedBookingRecord(
    val bookingCode: String,
    val invoiceNumber: String,
    val tourId: String,
    val tourTitle: String,
    val departureDates: String,
    val customerName: String,
    val customerPhone: String,
    val paxCount: Int,
    val includeJeepAddon: Boolean,
    val paymentMethodName: String,
    val adminFee: Long,
    val totalAmount: Long,
    val paidAtTimestamp: String
)

private enum class CheckoutStep {
    ORDER_AND_METHOD,
    PAYMENT_INSTRUCTION,
    PAYMENT_SUCCESS
}

/**
 * Layar Detail & Jadwal Tur (Screen 3 - Gaya iOS Minimalis Modern 1:1):
 * - Zero-Lag Theme Switcher (Light #FFFFFF & #111111 / Dark #0C0D0E & #F4F4F5).
 * - Top Bar: Back lingkaran (#F4F4F5), Tengah "Eksplor Jember" (16sp SemiBold) + tanggal (12sp),
 *   Kanan: Toggle Tema (Sun/Moon) + Wishlist/Bookmark lingkaran (#F4F4F5).
 * - Segmented Control Pill Tabs: "Jadwal tur" (#111111 aktif), "Akomodasi", "Info penting" (#F4F4F5).
 * - Judul Section: "3-Hari Eksplor Jember Eksotis" (20sp Bold, margin-top 20dp, margin-bottom 16dp).
 * - Card Accordion Jadwal: 1dp border tipis (#EAEAEA / rgba(255,255,255,0.08)), tanpa drop shadow tebal,
 *   animasi buka-tutup 200ms + rotasi chevron 180deg 200ms + haptic selection feedback,
 *   thumbnail 52x52dp radius 12dp dengan Blurhash & transisi 200ms.
 * - Sticky Bottom CTA: Kapsul penuh "Pesan tur ini" (height 52dp, radius 9999px) dengan backdrop blur/transparan
 *   dan micro-interaction opacity 0.85f tanpa bounce scale.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TourItineraryScreenS03(
    tour: TourPackage,
    isTourSaved: Boolean,
    onToggleSaveTour: () -> Unit,
    isOfflineVaultSaved: Boolean,
    onSaveToOfflineVault: () -> Unit,
    isOfflineMode: Boolean,
    savedOfflineCount: Int,
    lastSyncedTimestamp: String,
    onOpenOfflineVault: () -> Unit,
    onBackClick: () -> Unit,
    onConfirmBooking: (ConfirmedBookingRecord) -> Unit,
    isDarkTheme: Boolean = false,
    onToggleDarkTheme: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBackClick)

    val colors = JelajahTheme.colors
    val s03 = JelajahTheme.s03

    var selectedTabIndex by rememberSaveable(tour.id) { mutableIntStateOf(0) }
    var includeJeepAddon by rememberSaveable(tour.id) { mutableStateOf(false) }
    var paxCount by rememberSaveable(tour.id) { mutableIntStateOf(1) }
    var showCheckoutSheet by rememberSaveable { mutableStateOf(false) }

    // Default: Kartu Hari 1 Terbuka, Hari 2 & Hari 3 Tertutup
    val expandedDays = remember(tour.id) {
        mutableStateListOf(1)
    }

    // Lazy mount tracking: catat kartu mana saja yang pernah dibuka
    val everExpandedDays = remember(tour.id) {
        mutableStateListOf(1)
    }

    val pricePerPerson = tour.pricePerPerson +
        if (includeJeepAddon) tour.addonOption.pricePerPerson else 0L

    val sectionHeadingText = remember(tour.id, tour.durationDays, tour.title) {
        if (tour.id == "tour-jbr-3d2n") {
            "3-Hari Eksplor Jember Eksotis"
        } else {
            "${tour.durationDays}-Hari ${tour.title} Eksotis"
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(s03.canvas)
            .testTag("tour_itinerary_screen_s03"),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 600.dp)
                .background(s03.canvas)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // 1. Top Bar Navigasi (Back lingkaran kiri, Judul & Tanggal tengah, Theme Toggle & Wishlist kanan)
            TourHeaderNav(
                tour = tour,
                isTourSaved = isTourSaved,
                onToggleSaveTour = onToggleSaveTour,
                isDarkTheme = isDarkTheme,
                onToggleDarkTheme = onToggleDarkTheme,
                onBackClick = onBackClick
            )

            // 2. Segmented Control / Pill Tabs: "Jadwal tur", "Akomodasi", "Info penting"
            SegmentedTabs(
                tabs = ContentSeedData.TOUR_TABS,
                selectedIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it },
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 12.dp,
                    bottom = 4.dp
                )
            )

            // 3. Konten Utama Dapat Digulir (60-120 FPS)
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .testTag("s03_itinerary_lazy_column"),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 0.dp,
                    bottom = 128.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (isOfflineMode) {
                    item(key = "offline_banner") {
                        Spacer(modifier = Modifier.height(8.dp))
                        OfflineBanner(
                            savedOfflineCount = savedOfflineCount,
                            lastSyncedTimestamp = lastSyncedTimestamp,
                            onOpenOfflineVault = onOpenOfflineVault
                        )
                    }
                }

                when (selectedTabIndex) {
                    0 -> {
                        // 4. Judul Section: "3-Hari Eksplor Jember Eksotis" (20px Bold #18181B, mt 20px, mb 16px)
                        item(key = "section_heading") {
                            Text(
                                text = sectionHeadingText,
                                style = TextStyle(
                                    fontFamily = OnestFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    lineHeight = 26.sp
                                ).tabularNums(),
                                color = s03.textPrimary,
                                modifier = Modifier
                                    .padding(top = 20.dp, bottom = 4.dp)
                                    .testTag("s03_section_title")
                            )
                        }

                        // 5. Komponen Card Accordion Jadwal (Hari 1 Terbuka, Hari 2 & Hari 3 Tertutup)
                        items(
                            items = tour.itineraryDays,
                            key = { it.dayNumber }
                        ) { daySchedule ->
                            val isExpanded = expandedDays.contains(daySchedule.dayNumber)
                            val hasEverExpanded = everExpandedDays.contains(daySchedule.dayNumber)

                            DayAccordionCard(
                                daySchedule = daySchedule,
                                expanded = isExpanded,
                                hasEverExpanded = hasEverExpanded,
                                onToggleExpand = {
                                    if (isExpanded) {
                                        expandedDays.remove(daySchedule.dayNumber)
                                    } else {
                                        if (!everExpandedDays.contains(daySchedule.dayNumber)) {
                                            everExpandedDays.add(daySchedule.dayNumber)
                                        }
                                        expandedDays.add(daySchedule.dayNumber)
                                    }
                                }
                            )
                        }

                        // Opsi Tambahan: Add-on Shuttle Jeep 4WD & Simpan Luring
                        item(key = "addon_shuttle") {
                            Spacer(modifier = Modifier.height(4.dp))
                            AddonShuttleSelectorCard(
                                tour = tour,
                                checked = includeJeepAddon,
                                onCheckedChange = { includeJeepAddon = it }
                            )
                        }

                        item(key = "offline_vault") {
                            OfflineVaultSyncCard(
                                tour = tour,
                                isSavedOffline = isOfflineVaultSaved,
                                onSaveOfflineClick = onSaveToOfflineVault
                            )
                        }
                    }

                    1 -> {
                        item(key = "tab_accommodation") {
                            Spacer(modifier = Modifier.height(16.dp))
                            FacilitiesAndHotelSection(
                                tour = tour,
                                includeJeepAddon = includeJeepAddon,
                                onToggleJeepAddon = { includeJeepAddon = it }
                            )
                        }
                    }

                    2 -> {
                        item(key = "tab_important_info") {
                            Spacer(modifier = Modifier.height(16.dp))
                            TerrainAndAccessSection(
                                tour = tour,
                                isSavedOffline = isOfflineVaultSaved,
                                onSaveOfflineClick = onSaveToOfflineVault
                            )
                        }
                    }
                }
            }
        }

        // 6. Tombol Booking Melayang (Sticky Bottom CTA "Pesan tur ini")
        StickyBottomBookingBar(
            onBookNowClick = { showCheckoutSheet = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .widthIn(max = 600.dp)
        )
    }

    // Lembar Pembayaran & Checkout 3 Tahap (Data Pemesan & Metode -> QRIS/VA -> E-Voucher Lunas)
    if (showCheckoutSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showCheckoutSheet = false },
            sheetState = sheetState,
            shape = JelajahDimens.ShapeSheet,
            containerColor = colors.bgSurface,
            contentColor = colors.textPrimary
        ) {
            PaymentCheckoutSheetContent(
                tour = tour,
                paxCount = paxCount,
                onPaxCountChange = { newPax -> paxCount = newPax.coerceIn(1, 10) },
                includeJeepAddon = includeJeepAddon,
                onToggleJeepAddon = { includeJeepAddon = it },
                pricePerPerson = pricePerPerson,
                onCompletePayment = { confirmedRecord ->
                    showCheckoutSheet = false
                    onConfirmBooking(confirmedRecord)
                }
            )
        }
    }
}

/**
 * 2. Struktur Top Bar Navigasi:
 * - Kiri: Tombol Back lingkaran (#F4F4F5 / #1F2227) dengan ikon chevron-left.
 * - Tengah: Judul "Eksplor Jember" (font 16px semi-bold #18181B) dan subtitle tanggal
 *   "Kamis, 12 Nov - Sabtu, 14 Nov" (font 12px teks sekunder #71717A).
 * - Kanan: Toggle switch ikon kecil (Sun/Moon) di sebelah tombol Bookmark/Wishlist lingkaran (#F4F4F5)
 *   dengan ikon heart outline.
 */
@Composable
private fun TourHeaderNav(
    tour: TourPackage,
    isTourSaved: Boolean,
    onToggleSaveTour: () -> Unit,
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    onBackClick: () -> Unit
) {
    val s03 = JelajahTheme.s03
    val colors = JelajahTheme.colors
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Kiri: Tombol Back lingkaran (#F4F4F5) dengan ikon chevron-left
        val backInteraction = remember { MutableInteractionSource() }
        Box(
            modifier = Modifier
                .testTag("s03_back_button")
                .minimumInteractiveComponentSize()
                .size(48.dp)
                .clip(CircleShape)
                .background(s03.inactiveTabBg, CircleShape)
                .clickable(
                    interactionSource = backInteraction,
                    indication = null,
                    role = Role.Button,
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onBackClick()
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Kembali ke Detail Destinasi",
                tint = s03.textPrimary,
                modifier = Modifier.size(22.dp)
            )
        }

        // Tengah: Judul "Eksplor Jember" (16px semi-bold) & subtitle tanggal (12px teks sekunder)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = tour.title,
                style = TextStyle(
                    fontFamily = OnestFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                ),
                color = s03.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = tour.departureDatesLabel,
                style = TextStyle(
                    fontFamily = OnestFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                ).tabularNums(),
                color = s03.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Kanan: Toggle Tema (Sun/Moon) di sebelah tombol Wishlist/Bookmark lingkaran (#F4F4F5)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val themeInteraction = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .testTag("s03_theme_toggle_button")
                    .minimumInteractiveComponentSize()
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(s03.inactiveTabBg, CircleShape)
                    .clickable(
                        interactionSource = themeInteraction,
                        indication = null,
                        role = Role.Switch,
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onToggleDarkTheme()
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                    contentDescription = if (isDarkTheme) "Ganti ke mode terang" else "Ganti ke mode gelap",
                    tint = s03.textPrimary,
                    modifier = Modifier.size(19.dp)
                )
            }

            val saveInteraction = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .testTag("s03_bookmark_button")
                    .minimumInteractiveComponentSize()
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(s03.inactiveTabBg, CircleShape)
                    .clickable(
                        interactionSource = saveInteraction,
                        indication = null,
                        role = Role.Button,
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onToggleSaveTour()
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isTourSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isTourSaved) "Tersimpan di favorit" else "Simpan paket tur",
                    tint = if (isTourSaved) colors.danger else s03.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * 3. Segmented Control / Tabs:
 * - Tab 1 (Aktif): "Jadwal tur" -> background #111111 (Light) / #F4F4F5 (Dark), teks #FFFFFF / #0C0D0E, pill.
 * - Tab 2: "Akomodasi" -> background #F4F4F5 (Light) / #1F2227 (Dark), teks #71717A / #8E95A2, pill.
 * - Tab 3: "Info penting" -> background #F4F4F5 (Light) / #1F2227 (Dark), teks #71717A / #8E95A2, pill.
 */
@Composable
private fun SegmentedTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val s03 = JelajahTheme.s03
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .testTag("s03_segmented_tabs"),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex
            val bg by animateColorAsState(
                targetValue = if (isSelected) s03.ctaAndActiveTabBg else s03.inactiveTabBg,
                animationSpec = tween(durationMillis = 150, easing = FastOutSlowInEasing),
                label = "SegmentTabBg_$index"
            )
            val fg by animateColorAsState(
                targetValue = if (isSelected) s03.ctaAndActiveTabText else s03.inactiveTabText,
                animationSpec = tween(durationMillis = 150, easing = FastOutSlowInEasing),
                label = "SegmentTabFg_$index"
            )
            val interactionSource = remember { MutableInteractionSource() }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .testTag("s03_tab_$index")
                    .minimumInteractiveComponentSize()
                    .heightIn(min = 48.dp)
                    .clip(CircleShape)
                    .background(bg, CircleShape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        role = Role.Tab,
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onTabSelected(index)
                        }
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = OnestFontFamily,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    color = fg,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * 5. Komponen Card Accordion Jadwal (1:1 iOS Minimalis Modern + 200ms UI-Thread Motion + Lazy Image):
 * - Border kartu: 1px border tipis #EAEAEA (Light) / rgba(255,255,255,0.08) (Dark), tanpa drop shadow tebal.
 * - Header: Foto thumbnail kotak sudut membulat (52x52dp, radius 12dp) dengan Blurhash placeholder &
 *   fade-in 200ms, teks "Hari X" (12px teks sekunder #71717A) di atas judul (15px semi-bold #18181B),
 *   dan ikon chevron di ujung kanan yang berotasi 180 derajat secara halus (durasi 200ms).
 * - Isi konten:
 *   Sub-judul "Pagi", "Siang", "Sore" (13px semi-bold #18181B) & deskripsi (13px #71717A).
 */
@Composable
private fun DayAccordionCard(
    daySchedule: DaySchedule,
    expanded: Boolean,
    hasEverExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    val s03 = JelajahTheme.s03
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }

    // Ikon chevron berotasi 0deg (chevron-up untuk terbuka) ke 180deg (chevron-down untuk tertutup) durasi 200ms
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 0f else 180f,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "AccordionChevron_${daySchedule.dayNumber}"
    )

    val cardShape = RoundedCornerShape(16.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = LinearEasing
                )
            )
            .testTag("day_accordion_${daySchedule.dayNumber}"),
        shape = cardShape,
        color = s03.surfaceCard,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = BorderStroke(
            width = 1.dp,
            color = s03.cardBorder
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("day_accordion_header_${daySchedule.dayNumber}")
                    .minimumInteractiveComponentSize()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        role = Role.Button,
                        onClick = {
                            // Haptics.selectionAsync() equivalent
                            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onToggleExpand()
                        }
                    )
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Thumbnail 52x52dp, radius 12dp, Blurhash 'L6PZfSi_.AyE_3t7t7R**0o#DgR4', transition 200ms
                LazyTourThumbnailImage(
                    sceneType = daySchedule.sceneType,
                    contentDescription = "Thumbnail Hari ${daySchedule.dayNumber} ${daySchedule.title}",
                    shouldLoad = true,
                    modifier = Modifier.testTag("day_accordion_thumb_${daySchedule.dayNumber}")
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Hari ${daySchedule.dayNumber}",
                        style = TextStyle(
                            fontFamily = OnestFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        ).tabularNums(),
                        color = s03.textSecondary
                    )
                    Text(
                        text = daySchedule.title,
                        style = TextStyle(
                            fontFamily = OnestFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            lineHeight = 20.sp
                        ),
                        color = s03.textPrimary
                    )
                }

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = if (expanded) "Tutup rincian hari" else "Buka rincian hari",
                    tint = s03.textSecondary,
                    modifier = Modifier
                        .size(20.dp)
                        .graphicsLayer {
                            rotationZ = chevronRotation
                        }
                )
            }

            // Konten detail jadwal (Pagi, Siang, Sore) dengan transisi 200ms & lazy mount
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                )
            ) {
                if (expanded || hasEverExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 14.dp,
                                end = 14.dp,
                                top = 2.dp,
                                bottom = 16.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = s03.surfaceBorder
                        )

                        daySchedule.activities.forEach { activity ->
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = activity.timeOfDayLabel,
                                    style = TextStyle(
                                        fontFamily = OnestFontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp
                                    ),
                                    color = s03.textPrimary
                                )

                                Text(
                                    text = activity.description,
                                    style = TextStyle(
                                        fontFamily = OnestFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 13.sp,
                                        lineHeight = 19.sp
                                    ),
                                    color = s03.textSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddonShuttleSelectorCard(
    tour: TourPackage,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val colors = JelajahTheme.colors
    val s03 = JelajahTheme.s03
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val addon = tour.addonOption

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("addon_shuttle_card")
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Checkbox,
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onCheckedChange(!checked)
                }
            ),
        shape = RoundedCornerShape(16.dp),
        color = if (checked) colors.successSurface else s03.surfaceCard,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = BorderStroke(
            width = 1.dp,
            color = if (checked) colors.success.copy(alpha = 0.45f) else s03.cardBorder
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
        ) {
            Icon(
                imageVector = if (checked) Icons.Filled.CheckBox else Icons.Filled.CheckBoxOutlineBlank,
                contentDescription = if (checked) "Jeep 4WD dipilih" else "Pilih Jeep 4WD",
                tint = if (checked) colors.success else s03.textSecondary,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(22.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusBadge(
                        label = "Mitra 4WD Terverifikasi",
                        tone = if (checked) NatureStatusTone.SAFE else NatureStatusTone.WARNING
                    )
                    Text(
                        text = ContentSeedData.formatRupiah(addon.pricePerPerson, includePlusPrefix = true),
                        style = typography.heading2.tabularNums(),
                        color = if (checked) colors.success else s03.textPrimary
                    )
                }

                Text(
                    text = addon.title,
                    style = TextStyle(
                        fontFamily = OnestFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    ),
                    color = s03.textPrimary
                )
                Text(
                    text = addon.subtitle,
                    style = TextStyle(
                        fontFamily = OnestFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    ),
                    color = s03.textSecondary
                )
            }
        }
    }
}

@Composable
private fun OfflineVaultSyncCard(
    tour: TourPackage,
    isSavedOffline: Boolean,
    onSaveOfflineClick: () -> Unit
) {
    val colors = JelajahTheme.colors
    val s03 = JelajahTheme.s03

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("offline_vault_sync_card"),
        shape = RoundedCornerShape(16.dp),
        color = s03.surfaceCard,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = BorderStroke(1.dp, s03.cardBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = if (isSavedOffline) Icons.Filled.CloudDone else Icons.Filled.Download,
                    contentDescription = null,
                    tint = if (isSavedOffline) colors.success else s03.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Panduan Luring (Offline Vault)",
                        style = TextStyle(
                            fontFamily = OnestFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            lineHeight = 20.sp
                        ),
                        color = s03.textPrimary
                    )
                    Text(
                        text = "Simpan jadwal harian, nomor darurat Ranger, & titik GPS saat memasuki area tanpa sinyal.",
                        style = TextStyle(
                            fontFamily = OnestFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        ),
                        color = s03.textSecondary
                    )
                }
            }

            ButtonPrimary(
                text = if (isSavedOffline) {
                    ContentSeedData.CTA_SAVED_OFFLINE
                } else {
                    ContentSeedData.CTA_SAVE_OFFLINE
                },
                leadingIcon = if (isSavedOffline) Icons.Filled.CheckCircle else Icons.Filled.Download,
                onClick = onSaveOfflineClick,
                modifier = Modifier.fillMaxWidth(),
                testTag = "btn_save_itinerary_offline"
            )
        }
    }
}

@Composable
private fun FacilitiesAndHotelSection(
    tour: TourPackage,
    includeJeepAddon: Boolean,
    onToggleJeepAddon: (Boolean) -> Unit
) {
    val colors = JelajahTheme.colors
    val s03 = JelajahTheme.s03
    val typography = JelajahTheme.typography

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = s03.surfaceCard,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            border = BorderStroke(1.dp, s03.cardBorder)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Hotel,
                        contentDescription = null,
                        tint = s03.textPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = tour.hotelName,
                            style = typography.heading2,
                            color = s03.textPrimary
                        )
                        Text(
                            text = tour.hotelRatingLabel,
                            style = typography.captionMeta.tabularNums(),
                            color = s03.textSecondary
                        )
                    }
                }

                HorizontalDivider(color = s03.surfaceBorder)

                Text(
                    text = "Titik Penjemputan Resmi",
                    style = typography.smallLabel,
                    color = s03.textSecondary
                )
                Text(
                    text = tour.pickupPoint,
                    style = typography.bodyMedium.tabularNums(),
                    color = s03.textPrimary
                )
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = s03.surfaceCard,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            border = BorderStroke(1.dp, s03.cardBorder)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Fasilitas Paket Tur",
                    style = typography.heading2,
                    color = s03.textPrimary
                )

                tour.facilitiesList.forEach { itemText ->
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = colors.success,
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .size(16.dp)
                        )
                        Text(
                            text = itemText,
                            style = typography.bodyDefault.tabularNums(),
                            color = s03.textSecondary
                        )
                    }
                }
            }
        }

        AddonShuttleSelectorCard(
            tour = tour,
            checked = includeJeepAddon,
            onCheckedChange = onToggleJeepAddon
        )
    }
}

@Composable
private fun TerrainAndAccessSection(
    tour: TourPackage,
    isSavedOffline: Boolean,
    onSaveOfflineClick: () -> Unit
) {
    val colors = JelajahTheme.colors
    val s03 = JelajahTheme.s03
    val typography = JelajahTheme.typography

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = colors.warningSurface,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            border = BorderStroke(1.dp, colors.warning.copy(alpha = 0.30f))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusBadge(
                    label = "Perlu Fisik Prima & Armada 4WD",
                    tone = NatureStatusTone.WARNING,
                    leadingIcon = Icons.Filled.WarningAmber
                )
                Text(
                    text = "Panduan Keselamatan & Kondisi Medan",
                    style = typography.heading2,
                    color = s03.textPrimary
                )
                Text(
                    text = tour.terrainAdvisory,
                    style = typography.bodyDefault.tabularNums(),
                    color = s03.textPrimary
                )
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = s03.surfaceCard,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            border = BorderStroke(1.dp, s03.cardBorder)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = null,
                        tint = colors.success,
                        modifier = Modifier.size(18.dp)
                    )
                    Column {
                        Text(
                            text = tour.emergencyContactName,
                            style = typography.bodyMedium,
                            color = s03.textPrimary
                        )
                        Text(
                            text = tour.emergencyContactNumber,
                            style = typography.smallLabel.tabularNums(),
                            color = s03.textSecondary
                        )
                    }
                }

                HorizontalDivider(color = s03.surfaceBorder)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Map,
                        contentDescription = null,
                        tint = s03.textPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Column {
                        Text(
                            text = "Titik Koordinat GPS Luring",
                            style = typography.bodyMedium,
                            color = s03.textPrimary
                        )
                        Text(
                            text = tour.gpsCoordinates,
                            style = typography.captionMeta.tabularNums(),
                            color = s03.textSecondary
                        )
                    }
                }
            }
        }

        OfflineVaultSyncCard(
            tour = tour,
            isSavedOffline = isSavedOffline,
            onSaveOfflineClick = onSaveOfflineClick
        )
    }
}

/**
 * 6. Tombol Booking Melayang (Sticky Bottom CTA):
 * - Container bawah dengan blur/transparan latar bawah.
 * - Tombol kapsul penuh (margin kiri-kanan 20px, tinggi 52px, border-radius 9999px).
 * - Background #111111 (Light) / #F4F4F5 (Dark), teks "Pesan tur ini" (15px semi-bold).
 * - Micro-interaction saat ditekan: opacity 0.85, tanpa scale melar.
 */
@Composable
private fun StickyBottomBookingBar(
    onBookNowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val s03 = JelajahTheme.s03
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = tween(durationMillis = 120, easing = LinearEasing),
        label = "StickyBottomCtaOpacity"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .testTag("sticky_bottom_booking_bar")
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        s03.canvas.copy(alpha = 0.0f),
                        s03.canvas.copy(alpha = 0.88f),
                        s03.canvas
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 12.dp,
                bottom = 16.dp
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("s03_cta_pesan_sekarang")
                .minimumInteractiveComponentSize()
                .graphicsLayer {
                    alpha = buttonAlpha
                }
                .clip(CircleShape)
                .background(s03.ctaAndActiveTabBg, CircleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onBookNowClick()
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ContentSeedData.CTA_BOOK_THIS_TOUR,
                style = TextStyle(
                    fontFamily = OnestFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                ),
                color = s03.ctaAndActiveTabText
            )
        }
    }
}

/**
 * Sistem Pembayaran & Checkout 3 Tahap (Data Pemesan & Metode -> Instruksi QRIS/VA -> Bukti Lunas & E-Tiket)
 */
@Composable
private fun PaymentCheckoutSheetContent(
    tour: TourPackage,
    paxCount: Int,
    onPaxCountChange: (Int) -> Unit,
    includeJeepAddon: Boolean,
    onToggleJeepAddon: (Boolean) -> Unit,
    pricePerPerson: Long,
    onCompletePayment: (ConfirmedBookingRecord) -> Unit
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val clipboardManager = LocalClipboardManager.current

    var currentStep by rememberSaveable { mutableStateOf(CheckoutStep.ORDER_AND_METHOD) }
    var customerName by rememberSaveable { mutableStateOf("Vanessa Putri") }
    var customerPhone by rememberSaveable { mutableStateOf("0812-3456-7890") }
    var selectedMethodId by rememberSaveable { mutableStateOf(ContentSeedData.PAYMENT_METHODS.first().id) }
    var copiedAccountFeedback by rememberSaveable { mutableStateOf(false) }

    val selectedMethod = remember(selectedMethodId) {
        ContentSeedData.PAYMENT_METHODS.find { it.id == selectedMethodId }
            ?: ContentSeedData.PAYMENT_METHODS.first()
    }

    val baseTourSubtotal = tour.pricePerPerson * paxCount
    val addonSubtotal = if (includeJeepAddon) tour.addonOption.pricePerPerson * paxCount else 0L
    val adminFee = selectedMethod.adminFee
    val grandTotal = baseTourSubtotal + addonSubtotal + adminFee

    val bookingCode = remember(tour.id, paxCount) {
        "JJ-${tour.durationDays}D-${210 + paxCount * 19}"
    }
    val invoiceNumber = remember(tour.id, selectedMethodId) {
        "INV-20261112-${840 + paxCount * 7}"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(
                start = JelajahDimens.Space6,
                end = JelajahDimens.Space6,
                bottom = JelajahDimens.Space8
            ),
        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space4)
    ) {
        // Indikator Langkah Checkout (1. Rincian -> 2. Pembayaran -> 3. E-Tiket)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusBadge(
                label = when (currentStep) {
                    CheckoutStep.ORDER_AND_METHOD -> "Langkah 1/3 • Rincian & Metode Bayar"
                    CheckoutStep.PAYMENT_INSTRUCTION -> "Langkah 2/3 • Selesaikan Pembayaran"
                    CheckoutStep.PAYMENT_SUCCESS -> "Langkah 3/3 • Pembayaran Lunas"
                },
                tone = if (currentStep == CheckoutStep.PAYMENT_SUCCESS) {
                    NatureStatusTone.SAFE
                } else {
                    NatureStatusTone.WARNING
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.VerifiedUser,
                    contentDescription = null,
                    tint = colors.success,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Transaksi Aman",
                    style = typography.captionMeta,
                    color = colors.success
                )
            }
        }

        when (currentStep) {
            CheckoutStep.ORDER_AND_METHOD -> {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Pemesanan & Metode Pembayaran",
                        style = typography.displayHero,
                        color = colors.textPrimary
                    )
                    Text(
                        text = "${tour.title} • ${tour.departureDatesLabel}",
                        style = typography.bodyDefault.tabularNums(),
                        color = colors.textSecondary
                    )
                }

                // Form Data Pemesan (Nama & WhatsApp)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = JelajahDimens.ShapeCard,
                    color = colors.bgElevated,
                    border = BorderStroke(1.dp, colors.borderSubtle)
                ) {
                    Column(
                        modifier = Modifier.padding(JelajahDimens.Space4),
                        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                    ) {
                        Text(
                            text = "Data Pemesan Utama (E-Tiket & Koordinasi Jemputan)",
                            style = typography.smallLabel,
                            color = colors.textSecondary
                        )

                        // Input Nama Lengkap
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(JelajahDimens.MinTouchTarget)
                                .clip(JelajahDimens.ShapeThumb)
                                .background(colors.bgSurface, JelajahDimens.ShapeThumb)
                                .border(1.dp, colors.borderSubtle, JelajahDimens.ShapeThumb)
                                .padding(horizontal = JelajahDimens.Space3),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null,
                                tint = colors.textSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                            BasicTextField(
                                value = customerName,
                                onValueChange = { customerName = it },
                                singleLine = true,
                                textStyle = typography.bodyMedium.copy(color = colors.textPrimary),
                                cursorBrush = SolidColor(colors.borderFocus),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        // Input Nomor WhatsApp
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(JelajahDimens.MinTouchTarget)
                                .clip(JelajahDimens.ShapeThumb)
                                .background(colors.bgSurface, JelajahDimens.ShapeThumb)
                                .border(1.dp, colors.borderSubtle, JelajahDimens.ShapeThumb)
                                .padding(horizontal = JelajahDimens.Space3),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Phone,
                                contentDescription = null,
                                tint = colors.textSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                            BasicTextField(
                                value = customerPhone,
                                onValueChange = { customerPhone = it },
                                singleLine = true,
                                textStyle = typography.bodyMedium.tabularNums().copy(color = colors.textPrimary),
                                cursorBrush = SolidColor(colors.borderFocus),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                // Pemilih Jumlah Peserta (Touch target >= 48dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(JelajahDimens.ShapeCard)
                        .background(colors.bgElevated)
                        .padding(horizontal = JelajahDimens.Space4, vertical = JelajahDimens.Space2),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Jumlah peserta",
                            style = typography.bodyMedium,
                            color = colors.textPrimary
                        )
                        Text(
                            text = "${ContentSeedData.formatRupiah(pricePerPerson)} / orang",
                            style = typography.captionMeta.tabularNums(),
                            color = colors.textSecondary
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                    ) {
                        val decInteraction = remember { MutableInteractionSource() }
                        Box(
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .size(JelajahDimens.MinTouchTarget)
                                .clip(CircleShape)
                                .background(colors.bgSurface, CircleShape)
                                .border(1.dp, colors.borderSubtle, CircleShape)
                                .clickable(
                                    interactionSource = decInteraction,
                                    indication = null,
                                    role = Role.Button,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        onPaxCountChange(paxCount - 1)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "-",
                                style = typography.heading1.tabularNums(),
                                color = colors.textPrimary
                            )
                        }

                        Text(
                            text = "$paxCount orang",
                            style = typography.heading2.tabularNums(),
                            color = colors.textPrimary
                        )

                        val incInteraction = remember { MutableInteractionSource() }
                        Box(
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .size(JelajahDimens.MinTouchTarget)
                                .clip(CircleShape)
                                .background(colors.bgSurface, CircleShape)
                                .border(1.dp, colors.borderSubtle, CircleShape)
                                .clickable(
                                    interactionSource = incInteraction,
                                    indication = null,
                                    role = Role.Button,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        onPaxCountChange(paxCount + 1)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+",
                                style = typography.heading1.tabularNums(),
                                color = colors.textPrimary
                            )
                        }
                    }
                }

                // Opsi Add-on Shuttle Jeep 4WD
                AddonShuttleSelectorCard(
                    tour = tour,
                    checked = includeJeepAddon,
                    onCheckedChange = onToggleJeepAddon
                )

                // Pilihan Metode Pembayaran (QRIS, BCA VA, Mandiri VA, BNI/BRI VA, GoPay/Dana)
                Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)) {
                    Text(
                        text = "Pilih Metode Pembayaran",
                        style = typography.heading2,
                        color = colors.textPrimary
                    )

                    ContentSeedData.PAYMENT_METHODS.forEach { method ->
                        val isSelected = method.id == selectedMethodId
                        val methodInteraction = remember { MutableInteractionSource() }
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("payment_method_${method.id}")
                                .minimumInteractiveComponentSize()
                                .clip(JelajahDimens.ShapeCard)
                                .clickable(
                                    interactionSource = methodInteraction,
                                    indication = null,
                                    role = Role.RadioButton,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        selectedMethodId = method.id
                                    }
                                ),
                            shape = JelajahDimens.ShapeCard,
                            color = if (isSelected) colors.successSurface else colors.bgSurface,
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isSelected) colors.success.copy(alpha = 0.50f) else colors.borderSubtle
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(JelajahDimens.Space3),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                            ) {
                                Icon(
                                    imageVector = when (method.category) {
                                        PaymentMethodCategory.QRIS -> Icons.Filled.QrCode2
                                        PaymentMethodCategory.VIRTUAL_ACCOUNT -> Icons.Filled.AccountBalance
                                        PaymentMethodCategory.EWALLET -> Icons.Filled.AccountBalanceWallet
                                    },
                                    contentDescription = null,
                                    tint = if (isSelected) colors.success else colors.textPrimary,
                                    modifier = Modifier.size(22.dp)
                                )

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = method.name,
                                            style = typography.bodyMedium,
                                            color = colors.textPrimary
                                        )
                                        Text(
                                            text = method.badgeText,
                                            style = typography.captionMeta.tabularNums(),
                                            color = if (method.adminFee == 0L) colors.success else colors.textSecondary
                                        )
                                    }
                                    Text(
                                        text = method.subtitle,
                                        style = typography.captionMeta,
                                        color = colors.textSecondary
                                    )
                                }

                                Icon(
                                    imageVector = if (isSelected) {
                                        Icons.Filled.RadioButtonChecked
                                    } else {
                                        Icons.Filled.RadioButtonUnchecked
                                    },
                                    contentDescription = null,
                                    tint = if (isSelected) colors.success else colors.textMuted,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                // Rincian Tagihan Transparan (Tabular-Nums)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = JelajahDimens.ShapeCard,
                    color = colors.bgElevated,
                    border = BorderStroke(1.dp, colors.borderSubtle)
                ) {
                    Column(
                        modifier = Modifier.padding(JelajahDimens.Space4),
                        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
                    ) {
                        Text(
                            text = "Rincian Tagihan",
                            style = typography.smallLabel,
                            color = colors.textPrimary
                        )
                        InvoiceLineItem(
                            label = "Paket Tur ($paxCount orang)",
                            value = ContentSeedData.formatRupiah(baseTourSubtotal)
                        )
                        if (includeJeepAddon) {
                            InvoiceLineItem(
                                label = "Armada Jeep 4WD ($paxCount orang)",
                                value = ContentSeedData.formatRupiah(addonSubtotal, includePlusPrefix = true)
                            )
                        }
                        InvoiceLineItem(
                            label = "Asuransi Perjalanan & Konservasi",
                            value = "Termasuk (Rp 0)"
                        )
                        InvoiceLineItem(
                            label = "Biaya Layanan (${selectedMethod.name})",
                            value = if (adminFee == 0L) "Bebas Biaya (Rp 0)" else ContentSeedData.formatRupiah(adminFee)
                        )
                        HorizontalDivider(color = colors.borderSubtle)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Pembayaran",
                                style = typography.heading2,
                                color = colors.textPrimary
                            )
                            Text(
                                text = ContentSeedData.formatRupiah(grandTotal),
                                style = typography.displayHero.tabularNums(),
                                color = colors.textPrimary
                            )
                        }
                    }
                }

                ButtonPrimary(
                    text = ContentSeedData.CTA_PROCEED_PAYMENT,
                    onClick = { currentStep = CheckoutStep.PAYMENT_INSTRUCTION },
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "btn_proceed_to_payment"
                )
            }

            CheckoutStep.PAYMENT_INSTRUCTION -> {
                // TAHAP 2: INSTRUKSI PEMBAYARAN QRIS / VIRTUAL ACCOUNT / E-WALLET
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = selectedMethod.name,
                        style = typography.displayHero,
                        color = colors.textPrimary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Schedule,
                            contentDescription = null,
                            tint = colors.warning,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Selesaikan dalam 14:59 • Invoice $invoiceNumber",
                            style = typography.smallLabel.tabularNums(),
                            color = colors.warning
                        )
                    }
                }

                if (selectedMethod.category == PaymentMethodCategory.QRIS) {
                    // Tampilan Kode QRIS Standar Nasional
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = JelajahDimens.ShapeCard,
                        color = colors.bgElevated,
                        border = BorderStroke(1.dp, colors.borderSubtle)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(JelajahDimens.Space4),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                        ) {
                            Text(
                                text = "PT JELAJAH JATIM NUSANTARA",
                                style = typography.heading2,
                                color = colors.textPrimary
                            )
                            Text(
                                text = selectedMethod.accountNumberOrNmid,
                                style = typography.captionMeta.tabularNums(),
                                color = colors.textSecondary
                            )

                            // Kanvas Kode QRIS Presisi Tinggi
                            Box(
                                modifier = Modifier
                                    .size(196.dp)
                                    .clip(JelajahDimens.ShapeThumb)
                                    .background(JelajahColorTokens.LightBgSurface)
                                    .border(1.dp, colors.borderStrong, JelajahDimens.ShapeThumb)
                                    .padding(14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                QrisMatrixCanvas(modifier = Modifier.fillMaxSize())
                            }

                            Text(
                                text = "Total Bayar: ${ContentSeedData.formatRupiah(grandTotal)}",
                                style = typography.heading1.tabularNums(),
                                color = colors.textPrimary
                            )
                            Text(
                                text = "Buka aplikasi m-Banking atau E-Wallet Anda, pilih menu QRIS, lalu scan kode di atas.",
                                style = typography.captionMeta,
                                color = colors.textSecondary
                            )
                        }
                    }
                } else {
                    // Tampilan Nomor Virtual Account / E-Wallet dengan Tombol Salin
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = JelajahDimens.ShapeCard,
                        color = colors.bgElevated,
                        border = BorderStroke(1.dp, colors.borderSubtle)
                    ) {
                        Column(
                            modifier = Modifier.padding(JelajahDimens.Space4),
                            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                        ) {
                            Text(
                                text = if (selectedMethod.category == PaymentMethodCategory.VIRTUAL_ACCOUNT) {
                                    "Nomor Virtual Account Resmi"
                                } else {
                                    "Akun Dompet Digital Terhubung"
                                },
                                style = typography.smallLabel,
                                color = colors.textSecondary
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(JelajahDimens.ShapeThumb)
                                    .background(colors.bgSurface)
                                    .border(1.dp, colors.borderSubtle, JelajahDimens.ShapeThumb)
                                    .padding(horizontal = JelajahDimens.Space3, vertical = JelajahDimens.Space2),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedMethod.accountNumberOrNmid,
                                    style = typography.heading1.tabularNums(),
                                    color = colors.textPrimary
                                )

                                val copyInteraction = remember { MutableInteractionSource() }
                                Box(
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .heightIn(min = JelajahDimens.MinTouchTarget)
                                        .clickable(
                                            interactionSource = copyInteraction,
                                            indication = null,
                                            role = Role.Button,
                                            onClick = {
                                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                                clipboardManager.setText(AnnotatedString(selectedMethod.accountNumberOrNmid))
                                                copiedAccountFeedback = true
                                            }
                                        ),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ContentCopy,
                                            contentDescription = "Salin nomor",
                                            tint = colors.success,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = if (copiedAccountFeedback) "Tersalin" else "Salin",
                                            style = typography.smallLabel,
                                            color = colors.success
                                        )
                                    }
                                }
                            }

                            HorizontalDivider(color = colors.borderSubtle)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Nominal Tagihan",
                                    style = typography.bodyMedium,
                                    color = colors.textSecondary
                                )
                                Text(
                                    text = ContentSeedData.formatRupiah(grandTotal),
                                    style = typography.heading1.tabularNums(),
                                    color = colors.textPrimary
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                ) {
                    CategoryPill(
                        label = "Ubah metode",
                        selected = false,
                        onClick = { currentStep = CheckoutStep.ORDER_AND_METHOD },
                        modifier = Modifier.weight(1f),
                        testTag = "btn_change_payment_method"
                    )
                    ButtonPrimary(
                        text = ContentSeedData.CTA_PAY_NOW,
                        onClick = { currentStep = CheckoutStep.PAYMENT_SUCCESS },
                        modifier = Modifier.weight(1f),
                        testTag = "btn_confirm_checkout"
                    )
                }
            }

            CheckoutStep.PAYMENT_SUCCESS -> {
                // TAHAP 3: BUKTI PEMBAYARAN LUNAS & E-VOUCHER RESMI
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = JelajahDimens.ShapeCard,
                    color = colors.successSurface,
                    border = BorderStroke(1.dp, colors.success.copy(alpha = 0.40f))
                ) {
                    Column(
                        modifier = Modifier.padding(JelajahDimens.Space4),
                        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = colors.success,
                                modifier = Modifier.size(26.dp)
                            )
                            Column {
                                Text(
                                    text = "Pembayaran Berhasil Diverifikasi",
                                    style = typography.heading1,
                                    color = colors.textPrimary
                                )
                                Text(
                                    text = "E-Tiket & Jadwal Luring otomatis tersimpan di perangkat Anda",
                                    style = typography.captionMeta,
                                    color = colors.textSecondary
                                )
                            }
                        }

                        HorizontalDivider(color = colors.borderSubtle)

                        InvoiceLineItem(label = "Kode Booking Resmi", value = bookingCode)
                        InvoiceLineItem(label = "Nomor Invoice", value = invoiceNumber)
                        InvoiceLineItem(label = "Nama Pemesan", value = customerName.ifBlank { "Vanessa Putri" })
                        InvoiceLineItem(label = "Metode Pembayaran", value = selectedMethod.name)
                        InvoiceLineItem(label = "Peserta & Durasi", value = "$paxCount orang • ${tour.durationLabel}")
                        if (includeJeepAddon) {
                            InvoiceLineItem(label = "Transportasi Khusus", value = "Termasuk Armada Jeep 4WD")
                        }
                        InvoiceLineItem(label = "Total Lunas", value = ContentSeedData.formatRupiah(grandTotal))
                    }
                }

                ButtonPrimary(
                    text = ContentSeedData.CTA_VIEW_ETICKET,
                    onClick = {
                        onCompletePayment(
                            ConfirmedBookingRecord(
                                bookingCode = bookingCode,
                                invoiceNumber = invoiceNumber,
                                tourId = tour.id,
                                tourTitle = tour.title,
                                departureDates = tour.departureDatesLabel,
                                customerName = customerName.ifBlank { "Vanessa Putri" },
                                customerPhone = customerPhone.ifBlank { "0812-3456-7890" },
                                paxCount = paxCount,
                                includeJeepAddon = includeJeepAddon,
                                paymentMethodName = selectedMethod.name,
                                adminFee = adminFee,
                                totalAmount = grandTotal,
                                paidAtTimestamp = "Lunas • 09:42 WIB"
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "btn_finish_view_eticket"
                )
            }
        }
    }
}

@Composable
private fun InvoiceLineItem(
    label: String,
    value: String
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = typography.captionMeta,
            color = colors.textSecondary
        )
        Text(
            text = value,
            style = typography.bodyMedium.tabularNums(),
            color = colors.textPrimary
        )
    }
}

@Composable
private fun QrisMatrixCanvas(modifier: Modifier = Modifier) {
    val ink = JelajahColorTokens.LightAccent
    Canvas(modifier = modifier) {
        val grid = 15
        val cell = size.width / grid

        fun drawFinder(startCol: Int, startRow: Int) {
            drawRect(
                color = ink,
                topLeft = Offset(startCol * cell, startRow * cell),
                size = Size(cell * 4, cell * 4)
            )
            drawRect(
                color = JelajahColorTokens.LightBgSurface,
                topLeft = Offset((startCol + 0.7f) * cell, (startRow + 0.7f) * cell),
                size = Size(cell * 2.6f, cell * 2.6f)
            )
            drawRect(
                color = ink,
                topLeft = Offset((startCol + 1.3f) * cell, (startRow + 1.3f) * cell),
                size = Size(cell * 1.4f, cell * 1.4f)
            )
        }

        drawFinder(0, 0)
        drawFinder(grid - 4, 0)
        drawFinder(0, grid - 4)

        for (r in 0 until grid) {
            for (c in 0 until grid) {
                val inTopLeft = r < 5 && c < 5
                val inTopRight = r < 5 && c >= grid - 5
                val inBottomLeft = r >= grid - 5 && c < 5
                if (!inTopLeft && !inTopRight && !inBottomLeft) {
                    if ((r * 7 + c * 11 + r * c) % 3 == 0) {
                        drawRect(
                            color = ink,
                            topLeft = Offset(c * cell, r * cell),
                            size = Size(cell * 0.86f, cell * 0.86f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun TourItineraryPreview() {
    JelajahJatimTheme {
        val dest = com.example.data.ContentSeedData.DESTINATIONS.first()
        val tour = com.example.data.ContentSeedData.getToursForDestination(dest.id).first()
        TourItineraryScreenS03(
            tour = tour,
            isTourSaved = false,
            onToggleSaveTour = {},
            isOfflineVaultSaved = false,
            onSaveToOfflineVault = {},
            isOfflineMode = false,
            savedOfflineCount = 2,
            lastSyncedTimestamp = "10 Min lalu",
            onOpenOfflineVault = {},
            onBackClick = {},
            onConfirmBooking = {}
        )
    }
}

