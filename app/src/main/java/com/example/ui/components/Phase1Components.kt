package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.NatureStatusTone
import com.example.ui.theme.JelajahDimens
import com.example.ui.theme.JelajahMotion
import com.example.ui.theme.JelajahTheme

/**
 * 5.4 ButtonPrimary - Tombol CTA Primer ("Pesan sekarang" / "Lihat detail")
 * - Tinggi: >= 48dp pada mobile untuk sentuhan ergonomis (>= 44x44 pt).
 * - Radius: 9999px (kapsul penuh / CircleShape).
 * - Status Normal: latar --accent (#16191D), teks --accent-fg (#FFFFFF).
 * - Status Ditekan (Pressed): latar --accent-hover (#2C3138), tanpa bounce scale.
 */
@Composable
fun ButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    testTag: String = "button_primary"
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val targetContainerColor = when {
        !enabled -> colors.bgElevated
        isPressed -> colors.accentHover
        else -> colors.accent
    }
    val targetContentColor = when {
        !enabled -> colors.textMuted
        else -> colors.accentFg
    }

    val containerColor by animateColorAsState(
        targetValue = targetContainerColor,
        animationSpec = tween(
            durationMillis = JelajahMotion.DurFastMs,
            easing = JelajahMotion.EaseStandard
        ),
        label = "ButtonPrimaryBg"
    )
    val contentColor by animateColorAsState(
        targetValue = targetContentColor,
        animationSpec = tween(
            durationMillis = JelajahMotion.DurFastMs,
            easing = JelajahMotion.EaseStandard
        ),
        label = "ButtonPrimaryFg"
    )

    Box(
        modifier = modifier
            .testTag(testTag)
            .minimumInteractiveComponentSize()
            .defaultMinSize(
                minWidth = JelajahDimens.MinTouchTarget,
                minHeight = JelajahDimens.ButtonPrimaryHeight
            )
            .height(52.dp)
            .clip(JelajahDimens.ShapePill)
            .background(containerColor, JelajahDimens.ShapePill)
            .then(
                if (!enabled) {
                    Modifier.border(
                        width = 1.dp,
                        color = colors.borderSubtle,
                        shape = JelajahDimens.ShapePill
                    )
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            )
            .padding(horizontal = JelajahDimens.Space6),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(JelajahDimens.Space2))
            }
            Text(
                text = text,
                style = typography.bodyMedium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(JelajahDimens.Space2))
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

/**
 * 5.2 CategoryPill - Pill Filter Kategori Wilayah (Gaya TripGlide)
 * - Bentuk pil penuh (tinggi visual 38dp, padding horizontal 18dp).
 * - Area sentuh (touch target) wajib >= 48dp x 48dp.
 * - Status Aktif: latar --accent (#16191D), teks --accent-fg (#FFFFFF).
 * - Status Non-Aktif: latar --bg-surface (#FFFFFF), garis batas 1dp --border-subtle, teks --text-primary.
 */
@Composable
fun CategoryPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String = "category_pill_$label"
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val targetBg = when {
        !enabled -> colors.bgElevated
        selected && isPressed -> colors.accentHover
        selected -> colors.accent
        isPressed -> colors.bgElevated
        else -> colors.bgSurface
    }
    val targetText = when {
        !enabled -> colors.textMuted
        selected -> colors.accentFg
        else -> colors.textPrimary
    }
    val targetBorder = when {
        selected -> Color.Transparent
        isPressed -> colors.borderStrong
        else -> colors.borderSubtle
    }

    val backgroundColor by animateColorAsState(
        targetValue = targetBg,
        animationSpec = tween(
            durationMillis = JelajahMotion.DurFastMs,
            easing = JelajahMotion.EaseStandard
        ),
        label = "CategoryPillBg"
    )
    val textColor by animateColorAsState(
        targetValue = targetText,
        animationSpec = tween(
            durationMillis = JelajahMotion.DurFastMs,
            easing = JelajahMotion.EaseStandard
        ),
        label = "CategoryPillText"
    )

    Box(
        modifier = modifier
            .testTag(testTag)
            .minimumInteractiveComponentSize()
            .heightIn(min = JelajahDimens.MinTouchTarget)
            .semantics {
                role = Role.Tab
                this.selected = selected
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(38.dp)
                .clip(JelajahDimens.ShapePill)
                .background(backgroundColor, JelajahDimens.ShapePill)
                .border(
                    width = 1.dp,
                    color = targetBorder,
                    shape = JelajahDimens.ShapePill
                )
                .padding(horizontal = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = typography.bodyMedium,
                color = textColor,
                maxLines = 1
            )
        }
    }
}

/**
 * StatusBadge - Lencana Status Medan & Kondisi Alam
 */
