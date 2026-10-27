package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.StarBorder
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.ContentSeedData
import com.example.data.DestinationSeed
import com.example.data.NatureStatusTone
import com.example.data.TourPackage
import com.example.ui.components.ButtonPrimary
import com.example.ui.components.DestinationArtwork
import com.example.ui.components.StatusBadge
import com.example.ui.theme.JelajahColorTokens
import com.example.ui.theme.JelajahDimens
import com.example.ui.theme.JelajahMotion
import com.example.ui.theme.JelajahTheme
import com.example.ui.theme.tabularNums

/**
 * Detail Destinasi & Paket Tur Terkait (Screen S-02 Gaya TripGlide)
 * - Foto hero lanskap penuh di atas dengan tombol bulat putih solid (`<` & `♡`).
 * - Lembar bawah putih (`radius = 32dp`) tumpang tindih di atas foto dengan drag handle.
 * - Baris judul besar ("Tanjung Papuma") di kiri & pil rating bergaris tepi (`☆ 5.0`) di kanan.
 * - Baris lokasi dengan titik hijau + `"Jember, Jawa Timur"` di kiri & `"184 ulasan"` bergaris bawah di kanan.
 * - Deskripsi 3 baris dengan tautan `"Baca selengkapnya"` bergaris bawah.
 * - Karusel horizontal `"Paket Tur Populer"` dengan kartu putih, tombol hati bulat putih di atas foto,
 *   dan tombol lingkaran gelap panah kanan (`→`) di pojok kanan bawah kartu.
 */
