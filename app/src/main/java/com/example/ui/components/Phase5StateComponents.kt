package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.data.ContentSeedData
import com.example.data.DestinationSeed
import com.example.ui.theme.JelajahDimens
import com.example.ui.theme.JelajahMotion
import com.example.ui.theme.JelajahTheme
import com.example.ui.theme.tabularNums

/**
 * Phase 5 - OfflineBanner (PRD.md 2.2 & content.md Section 3)
 * Menampilkan banner non-intrusif: "Mode Luring Aktif. Jadwal tersimpan aman."
 * serta detail "Sinyal Seluler Terbatas" dan tombol "Buka jadwal offline".
 */
@Composable
fun OfflineBanner(
    savedOfflineCount: Int,
    lastSyncedTimestamp: String,
    onOpenOfflineVault: () -> Unit,
    cachedDestinationsCount: Int = 5,
    cachedImageTotalKb: String = "",
    modifier: Modifier = Modifier
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("offline_banner"),
        shape = JelajahDimens.ShapeCard,
        color = colors.warningSurface,
        border = BorderStroke(1.dp, colors.warning.copy(alpha = 0.28f))
    ) {
        Column(
            modifier = Modifier.padding(JelajahDimens.Space4),
            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)
            ) {
                Icon(
                    imageVector = Icons.Filled.CloudOff,
                    contentDescription = null,
                    tint = colors.warning,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = ContentSeedData.OFFLINE_BANNER_SIMPLE,
                    style = typography.smallLabel,
                    color = colors.warning
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space1)) {
                Text(
                    text = ContentSeedData.OFFLINE_TITLE,
                    style = typography.heading2,
                    color = colors.textPrimary
                )
                Text(
                    text = ContentSeedData.OFFLINE_DESCRIPTION,
                    style = typography.bodyDefault,
                    color = colors.textSecondary
                )
                val sizeSuffix = if (cachedImageTotalKb.isNotBlank()) " ($cachedImageTotalKb)" else ""
                Text(
                    text = "Cache lokal: $cachedDestinationsCount destinasi utama$sizeSuffix & $savedOfflineCount paket tur • Sinkronisasi terakhir $lastSyncedTimestamp",
                    style = typography.captionMeta.tabularNums(),
                    color = colors.textMuted,
                    modifier = Modifier.testTag("offline_banner_cache_meta")
                )
            }

            ButtonPrimary(
                text = ContentSeedData.CTA_OPEN_OFFLINE,
                onClick = onOpenOfflineVault,
                modifier = Modifier.fillMaxWidth(),
                testTag = "btn_open_offline_vault"
            )
        }
    }
}

/**
 * Phase 5 - SkeletonLoader
 * Placeholder abu-abu halus (--bg-elevated) untuk kartu destinasi saat data sedang disinkronkan.
 * Dilarang menggunakan spinner default yang membuat antarmuka terasa lambat.
 */
@Composable
fun SkeletonLoader(
    modifier: Modifier = Modifier
) {
    val colors = JelajahTheme.colors
    val infiniteTransition = rememberInfiniteTransition(label = "SkeletonPulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.55f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = JelajahMotion.EaseStandard),
            repeatMode = RepeatMode.Reverse
        ),
        label = "SkeletonAlpha"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("skeleton_loader")
            .alpha(pulseAlpha),
        verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space4)
    ) {
        // Skeleton Featured Hero Card (4:5 ratio, 28dp radius)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 5f)
                .clip(JelajahDimens.ShapeHero)
                .background(colors.bgElevated)
                .border(1.dp, colors.borderSubtle, JelajahDimens.ShapeHero)
                .padding(JelajahDimens.Space6)
        ) {
            Column(
                modifier = Modifier.align(Alignment.BottomStart),
                verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space3)
            ) {
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(16.dp)
                        .clip(JelajahDimens.ShapeSm)
                        .background(colors.bgSurface)
                )
                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .height(28.dp)
                        .clip(JelajahDimens.ShapeSm)
                        .background(colors.bgSurface)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(JelajahDimens.ButtonPrimaryHeight)
                        .clip(JelajahDimens.ShapePill)
                        .background(colors.bgSurface)
                )
            }
        }
    }
}

/**
 * Phase 5 - EmptyState (PRD.md 2.2 & content.md Section 3)
 * Pencarian Tanpa Hasil: Menampilkan pesan santun ("Destinasi Belum Ditemukan"),
 * tombol "Lihat wisata Jember", dan rekomendasi 3 destinasi terpopuler di Jatim.
 */
@Composable
fun EmptyState(
    searchQuery: String,
    popularDestinations: List<DestinationSeed>,
    onExploreJemberClick: () -> Unit,
    onDestinationClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("empty_state_card"),
        shape = JelajahDimens.ShapeCard,
        color = colors.bgSurface,
        border = BorderStroke(1.dp, colors.borderSubtle)
    ) {
        Column(
            modifier = Modifier.padding(JelajahDimens.Space6),
            verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space4)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(JelajahDimens.ShapeThumb)
                    .background(colors.bgElevated),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.SearchOff,
                    contentDescription = null,
                    tint = colors.textSecondary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)) {
                Text(
                    text = ContentSeedData.EMPTY_SEARCH_TITLE,
                    style = typography.heading1,
                    color = colors.textPrimary
                )
                Text(
                    text = if (searchQuery.isNotBlank()) {
                        "Kata kunci \"$searchQuery\" belum tersedia. ${ContentSeedData.EMPTY_SEARCH_DESCRIPTION}"
                    } else {
                        ContentSeedData.EMPTY_SEARCH_DESCRIPTION
                    },
                    style = typography.bodyDefault,
                    color = colors.textSecondary
                )
            }

            ButtonPrimary(
                text = ContentSeedData.CTA_EXPLORE_JEMBER,
                onClick = onExploreJemberClick,
                modifier = Modifier.fillMaxWidth(),
                testTag = "btn_empty_explore_jember"
            )

            Spacer(modifier = Modifier.height(JelajahDimens.Space1))

            Text(
                text = "Rekomendasi 3 destinasi terpopuler di Jawa Timur",
                style = typography.smallLabel.tabularNums(),
                color = colors.textMuted
            )

            Column(verticalArrangement = Arrangement.spacedBy(JelajahDimens.Space2)) {
                popularDestinations.take(3).forEach { destination ->
                    val interactionSource = remember { MutableInteractionSource() }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("empty_rec_${destination.id}")
                            .minimumInteractiveComponentSize()
                            .heightIn(min = JelajahDimens.MinTouchTarget)
                            .clip(JelajahDimens.ShapeThumb)
                            .background(colors.bgElevated, JelajahDimens.ShapeThumb)
                            .border(1.dp, colors.borderSubtle, JelajahDimens.ShapeThumb)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                role = Role.Button,
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onDestinationClick(destination.id)
                                }
                            )
                            .padding(horizontal = JelajahDimens.Space3, vertical = JelajahDimens.Space2),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = destination.name,
                                style = typography.bodyMedium,
                                color = colors.textPrimary
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Place,
                                    contentDescription = null,
                                    tint = colors.textSecondary,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = destination.locationBadge,
                                    style = typography.captionMeta,
                                    color = colors.textSecondary
                                )
                            }
                        }
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
                                text = "${destination.rating}",
                                style = typography.smallLabel.tabularNums(),
                                color = colors.textPrimary
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = colors.textSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
