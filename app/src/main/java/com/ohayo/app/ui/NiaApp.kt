package com.ohayo.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.ohayo.app.R
import com.ohayo.app.core.data.util.NetworkMonitor
import com.ohayo.app.core.designsystem.component.*
import com.ohayo.app.core.designsystem.icon.Icon.DrawableResourceIcon
import com.ohayo.app.core.designsystem.icon.Icon.ImageVectorIcon
import com.ohayo.app.core.designsystem.icon.NiaIcons
import com.ohayo.app.navigation.NiaNavHost
import com.ohayo.app.navigation.TopLevelDestination

/**
 * Created by Furuichi on 30/12/2022
 */
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalLifecycleComposeApi::class
)
@Composable
fun NiaApp(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    appState: NiaAppState = rememberNiaAppState(
        networkMonitor = networkMonitor,
        windowSizeClass = windowSizeClass
    ),
) {
    val background: @Composable (@Composable () -> Unit) -> Unit =
        when (appState.currentTopLevelDestination) {
            TopLevelDestination.FOR_YOU -> { content ->
                CustomGradientBackground(content = content)
            }
            else -> { content -> CustomBackground(content = content) }
        }

    background {

        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            modifier = Modifier,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                // Show the top app bar on top level destinations.
                val destination = appState.currentTopLevelDestination
                if (destination != null) {
                    NiaTopAppBar(
                        // When the nav rail is displayed, the top app bar will, by default
                        // overlap it. This means that the top most item in the nav rail
                        // won't be tappable. A workaround is to position the top app bar
                        // behind the nav rail using zIndex.
                        modifier = Modifier.zIndex(-1F),
                        titleRes = destination.titleTextId,
                        actionIcon = NiaIcons.Settings,
                        actionIconContentDescription = null,
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        onActionClick = {

                        }
                    )
                }
            },
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    NiaBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination,
                        modifier = Modifier.testTag("NiaBottomBar")
                    )
                }
            }
        ) { padding ->

            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

            // If user is not connected to the internet show a snack bar to inform them.
            val notConnected = stringResource(R.string.not_connected)
            LaunchedEffect(isOffline) {
                if (isOffline) snackbarHostState.showSnackbar(
                    message = notConnected,
                    duration = SnackbarDuration.Indefinite
                )
            }

            if (appState.shouldShowSettingsDialog) {
//                SettingsDialog(
//                    onDismiss = { appState.setShowSettingsDialog(false) }
//                )
            }

            Row(
                Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    )
            ) {
                if (appState.shouldShowNavRail) {
                    NiaNavRail(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination,
                        modifier = Modifier
                            .testTag("NiaNavRail")
                            .safeDrawingPadding()
                    )
                }

                NiaNavHost(
                    navController = appState.navController,
                    onBackClick = appState::onBackClick,

                    modifier = Modifier
                        .padding(padding)
                        .consumedWindowInsets(padding)
                )
            }
        }
    }
}

@Composable
private fun NiaNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NiaNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NiaNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    when (icon) {
                        is ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )
                        is DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null
                        )
                    }
                },
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}

@Composable
private fun NiaBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NiaNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NiaNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    when (icon) {
                        is ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )

                        is DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null
                        )
                    }
                },
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false