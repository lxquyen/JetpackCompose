package com.ohayo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ohayo.app.NavGraphs.root
import com.ohayo.app.core.data.util.NetworkMonitor
import com.ohayo.app.core.designsystem.theme.CustomTheme
import com.ohayo.app.destinations.HomeScreenDestination
import com.ohayo.app.ui.NiaApp
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            DisposableEffect(systemUiController) {
                systemUiController.systemBarsDarkContentEnabled = true
                onDispose { }
            }

            CustomTheme(
                darkTheme = true,
                androidTheme = false
            ) {
                NiaApp(
                    networkMonitor = networkMonitor,
                    windowSizeClass = calculateWindowSizeClass(this),
                )
            }
        }
    }
}

@Composable
fun MainContent() {
    DestinationsNavHost(navGraph = root) {
        composable(HomeScreenDestination) {
            HomeScreen(navigator = destinationsNavigator)
        }
    }
}

@Destination
@Composable
fun SplashScreen(navigator: DestinationsNavigator) {

}

@Destination(start = true)
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {

}

@Destination
@Composable
fun SecondScreen(navController: NavController, content: String?) {

}

@Preview
@Composable
fun MainContentPreview() {
    MainContent()
}

