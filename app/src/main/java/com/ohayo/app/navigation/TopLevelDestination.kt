package com.ohayo.app.navigation

import com.ohayo.app.R
import com.ohayo.app.core.designsystem.icon.Icon
import com.ohayo.app.core.designsystem.icon.Icon.DrawableResourceIcon
import com.ohayo.app.core.designsystem.icon.Icon.ImageVectorIcon
import com.ohayo.app.core.designsystem.icon.NiaIcons

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int,
    val titleTextId: Int
) {
    FOR_YOU(
        selectedIcon = DrawableResourceIcon(NiaIcons.Upcoming),
        unselectedIcon = DrawableResourceIcon(NiaIcons.UpcomingBorder),
        iconTextId = R.string.for_you,
        titleTextId = R.string.app_name
    ),
    BOOKMARKS(
        selectedIcon = DrawableResourceIcon(NiaIcons.Bookmarks),
        unselectedIcon = DrawableResourceIcon(NiaIcons.BookmarksBorder),
        iconTextId = R.string.saved,
        titleTextId = R.string.saved
    ),
    INTERESTS(
        selectedIcon = ImageVectorIcon(NiaIcons.Grid3x3),
        unselectedIcon = ImageVectorIcon(NiaIcons.Grid3x3),
        iconTextId = R.string.interests,
        titleTextId = R.string.interests
    )
}