@Composable
fun DestinationDetailScreenS02(
    destination: DestinationSeed,
    relatedTours: List<TourPackage>,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onBackClick: () -> Unit,
    onSelectTourPackage: (String) -> Unit,
    onSwitchToAlternateDestination: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBackClick)

    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val scrollState = rememberScrollState()

    var isDescriptionExpanded by rememberSaveable(destination.id) { mutableStateOf(false) }
    var simulateHighTideWarning by rememberSaveable(destination.id) { mutableStateOf(false) }
    val photoGallery = remember(destination.id) {
        ContentSeedData.getPhotoGalleryForDestination(destination.id)
    }
    var selectedPhotoIndex by rememberSaveable(destination.id) { mutableStateOf(0) }
    val activePhoto = photoGallery.getOrElse(selectedPhotoIndex) {
        com.example.data.PhotoGalleryItem(destination.sceneType, destination.name)
    }

    val activeNatureStatus = remember(destination.id, simulateHighTideWarning) {
        if (simulateHighTideWarning && destination.id == "dest-jember-001") {
            ContentSeedData.NATURE_STATUS_PAPUMA_HIGH_TIDE
        } else {
            destination.natureStatus
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.bgSurface)
            .testTag("destination_detail_screen_s02"),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 600.dp)
                .verticalScroll(scrollState)
        ) {
            // 1. HeroImageHeader Full-Bleed (Tinggi 336dp dengan tombol bulat putih solid)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(336.dp)
            ) {
                DestinationArtwork(
                    sceneType = activePhoto.sceneType,
                    contentDescription = activePhoto.caption,
                    showBottomScrim = false,
                    modifier = Modifier.fillMaxSize()
                )

                // Top Bar: Tombol Kembali (`<`) & Wishlist (`♡`) Bulat Putih Solid (Persis TripGlide Screen 2)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(
                            horizontal = JelajahDimens.Space5,
                            vertical = JelajahDimens.Space3
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val backInteraction = remember { MutableInteractionSource() }
                    Box(
                        modifier = Modifier
                            .testTag("s02_back_button")
                            .minimumInteractiveComponentSize()
                            .size(JelajahDimens.MinTouchTarget)
                            .clip(CircleShape)
                            .background(JelajahColorTokens.LightBgSurface, CircleShape)
                            .border(1.dp, JelajahColorTokens.LightBorderSubtle, CircleShape)
                            .clickable(
                                interactionSource = backInteraction,
                                indication = null,
                                role = Role.Button,
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onBackClick()
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Kembali ke Beranda",
                            tint = JelajahColorTokens.LightTextPrimary,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    val heartInteraction = remember { MutableInteractionSource() }
                    Box(
                        modifier = Modifier
                            .testTag("s02_wishlist_button")
                            .minimumInteractiveComponentSize()
                            .size(JelajahDimens.MinTouchTarget)
                            .clip(CircleShape)
                            .background(JelajahColorTokens.LightBgSurface, CircleShape)
                            .border(1.dp, JelajahColorTokens.LightBorderSubtle, CircleShape)
                            .clickable(
                                interactionSource = heartInteraction,
                                indication = null,
                                role = Role.Button,
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onToggleSave()
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isSaved) "Hapus dari favorit" else "Simpan ke favorit",
                            tint = if (isSaved) JelajahColorTokens.LightDanger else JelajahColorTokens.LightTextPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // Pemilih Galeri Thumbnail Foto Asli di sudut kanan bawah foto
                if (photoGallery.size > 1) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(
                                end = JelajahDimens.Space5,
                                bottom = 46.dp
                            ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        photoGallery.forEachIndexed { idx, item ->
                            val isSelected = idx == selectedPhotoIndex
                            val thumbInteraction = remember { MutableInteractionSource() }
                            Box(
                                modifier = Modifier
                                    .testTag("s02_photo_thumb_$idx")
                                    .minimumInteractiveComponentSize()
                                    .size(44.dp)
                                    .clip(JelajahDimens.ShapeThumb)
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) {
                                            JelajahColorTokens.LightBgSurface
                                        } else {
                                            JelajahColorTokens.LightBgSurface.copy(alpha = 0.55f)
                                        },
                                        shape = JelajahDimens.ShapeThumb
                                    )
                                    .clickable(
                                        interactionSource = thumbInteraction,
                                        indication = null,
                                        role = Role.Tab,
                                        onClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                            selectedPhotoIndex = idx
                                        }
                                    )
                            ) {
                                DestinationArtwork(
                                    sceneType = item.sceneType,
                                    contentDescription = item.caption,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }

            // 2. DestinationSheet: Lembar putih radius 32dp tumpang tindih 36dp di atas foto
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-36).dp)
                    .testTag("destination_bottom_sheet"),
                shape = JelajahDimens.ShapeSheet,
                color = colors.bgSurface,
                tonalElevation = 2.dp,
                shadowElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(
                            start = JelajahDimens.Space6,
                            end = JelajahDimens.Space6,
                            top = 14.dp,
                            bottom = JelajahDimens.Space6
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Drag Handle Pill
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .width(42.dp)
                                .height(4.dp)
                                .clip(JelajahDimens.ShapePill)
                                .background(colors.borderStrong)
                        )
                    }

                    // Header: Nama Destinasi + Pil Rating Bergaris Tepi (`☆ 5.0`), Lokasi + `"184 ulasan"` Bergaris Bawah
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = destination.name,
                                style = typography.displayHero,
                                color = colors.textPrimary,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(JelajahDimens.Space3))

                            // Pil Rating Bergaris Tepi (`☆ 5.0`) persis referensi TripGlide Screen 2
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                modifier = Modifier
                                    .clip(JelajahDimens.ShapePill)
                                    .background(colors.bgSurface)
                                    .border(1.dp, colors.borderStrong, JelajahDimens.ShapePill)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.StarBorder,
                                    contentDescription = null,
                                    tint = colors.textPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "${destination.rating}",
                                    style = typography.bodyMedium.tabularNums(),
                                    color = colors.textPrimary
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Lingkaran indikator warna hijau alam
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(colors.success, CircleShape)
                                )
                                Text(
                                    text = destination.locationBadge,
                                    style = typography.bodyDefault,
                                    color = colors.textSecondary
                                )
                            }

                            Text(
                                text = "${destination.reviewCount} ulasan",
                                style = typography.smallLabel.tabularNums().copy(
                                    textDecoration = TextDecoration.Underline
                                ),
                                color = colors.textPrimary
                            )
                        }
                    }

                    // Deskripsi Destinasi + Tautan "Baca selengkapnya" Bergaris Bawah (Persis TripGlide Screen 2)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = JelajahMotion.DurBaseMs,
                                    easing = JelajahMotion.EaseStandard
                                )
                            ),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = if (isDescriptionExpanded) {
                                "${destination.description}\n\nMedan lokal: ${destination.terrainType.label}. Waktu kunjungan terbaik: ${destination.recommendedHours}. Seluruh paket tur telah mencakup koordinasi transportasi lokal dan pemandu terverifikasi."
                            } else {
                                destination.description
                            },
                            style = typography.bodyDefault,
                            color = colors.textSecondary,
                            maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        val readMoreInteraction = remember { MutableInteractionSource() }
                        Box(
                            modifier = Modifier
                                .testTag("s02_read_more_toggle")
                                .minimumInteractiveComponentSize()
                                .heightIn(min = 40.dp)
                                .clickable(
                                    interactionSource = readMoreInteraction,
                                    indication = null,
                                    role = Role.Button,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        isDescriptionExpanded = !isDescriptionExpanded
                                    }
                                ),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = if (isDescriptionExpanded) {
                                    ContentSeedData.CTA_HIDE
                                } else {
                                    ContentSeedData.CTA_READ_MORE
                                },
                                style = typography.bodyMedium.copy(
                                    textDecoration = TextDecoration.Underline
                                ),
                                color = colors.textPrimary
                            )
                        }
                    }

                    // Indikator Kondisi Alam & Akses Medan yang Ringkas & Fungsional (F-04)
                    NatureConditionCompactStrip(
                        destination = destination,
                        status = activeNatureStatus,
                        isSimulatingHighTide = simulateHighTideWarning,
                        onToggleHighTideSimulation = {
                            simulateHighTideWarning = !simulateHighTideWarning
                        },
                        onAlternateRouteClick = {
                            val altId = activeNatureStatus.alternateRouteDestinationId
                            if (altId != null && altId != destination.id) {
                                onSwitchToAlternateDestination(altId)
                            } else {
                                simulateHighTideWarning = false
                            }
                        }
                    )

                    // Karusel Horizontal "Paket Tur Populer" (Persis TripGlide Screen 2)
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = ContentSeedData.SECTION_POPULAR_TOURS,
                                style = typography.heading1,
                                color = colors.textPrimary
                            )
                            val seeAllInteraction = remember { MutableInteractionSource() }
                            Box(
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .heightIn(min = JelajahDimens.MinTouchTarget)
                                    .clickable(
                                        interactionSource = seeAllInteraction,
                                        indication = null,
                                        role = Role.Button,
                                        onClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                            onSelectTourPackage(relatedTours.first().id)
                                        }
                                    ),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    text = ContentSeedData.ACTION_SEE_ALL,
                                    style = typography.bodyMedium.copy(
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    color = colors.textPrimary
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .testTag("s02_tour_carousel"),
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            relatedTours.forEach { tour ->
                                TourHorizontalMiniCard(
                                    tour = tour,
                                    onClick = { onSelectTourPackage(tour.id) }
                                )
                            }
                        }

                        // Daftar Paket Ekspedisi & Tur Jawa Timur (Dipindahkan dari Layar Beranda Screen 1)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp)
                                .testTag("s02_all_tour_packages_list"),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            ContentSeedData.TOUR_PACKAGES.forEach { tour ->
                                TourVerticalListCard(
                                    tour = tour,
                                    onClick = { onSelectTourPackage(tour.id) }
                                )
                            }
                        }
                    }

                    // Tombol Aksi Utama Langsung ke Paket Tur Unggulan Destinasi Ini
                    ButtonPrimary(
                        text = "Pilih paket ${relatedTours.first().durationLabel}",
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                        onClick = { onSelectTourPackage(relatedTours.first().id) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "s02_primary_select_tour_cta"
                    )
                }
            }
        }
    }
}

