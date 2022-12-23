package com.ohayo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.ohayo.app.NavGraphs.root
import com.ohayo.app.core.designsystem.theme.CustomTheme
import com.ohayo.app.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CustomTheme {

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

