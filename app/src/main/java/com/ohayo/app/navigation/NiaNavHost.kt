package com.ohayo.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ohayo.app.feature.bookmarks.navigation.bookmarksScreen
import com.ohayo.app.feature.foryou.navigation.forYouNavigationRoute
import com.ohayo.app.feature.foryou.navigation.forYouScreen
import com.ohayo.app.feature.interests.navigation.interestsGraph
import com.ohayo.app.feature.topic.navigation.navigateToTopic
import com.ohayo.app.feature.topic.navigation.topicScreen

/**
 * Created by Furuichi on 30/12/2022
 */
@Composable
fun NiaNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = forYouNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        forYouScreen()
        bookmarksScreen()
        interestsGraph(
            navigateToTopic = { topicId ->
                navController.navigateToTopic(topicId)
            },
            nestedGraphs = {
                topicScreen(onBackClick)
            }
        )
    }
}