@Composable
private fun TourVerticalListCard(
    tour: TourPackage,
    onClick: () -> Unit
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("s02_tour_list_card_${tour.id}")
            .clip(JelajahDimens.ShapeCard)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            ),
        shape = JelajahDimens.ShapeCard,
        color = colors.bgCanvas,
        tonalElevation = 1.dp,
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
                    .size(72.dp)
                    .clip(JelajahDimens.ShapeThumb)
            ) {
                DestinationArtwork(
                    sceneType = tour.sceneType,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = tour.title,
                    style = typography.heading2,
                    color = colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${tour.durationDays} Hari • mulai ${ContentSeedData.formatRupiah(tour.pricePerPerson)}/orang",
                    style = typography.captionMeta.tabularNums(),
                    color = colors.textSecondary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.StarBorder,
                        contentDescription = null,
                        tint = colors.accent,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${tour.rating}  ${tour.reviewCount} ulasan",
                        style = typography.smallLabel.tabularNums(),
                        color = colors.textPrimary
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(colors.accent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = ContentSeedData.CTA_VIEW_DETAIL,
                    tint = colors.accentFg,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun NatureConditionCompactStrip(
    destination: DestinationSeed,
    status: com.example.data.NatureStatusInfo,
    isSimulatingHighTide: Boolean,
    onToggleHighTideSimulation: () -> Unit,
    onAlternateRouteClick: () -> Unit
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current

    val (surfaceColor, accentColor) = when (status.tone) {
        NatureStatusTone.SAFE -> colors.successSurface to colors.success
        NatureStatusTone.WARNING -> colors.warningSurface to colors.warning
        NatureStatusTone.DANGER -> colors.dangerSurface to colors.danger
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("nature_condition_section")
            .clip(JelajahDimens.ShapeCard)
            .background(surfaceColor)
            .border(1.dp, accentColor.copy(alpha = 0.22f), JelajahDimens.ShapeCard)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusBadge(
                label = status.badgeText,
                tone = status.tone
            )

            if (destination.id == "dest-jember-001") {
                val simInteraction = remember { MutableInteractionSource() }
                Box(
                    modifier = Modifier
                        .testTag("toggle_tide_warning_simulation")
                        .minimumInteractiveComponentSize()
                        .heightIn(min = JelajahDimens.MinTouchTarget)
                        .clickable(
                            interactionSource = simInteraction,
                            indication = null,
                            role = Role.Switch,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onToggleHighTideSimulation()
                            }
                        ),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = if (isSimulatingHighTide) "Ombak Normal" else "Simulasi Pasang 16:30",
                        style = typography.captionMeta.tabularNums().copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        color = accentColor
                    )
                }
            }
        }

        Text(
            text = status.description,
            style = typography.smallLabel.tabularNums(),
            color = colors.textPrimary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Waves,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = status.waveOrElevationMetric,
                    style = typography.captionMeta.tabularNums(),
                    color = colors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.DirectionsCar,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = destination.accessRequirement.shortTag,
                    style = typography.captionMeta,
                    color = colors.textPrimary
                )
            }
        }

        if (status.tone != NatureStatusTone.SAFE && status.alternateRouteLabel != null) {
            val altInteraction = remember { MutableInteractionSource() }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("alternate_route_button")
                    .minimumInteractiveComponentSize()
                    .heightIn(min = JelajahDimens.MinTouchTarget)
                    .clip(JelajahDimens.ShapeThumb)
                    .background(colors.bgSurface, JelajahDimens.ShapeThumb)
                    .border(1.dp, accentColor.copy(alpha = 0.35f), JelajahDimens.ShapeThumb)
                    .clickable(
                        interactionSource = altInteraction,
                        indication = null,
                        role = Role.Button,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onAlternateRouteClick()
                        }
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Terrain,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = status.alternateRouteLabel,
                        style = typography.smallLabel,
                        color = colors.textPrimary
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = colors.textPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/**
 * Kartu Tur Horizontal (Persis TripGlide Screen 2 "Upcoming tours"):
 * - Permukaan putih (--bg-surface) atau --bg-elevated dengan sudut membulat 20dp.
 * - Foto di bagian atas (tinggi 132dp, radius 14dp) dengan tombol hati bulat putih di pojok kanan atas.
 * - Di bawah foto: Judul tur, `"3 Hari • mulai Rp 1.450.000/orang"`, rating `☆ 4.9  142 ulasan` di kiri bawah,
 *   dan tombol lingkaran gelap (`#16191D`, 42dp) dengan panah putih `→` di kanan bawah!
 */
@Composable
private fun TourHorizontalMiniCard(
    tour: TourPackage,
    onClick: () -> Unit
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    var isLiked by rememberSaveable(tour.id) { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .width(248.dp)
            .testTag("tour_mini_card_${tour.id}")
            .clip(JelajahDimens.ShapeCard)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            ),
        shape = JelajahDimens.ShapeCard,
        color = colors.bgCanvas,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, colors.borderSubtle)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(136.dp)
                    .clip(JelajahDimens.ShapeThumb)
            ) {
                DestinationArtwork(
                    sceneType = tour.sceneType,
                    modifier = Modifier.fillMaxSize()
                )

                // Tombol Hati Bulat Putih di Pojok Kanan Atas Foto (Persis TripGlide Screen 2)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(JelajahColorTokens.LightBgSurface, CircleShape)
                        .clickable { isLiked = !isLiked },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Simpan paket tur",
                        tint = if (isLiked) JelajahColorTokens.LightDanger else JelajahColorTokens.LightTextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = tour.title,
                        style = typography.heading2,
                        color = colors.textPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${tour.durationDays} Hari • mulai ${ContentSeedData.formatRupiah(tour.pricePerPerson)}/orang",
                        style = typography.captionMeta.tabularNums(),
                        color = colors.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.StarBorder,
                            contentDescription = null,
                            tint = colors.textPrimary,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "${tour.rating}",
                            style = typography.smallLabel.tabularNums(),
                            color = colors.textPrimary
                        )
                        Text(
                            text = "${tour.reviewCount} ulasan",
                            style = typography.captionMeta.tabularNums(),
                            color = colors.textSecondary
                        )
                    }
                }

                // Tombol Lingkaran Gelap (`#16191D`, 44dp) dengan Panah Putih `→` di Kanan Bawah
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(colors.accent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Buka rincian tur",
                        tint = colors.accentFg,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
