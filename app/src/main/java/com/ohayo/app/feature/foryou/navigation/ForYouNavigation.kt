package com.ohayo.app.feature.foryou.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ohayo.app.feature.foryou.ForYouRoute

/**
 * Created by Furuichi on 30/12/2022
 */
const val forYouNavigationRoute = "for_you_route"

fun NavController.navigateToForYou(navOptions: NavOptions? = null) {
    this.navigate(forYouNavigationRoute, navOptions)
}

fun NavGraphBuilder.forYouScreen() {
    composable(route = forYouNavigationRoute) {
        ForYouRoute()
    }
}
