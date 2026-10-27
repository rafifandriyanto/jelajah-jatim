package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.ContentSeedData
import com.example.data.DestinationSeed
import com.example.data.NatureStatusTone
import com.example.data.PaymentMethodCategory
import com.example.data.TourPackage
import com.example.ui.components.ButtonPrimary
import com.example.ui.components.CategoryPill
import com.example.ui.components.DestinationArtwork
import com.example.ui.components.StatusBadge
import com.example.ui.components.UserAvatarImage
import com.example.ui.theme.JelajahDimens
import com.example.ui.theme.JelajahTheme
import com.example.ui.theme.tabularNums

/**
 * Tab 2: Layar Tersimpan (Wishlist Destinasi & Paket Tur)
 */
@Composable
fun SavedWishlistScreen(
    savedDestinations: List<DestinationSeed>,
    savedTours: List<TourPackage>,
    onRemoveDestination: (String) -> Unit,
    onOpenDestination: (String) -> Unit,
    onOpenTour: (String) -> Unit,
    onExploreHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .widthIn(max = 600.dp)
            .testTag("saved_wishlist_screen"),
        contentPadding = PaddingValues(
            start = JelajahDimens.Space5,
            end = JelajahDimens.Space5,
            top = JelajahDimens.Space4,
            bottom = 112.dp
        ),
        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space4)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space1)) {
                Text(
                    text = "Destinasi & Tur Tersimpan",
                    style = typography.displayHero,
                    color = colors.textPrimary
                )
                Text(
                    text = "${savedDestinations.size} destinasi • ${savedTours.size} paket tur dalam daftar favorit Anda",
                    style = typography.bodyDefault.tabularNums(),
                    color = colors.textSecondary
                )
            }
        }

        if (savedDestinations.isEmpty() && savedTours.isEmpty()) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = JelajahDimens.ShapeCard,
                    color = colors.bgSurface,
                    tonalElevation = 2.dp,
                    shadowElevation = 2.dp,
                    border = BorderStroke(1.dp, colors.borderSubtle)
                ) {
                    Column(
                        modifier = Modifier.padding(JelajahDimens.Space6),
                        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Bookmark,
                            contentDescription = null,
                            tint = colors.textMuted,
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = "Belum ada destinasi tersimpan",
                            style = typography.heading1,
                            color = colors.textPrimary
                        )
                        Text(
                            text = "Ketuk ikon hati pada kartu Tanjung Papuma, Meru Betiri, atau Kawah Ijen untuk menyimpan rencana perjalanan akhir pekan Anda.",
                            style = typography.bodyDefault,
                            color = colors.textSecondary
                        )
                        ButtonPrimary(
                            text = ContentSeedData.CTA_EXPLORE_JEMBER,
                            onClick = onExploreHome,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        } else {
            if (savedDestinations.isNotEmpty()) {
                item {
                    Text(
                        text = "Destinasi Favorit",
                        style = typography.heading2,
                        color = colors.textPrimary
                    )
                }

                items(
                    items = savedDestinations,
                    key = { it.id }
                ) { dest ->
                    val interaction = remember { MutableInteractionSource() }
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(JelajahDimens.ShapeCard)
                            .clickable(
                                interactionSource = interaction,
                                indication = null,
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onOpenDestination(dest.id)
                                }
                            ),
                        shape = JelajahDimens.ShapeCard,
                        color = colors.bgSurface,
                        tonalElevation = 2.dp,
                        shadowElevation = 2.dp,
                        border = BorderStroke(1.dp, colors.borderSubtle)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(84.dp)
                                    .clip(JelajahDimens.ShapeThumb)
                            ) {
                                DestinationArtwork(
                                    sceneType = dest.sceneType,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                StatusBadge(
                                    label = dest.natureStatus.badgeText,
                                    tone = dest.natureStatus.tone
                                )
                                Text(
                                    text = dest.name,
                                    style = typography.heading2,
                                    color = colors.textPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
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

                            val removeInteraction = remember { MutableInteractionSource() }
                            Box(
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .size(JelajahDimens.MinTouchTarget)
                                    .clickable(
                                        interactionSource = removeInteraction,
                                        indication = null,
                                        role = Role.Button,
                                        onClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                            onRemoveDestination(dest.id)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DeleteOutline,
                                    contentDescription = "Hapus dari tersimpan",
                                    tint = colors.textSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (savedTours.isNotEmpty()) {
                item {
                    Text(
                        text = "Paket Tur Tersimpan",
                        style = typography.heading2,
                        color = colors.textPrimary
                    )
                }

                items(
                    items = savedTours,
                    key = { it.id }
                ) { tour ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = JelajahDimens.ShapeCard,
                        color = colors.bgSurface,
                        tonalElevation = 2.dp,
                        shadowElevation = 2.dp,
                        border = BorderStroke(1.dp, colors.borderSubtle)
                    ) {
                        Column(
                            modifier = Modifier.padding(JelajahDimens.Space4),
                            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StatusBadge(
                                    label = tour.durationLabel,
                                    tone = NatureStatusTone.SAFE
                                )
                                Text(
                                    text = "${ContentSeedData.formatRupiah(tour.pricePerPerson)} / orang",
                                    style = typography.heading2.tabularNums(),
                                    color = colors.textPrimary
                                )
                            }
                            Text(
                                text = tour.title,
                                style = typography.heading1,
                                color = colors.textPrimary
                            )
                            ButtonPrimary(
                                text = ContentSeedData.CTA_VIEW_DETAIL,
                                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                                onClick = { onOpenTour(tour.id) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Tab 3: Layar Perjalanan (Offline Vault F-06 & Pesanan Terkonfirmasi)
 */
@Composable
fun TripsAndOfflineVaultScreen(
    offlineSavedTours: List<TourPackage>,
    confirmedBookings: List<ConfirmedBookingRecord>,
    lastSyncedTimestamp: String,
    onOpenTourItinerary: (String) -> Unit,
    cachedTopDestinations: List<com.example.data.CachedDestinationEntity> = emptyList(),
    totalCachedBytes: Long = 0L,
    onOpenCachedDestination: (String) -> Unit = {},
    onResyncOfflineCache: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .widthIn(max = 600.dp)
            .testTag("trips_offline_vault_screen"),
        contentPadding = PaddingValues(
            start = JelajahDimens.Space5,
            end = JelajahDimens.Space5,
            top = JelajahDimens.Space4,
            bottom = 112.dp
        ),
        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space4)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space1)) {
                StatusBadge(
                    label = "Mode Luring Siap • Sinkronisasi $lastSyncedTimestamp",
                    tone = NatureStatusTone.SAFE,
                    leadingIcon = Icons.Filled.CloudDone
                )
                Spacer(modifier = Modifier.height(JelajahDimens.Space1))
                Text(
                    text = "Perjalanan & Jadwal Offline",
                    style = typography.displayHero,
                    color = colors.textPrimary
                )
                Text(
                    text = "Akses 5 destinasi utama tersimpan di perangkat, voucher pesanan, dan kontak darurat lokal tanpa koneksi internet.",
                    style = typography.bodyDefault,
                    color = colors.textSecondary
                )
            }
        }

        // Section Cache Luring 5 Destinasi Wisata Utama (Room SQLite + File Gambar Lokal)
        if (cachedTopDestinations.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("offline_top5_destinations_section"),
                    verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "Cache Luring 5 Destinasi Utama",
                                style = typography.heading1.tabularNums(),
                                color = colors.textPrimary
                            )
                            Text(
                                text = "${cachedTopDestinations.size} destinasi & foto tersimpan (${com.example.data.OfflineDestinationRepository.formatCacheSizeKb(totalCachedBytes)})",
                                style = typography.captionMeta.tabularNums(),
                                color = colors.textSecondary
                            )
                        }

                        CategoryPill(
                            label = "Segarkan cache",
                            selected = false,
                            onClick = onResyncOfflineCache,
                            testTag = "btn_resync_top5_cache"
                        )
                    }

                    cachedTopDestinations.forEach { entity ->
                        val seed = remember(entity) { entity.toDestinationSeed() }
                        val itemInteraction = remember { MutableInteractionSource() }
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("offline_cached_dest_${entity.id}")
                                .clip(JelajahDimens.ShapeCard)
                                .clickable(
                                    interactionSource = itemInteraction,
                                    indication = null,
                                    role = Role.Button,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        onOpenCachedDestination(entity.id)
                                    }
                                ),
                            shape = JelajahDimens.ShapeCard,
                            color = colors.bgSurface,
                            tonalElevation = 2.dp,
                            shadowElevation = 2.dp,
                            border = BorderStroke(1.dp, colors.borderSubtle)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(JelajahDimens.ShapeThumb)
                                ) {
                                    DestinationArtwork(
                                        sceneType = seed.sceneType,
                                        contentDescription = seed.name,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = "#${entity.rankOrder} • ${seed.locationBadge}",
                                            style = typography.captionMeta.tabularNums(),
                                            color = colors.textSecondary
                                        )
                                        Text(
                                            text = "• ${com.example.data.OfflineDestinationRepository.formatCacheSizeKb(entity.cachedImageSizeBytes)}",
                                            style = typography.captionMeta.tabularNums(),
                                            color = colors.success
                                        )
                                    }
                                    Text(
                                        text = seed.name,
                                        style = typography.heading2,
                                        color = colors.textPrimary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            contentDescription = null,
                                            tint = colors.warning,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "${seed.rating} (${seed.reviewCount} ulasan)",
                                            style = typography.smallLabel.tabularNums(),
                                            color = colors.textPrimary
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(colors.bgElevated, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = ContentSeedData.CTA_VIEW_DETAIL,
                                        tint = colors.textPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (confirmedBookings.isNotEmpty()) {
            item {
                Text(
                    text = "Voucher Pesanan Aktif",
                    style = typography.heading1,
                    color = colors.textPrimary
                )
            }

            items(
                items = confirmedBookings,
                key = { it.bookingCode }
            ) { booking ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("confirmed_booking_${booking.bookingCode}"),
                    shape = JelajahDimens.ShapeCard,
                    color = colors.bgSurface,
                    tonalElevation = 2.dp,
                    shadowElevation = 3.dp,
                    border = BorderStroke(1.dp, colors.success.copy(alpha = 0.40f))
                ) {
                    Column(
                        modifier = Modifier.padding(JelajahDimens.Space4),
                        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StatusBadge(
                                label = "LUNAS • ${booking.bookingCode}",
                                tone = NatureStatusTone.SAFE
                            )
                            Text(
                                text = "${booking.paxCount} peserta • ${booking.paidAtTimestamp}",
                                style = typography.captionMeta.tabularNums(),
                                color = colors.textSecondary
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = booking.tourTitle,
                                style = typography.heading1,
                                color = colors.textPrimary
                            )
                            Text(
                                text = "${booking.departureDates} • Pemesan: ${booking.customerName}",
                                style = typography.bodyDefault.tabularNums(),
                                color = colors.textSecondary
                            )
                            Text(
                                text = "Metode: ${booking.paymentMethodName} (${booking.invoiceNumber})",
                                style = typography.captionMeta.tabularNums(),
                                color = colors.textMuted
                            )
                        }

                        if (booking.includeJeepAddon) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DirectionsCar,
                                    contentDescription = null,
                                    tint = colors.success,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Termasuk Armada Jeep 4WD Terverifikasi",
                                    style = typography.smallLabel,
                                    color = colors.success
                                )
                            }
                        }

                        HorizontalDivider(color = colors.borderSubtle)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Total Terkonfirmasi",
                                    style = typography.captionMeta,
                                    color = colors.textMuted
                                )
                                Text(
                                    text = ContentSeedData.formatRupiah(booking.totalAmount),
                                    style = typography.heading2.tabularNums(),
                                    color = colors.textPrimary
                                )
                            }

                            ButtonPrimary(
                                text = ContentSeedData.CTA_CONTACT_MITRA,
                                leadingIcon = Icons.Filled.Phone,
                                onClick = {
                                    val message = Uri.encode(
                                        "Halo Mitra Resmi Jelajah Jatim, saya mengonfirmasi pesanan ${booking.bookingCode} (${booking.tourTitle})."
                                    )
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://wa.me/6281234567890?text=$message")
                                    )
                                    runCatching { context.startActivity(intent) }
                                }
                            )
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Offline Vault (${offlineSavedTours.size} paket tersimpan)",
                style = typography.heading1.tabularNums(),
                color = colors.textPrimary
            )
        }

        items(
            items = offlineSavedTours,
            key = { it.id }
        ) { tour ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("offline_vault_item_${tour.id}"),
                shape = JelajahDimens.ShapeCard,
                color = colors.bgSurface,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp,
                border = BorderStroke(1.dp, colors.borderSubtle)
            ) {
                Column(
                    modifier = Modifier.padding(JelajahDimens.Space4),
                    verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatusBadge(
                            label = "Tersimpan Luring • ${tour.durationLabel}",
                            tone = NatureStatusTone.SAFE
                        )
                        Text(
                            text = tour.departureDatesLabel,
                            style = typography.captionMeta.tabularNums(),
                            color = colors.textSecondary
                        )
                    }

                    Text(
                        text = tour.title,
                        style = typography.heading1,
                        color = colors.textPrimary
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(JelajahDimens.ShapeThumb)
                            .background(colors.bgElevated)
                            .padding(JelajahDimens.Space3),
                        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Phone,
                                contentDescription = null,
                                tint = colors.success,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = "${tour.emergencyContactName}: ${tour.emergencyContactNumber}",
                                style = typography.smallLabel.tabularNums(),
                                color = colors.textPrimary
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Map,
                                contentDescription = null,
                                tint = colors.textSecondary,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = tour.gpsCoordinates,
                                style = typography.captionMeta.tabularNums(),
                                color = colors.textSecondary
                            )
                        }
                    }

                    ButtonPrimary(
                        text = ContentSeedData.CTA_OPEN_OFFLINE,
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                        onClick = { onOpenTourItinerary(tour.id) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "vault_open_tour_${tour.id}"
                    )
                }
            }
        }
    }
}

