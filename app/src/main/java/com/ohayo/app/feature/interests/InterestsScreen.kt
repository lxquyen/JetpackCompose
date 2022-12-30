package com.ohayo.app.feature.interests

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Created by Furuichi on 30/12/2022
 */

@Composable
fun InterestsRoute(
    navigateToTopic: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InterestsViewModel = hiltViewModel()
) {

}

class InterestsScreen {
}