@Composable
fun StatusBadge(
    label: String,
    tone: NatureStatusTone,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    testTag: String = "status_badge"
) {
    val colors = JelajahTheme.colors
    val typography = JelajahTheme.typography
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val (fgColor, bgColor, defaultIcon) = when (tone) {
        NatureStatusTone.SAFE -> Triple(
            colors.success,
            colors.successSurface,
            Icons.Filled.CheckCircle
        )
        NatureStatusTone.WARNING -> Triple(
            colors.warning,
            colors.warningSurface,
            Icons.Filled.WarningAmber
        )
        NatureStatusTone.DANGER -> Triple(
            colors.danger,
            colors.dangerSurface,
            Icons.Filled.ErrorOutline
        )
    }

    val borderColor = if (selected || isPressed) {
        fgColor.copy(alpha = 0.55f)
    } else {
        fgColor.copy(alpha = 0.24f)
    }

    val resolvedIcon = leadingIcon ?: defaultIcon

    val clickableModifier = if (onClick != null) {
        Modifier
            .minimumInteractiveComponentSize()
            .heightIn(min = JelajahDimens.MinTouchTarget)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            )
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .testTag(testTag)
            .then(clickableModifier),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = 30.dp)
                .clip(JelajahDimens.ShapePill)
                .background(bgColor, JelajahDimens.ShapePill)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = JelajahDimens.ShapePill
                )
                .padding(horizontal = JelajahDimens.Space3, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = resolvedIcon,
                contentDescription = null,
                tint = fgColor,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = label,
                style = typography.smallLabel,
                color = fgColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * 5.1 FloatingNavBar - Floating Bottom Navigation Bar (Gaya TripGlide)
 * - Kapsul penuh berwarna --accent (#16191D), tinggi 68dp.
 * - Tab yang aktif memiliki lingkaran putih solid (--bg-surface #FFFFFF, 48x48dp)
 *   dengan ikon gelap (--text-primary #16191D) persis seperti referensi desain TripGlide.
 */
enum class FloatingNavTab(
    val id: String,
    val label: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
) {
    HOME(
        id = "home",
        label = "Beranda",
        activeIcon = Icons.Filled.Home,
        inactiveIcon = Icons.Outlined.Home
    ),
    SAVED(
        id = "saved",
        label = "Tersimpan",
        activeIcon = Icons.Filled.Favorite,
        inactiveIcon = Icons.Outlined.FavoriteBorder
    ),
    TRIPS(
        id = "trips",
        label = "Perjalanan",
        activeIcon = Icons.AutoMirrored.Filled.ListAlt,
        inactiveIcon = Icons.AutoMirrored.Outlined.ListAlt
    ),
    MENU(
        id = "menu",
        label = "Profil",
        activeIcon = Icons.Filled.GridView,
        inactiveIcon = Icons.Outlined.GridView
    )
}

@Composable
fun FloatingNavBar(
    selectedTab: FloatingNavTab,
    onTabSelected: (FloatingNavTab) -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "floating_nav_bar"
) {
    val colors = JelajahTheme.colors
    val haptic = LocalHapticFeedback.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = JelajahDimens.Space6),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .testTag(testTag)
                .fillMaxWidth()
                .height(68.dp),
            shape = JelajahDimens.ShapePill,
            color = colors.accent,
            contentColor = colors.accentFg,
            tonalElevation = 4.dp,
            shadowElevation = 6.dp,
            border = BorderStroke(1.dp, colors.borderSubtle)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingNavTab.entries.forEach { tab ->
                    val isSelected = tab == selectedTab
                    val interactionSource = remember { MutableInteractionSource() }

                    val indicatorBg by animateColorAsState(
                        targetValue = if (isSelected) colors.bgSurface else Color.Transparent,
                        animationSpec = tween(
                            durationMillis = JelajahMotion.DurFastMs,
                            easing = JelajahMotion.EaseStandard
                        ),
                        label = "NavTabIndicator_${tab.id}"
                    )

                    val iconTint by animateColorAsState(
                        targetValue = if (isSelected) {
                            colors.textPrimary
                        } else {
                            colors.accentFg.copy(alpha = 0.85f)
                        },
                        animationSpec = tween(
                            durationMillis = JelajahMotion.DurFastMs,
                            easing = JelajahMotion.EaseStandard
                        ),
                        label = "NavTabTint_${tab.id}"
                    )

                    Box(
                        modifier = Modifier
                            .testTag("floating_nav_item_${tab.id}")
                            .minimumInteractiveComponentSize()
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(indicatorBg, CircleShape)
                            .semantics {
                                contentDescription = tab.label
                                role = Role.Tab
                                selected = isSelected
                            }
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onTabSelected(tab)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSelected) tab.activeIcon else tab.inactiveIcon,
                            contentDescription = tab.label,
                            tint = iconTint,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