/**
 * Tab 4 / Profil Pengguna: Layar Profil Pengguna (User Profile), Metode Pembayaran Utama,
 * Simulasi Kondisi Lapangan (Mode Luring & Tema), serta Quality Gate Audit.
 */
@Composable
fun MenuAndQualityGateScreen(
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    isOfflineMode: Boolean,
    onToggleOfflineMode: () -> Unit,
    isSimulatingSkeleton: Boolean,
    onToggleSkeletonSimulation: () -> Unit,
    onOpenPhase0And1Showcase: () -> Unit,
    savedDestinationsCount: Int = 1,
    offlineVaultCount: Int = 2,
    confirmedBookingsCount: Int = 0,
    userFullName: String = "Vanessa Putri",
    onUpdateUserFullName: (String) -> Unit = {},
    selectedThemePreset: com.example.ui.theme.JelajahThemePreset = com.example.ui.theme.JelajahThemePreset.MONO_EDITORIAL,
    onSelectThemePreset: (com.example.ui.theme.JelajahThemePreset) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current

    var isEditingProfile by rememberSaveable { mutableStateOf(false) }
    var draftName by rememberSaveable(userFullName) { mutableStateOf(userFullName) }
    var userEmail by rememberSaveable { mutableStateOf("vanessa.putri@jelajahjatim.id") }
    var userPhone by rememberSaveable { mutableStateOf("0812-3456-7890") }
    var userCity by rememberSaveable { mutableStateOf("Surabaya, Jawa Timur") }
    var emergencyContact by rememberSaveable { mutableStateOf("Budi Santoso (Keluarga) • 0811-3400-9988") }
    var preferredPaymentId by rememberSaveable { mutableStateOf(ContentSeedData.PAYMENT_METHODS.first().id) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .widthIn(max = 600.dp)
            .testTag("menu_quality_gate_screen"),
        contentPadding = PaddingValues(
            start = JelajahDimens.Space5,
            end = JelajahDimens.Space5,
            top = JelajahDimens.Space4,
            bottom = 112.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Kartu Profil Utama Pengguna (Vanessa Putri)
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("user_profile_header_card"),
                shape = JelajahDimens.ShapeCard,
                color = colors.bgSurface,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp,
                border = BorderStroke(1.dp, colors.borderSubtle)
            ) {
                Column(
                    modifier = Modifier.padding(JelajahDimens.Space5),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        UserAvatarImage(
                            modifier = Modifier.size(68.dp),
                            contentDescription = "Foto profil $userFullName"
                        )

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            StatusBadge(
                                label = "Penjelajah Terverifikasi",
                                tone = NatureStatusTone.SAFE,
                                leadingIcon = Icons.Filled.Verified
                            )
                            Text(
                                text = userFullName,
                                style = typography.displayHero,
                                color = colors.textPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "$userCity • $userPhone",
                                style = typography.smallLabel.tabularNums(),
                                color = colors.textSecondary
                            )
                        }

                        val editInteraction = remember { MutableInteractionSource() }
                        Box(
                            modifier = Modifier
                                .testTag("btn_edit_user_profile")
                                .minimumInteractiveComponentSize()
                                .size(JelajahDimens.MinTouchTarget)
                                .clip(CircleShape)
                                .background(colors.bgElevated, CircleShape)
                                .border(1.dp, colors.borderSubtle, CircleShape)
                                .clickable(
                                    interactionSource = editInteraction,
                                    indication = null,
                                    role = Role.Button,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        isEditingProfile = !isEditingProfile
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit profil pengguna",
                                tint = colors.textPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Statistik Perjalanan Pengguna (Tabular-Nums)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ProfileStatBox(
                            value = "14",
                            label = "Destinasi Jatim",
                            modifier = Modifier.weight(1f)
                        )
                        ProfileStatBox(
                            value = "$savedDestinationsCount",
                            label = "Favorit",
                            modifier = Modifier.weight(1f)
                        )
                        ProfileStatBox(
                            value = "$offlineVaultCount",
                            label = "Jadwal Offline",
                            modifier = Modifier.weight(1f)
                        )
                        ProfileStatBox(
                            value = "$confirmedBookingsCount",
                            label = "E-Tiket Aktif",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Form Edit Profil Lengkap (Muncul saat tombol Edit diketuk)
                    if (isEditingProfile) {
                        HorizontalDivider(color = colors.borderSubtle)

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Ubah Informasi Profil & Kontak Darurat",
                                style = typography.heading2,
                                color = colors.textPrimary
                            )

                            ProfileTextFieldRow(
                                label = "Nama lengkap",
                                value = draftName,
                                onValueChange = { draftName = it },
                                icon = Icons.Filled.Person,
                                testTag = "input_profile_name"
                            )
                            ProfileTextFieldRow(
                                label = "Email e-tiket",
                                value = userEmail,
                                onValueChange = { userEmail = it },
                                icon = Icons.Filled.Email,
                                testTag = "input_profile_email"
                            )
                            ProfileTextFieldRow(
                                label = "Nomor WhatsApp",
                                value = userPhone,
                                onValueChange = { userPhone = it },
                                icon = Icons.Filled.Phone,
                                testTag = "input_profile_phone"
                            )
                            ProfileTextFieldRow(
                                label = "Kota domisili",
                                value = userCity,
                                onValueChange = { userCity = it },
                                icon = Icons.Filled.Place,
                                testTag = "input_profile_city"
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                CategoryPill(
                                    label = "Batal",
                                    selected = false,
                                    onClick = {
                                        draftName = userFullName
                                        isEditingProfile = false
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                ButtonPrimary(
                                    text = "Simpan profil",
                                    onClick = {
                                        if (draftName.isNotBlank()) {
                                            onUpdateUserFullName(draftName.trim())
                                        }
                                        isEditingProfile = false
                                    },
                                    modifier = Modifier.weight(1f),
                                    testTag = "btn_save_user_profile"
                                )
                            }
                        }
                    } else {
                        // Ringkasan Kontak & Keamanan Pendakian/Pesisir
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(JelajahDimens.ShapeThumb)
                                .background(colors.bgCanvas)
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Email,
                                    contentDescription = null,
                                    tint = colors.textSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = userEmail,
                                    style = typography.smallLabel,
                                    color = colors.textPrimary
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Phone,
                                    contentDescription = null,
                                    tint = colors.success,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Kontak Darurat: $emergencyContact",
                                    style = typography.captionMeta.tabularNums(),
                                    color = colors.textSecondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Pemilih Tema Lanskap Jawa Timur (Interaktif 5 Preset)
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_theme_preset_card"),
                shape = JelajahDimens.ShapeCard,
                color = colors.bgSurface,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp,
                border = BorderStroke(1.dp, colors.borderSubtle)
            ) {
                Column(
                    modifier = Modifier.padding(JelajahDimens.Space4),
                    verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "Tema Lanskap Jawa Timur",
                                style = typography.heading1,
                                color = colors.textPrimary
                            )
                            Text(
                                text = "Pilih palet warna yang terinspirasi dari bentang alam Jawa Timur.",
                                style = typography.captionMeta,
                                color = colors.textSecondary
                            )
                        }
                    }

                    com.example.ui.theme.JelajahThemePreset.entries.forEach { preset ->
                        val isSelected = preset == selectedThemePreset
                        val presetInteraction = remember { MutableInteractionSource() }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("theme_preset_option_${preset.id}")
                                .minimumInteractiveComponentSize()
                                .clip(JelajahDimens.ShapeThumb)
                                .background(
                                    if (isSelected) colors.bgElevated else colors.bgCanvas,
                                    JelajahDimens.ShapeThumb
                                )
                                .border(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = if (isSelected) colors.accent else colors.borderSubtle,
                                    shape = JelajahDimens.ShapeThumb
                                )
                                .clickable(
                                    interactionSource = presetInteraction,
                                    indication = null,
                                    role = Role.RadioButton,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        onSelectThemePreset(preset)
                                    }
                                )
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Swatch Palet 3 Warna (Canvas, Surface, Accent)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy((-6).dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(preset.colors.bgCanvas, CircleShape)
                                        .border(1.dp, preset.colors.borderStrong, CircleShape)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(preset.colors.accent, CircleShape)
                                        .border(1.5.dp, preset.colors.bgSurface, CircleShape)
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = preset.fullName,
                                    style = typography.bodyMedium,
                                    color = colors.textPrimary
                                )
                                Text(
                                    text = preset.landscapeInspiration,
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
                                tint = if (isSelected) colors.accent else colors.textMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // 3. Metode Pembayaran Utama Tersimpan
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = JelajahDimens.ShapeCard,
                color = colors.bgSurface,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp,
                border = BorderStroke(1.dp, colors.borderSubtle)
            ) {
                Column(
                    modifier = Modifier.padding(JelajahDimens.Space4),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Metode Pembayaran Utama",
                        style = typography.heading1,
                        color = colors.textPrimary
                    )
                    Text(
                        text = "Digunakan secara otomatis saat memesan paket tur & shuttle Jeep 4WD.",
                        style = typography.captionMeta,
                        color = colors.textSecondary
                    )

                    ContentSeedData.PAYMENT_METHODS.take(3).forEach { method ->
                        val isSelected = method.id == preferredPaymentId
                        val interaction = remember { MutableInteractionSource() }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .minimumInteractiveComponentSize()
                                .clip(JelajahDimens.ShapeThumb)
                                .background(
                                    if (isSelected) colors.successSurface else colors.bgCanvas,
                                    JelajahDimens.ShapeThumb
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) colors.success.copy(alpha = 0.4f) else colors.borderSubtle,
                                    shape = JelajahDimens.ShapeThumb
                                )
                                .clickable(
                                    interactionSource = interaction,
                                    indication = null,
                                    role = Role.RadioButton,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        preferredPaymentId = method.id
                                    }
                                )
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = when (method.category) {
                                    PaymentMethodCategory.QRIS -> Icons.Filled.QrCode2
                                    PaymentMethodCategory.VIRTUAL_ACCOUNT -> Icons.Filled.AccountBalance
                                    PaymentMethodCategory.EWALLET -> Icons.Filled.AccountBalanceWallet
                                },
                                contentDescription = null,
                                tint = if (isSelected) colors.success else colors.textPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = method.name,
                                    style = typography.bodyMedium,
                                    color = colors.textPrimary
                                )
                                Text(
                                    text = method.badgeText,
                                    style = typography.captionMeta.tabularNums(),
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
        }

        // 4. Kontrol Simulasi State Lapangan & Tema
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = JelajahDimens.ShapeCard,
                color = colors.bgSurface,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp,
                border = BorderStroke(1.dp, colors.borderSubtle)
            ) {
                Column(
                    modifier = Modifier.padding(JelajahDimens.Space4),
                    verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
                ) {
                    Text(
                        text = "Pengaturan Aplikasi & Simulasi Lapangan",
                        style = typography.heading1,
                        color = colors.textPrimary
                    )

                    MenuToggleRow(
                        title = "Mode Luring (Blank Spot Meru Betiri / Ijen)",
                        subtitle = if (isOfflineMode) {
                            "Aktif — Menampilkan OfflineBanner & mengakses cache lokal"
                        } else {
                            "Non-aktif — Terhubung ke jaringan seluler"
                        },
                        icon = if (isOfflineMode) Icons.Filled.CloudOff else Icons.Filled.CloudDone,
                        active = isOfflineMode,
                        onClick = onToggleOfflineMode,
                        testTag = "menu_toggle_offline"
                    )

                    HorizontalDivider(color = colors.borderSubtle)

                    MenuToggleRow(
                        title = "Tema Gelap / Terang",
                        subtitle = if (isDarkTheme) {
                            "Mode Gelap Aktif (${selectedThemePreset.fullName})"
                        } else {
                            "Mode Terang Aktif (${selectedThemePreset.fullName})"
                        },
                        icon = if (isDarkTheme) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                        active = isDarkTheme,
                        onClick = onToggleDarkTheme,
                        testTag = "menu_toggle_theme"
                    )

                    HorizontalDivider(color = colors.borderSubtle)

                    MenuToggleRow(
                        title = "Simulasi SkeletonLoader Beranda",
                        subtitle = if (isSimulatingSkeleton) {
                            "Aktif — Menampilkan placeholder kartu"
                        } else {
                            "Non-aktif — Menampilkan kartu destinasi penuh"
                        },
                        icon = Icons.Filled.HourglassEmpty,
                        active = isSimulatingSkeleton,
                        onClick = onToggleSkeletonSimulation,
                        testTag = "menu_toggle_skeleton"
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    ButtonPrimary(
                        text = "Buka showcase komponen Phase 0 & 1",
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                        onClick = onOpenPhase0And1Showcase,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "btn_open_phase01_showcase"
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileStatBox(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography

    Column(
        modifier = modifier
            .clip(JelajahDimens.ShapeThumb)
            .background(colors.bgCanvas)
            .border(1.dp, colors.borderSubtle, JelajahDimens.ShapeThumb)
            .padding(vertical = 10.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = value,
            style = typography.heading1.tabularNums(),
            color = colors.textPrimary
        )
        Text(
            text = label,
            style = typography.captionMeta,
            color = colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ProfileTextFieldRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    testTag: String
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = typography.captionMeta,
            color = colors.textSecondary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(JelajahDimens.MinTouchTarget)
                .clip(JelajahDimens.ShapeThumb)
                .background(colors.bgCanvas, JelajahDimens.ShapeThumb)
                .border(1.dp, colors.borderSubtle, JelajahDimens.ShapeThumb)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.textSecondary,
                modifier = Modifier.size(18.dp)
            )
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = typography.bodyMedium.tabularNums().copy(color = colors.textPrimary),
                cursorBrush = SolidColor(colors.borderFocus),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(testTag)
            )
        }
    }
}

@Composable
private fun MenuToggleRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    active: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
            .minimumInteractiveComponentSize()
            .heightIn(min = JelajahDimens.MinTouchTarget)
            .clip(JelajahDimens.ShapeThumb)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Switch,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            )
            .padding(vertical = JelajahDimens.Space1),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (active) colors.successSurface else colors.bgElevated,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (active) colors.success else colors.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = title,
                    style = typography.bodyMedium,
                    color = colors.textPrimary
                )
                Text(
                    text = subtitle,
                    style = typography.captionMeta.tabularNums(),
                    color = colors.textSecondary
                )
            }
        }

        StatusBadge(
            label = if (active) "Aktif" else "Ubah",
            tone = if (active) NatureStatusTone.SAFE else NatureStatusTone.WARNING
        )
    }
}
