package com.test.movemate.navigate

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.test.movemate.ui.screen.NavGraphs
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun NavHostContainer(navController: NavHostController) {
    DestinationsNavHost(
        navGraph = NavGraphs.root,
        navController = navController,
        engine = rememberAnimatedNavHostEngine()
    )
}