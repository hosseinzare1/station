package ir.romina.hossein.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ir.romina.hossein.features.map.presentation.MapNavigation
import ir.romina.hossein.features.map.presentation.mapNavGraph


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = MapNavigation.MapScreen
    ) {
        mapNavGraph(navController)
    }
}