package ir.romina.hossein.features.map.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ir.romina.hossein.core.ui.navigation.CustomNavType
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.presentation.screens.MapScreen
import ir.romina.hossein.features.map.presentation.screens.StationDetailsScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.typeOf


fun NavGraphBuilder.mapNavGraph(navController: NavHostController) {
    composable<MapNavigation.MapScreen> {
        MapScreen(
            stationViewModel = koinViewModel(),
            onNavigateToDetails = { station ->
                navController.navigate(MapNavigation.StationDetailsScreen(station))
            },
        )
    }
    composable<MapNavigation.StationDetailsScreen>(
        typeMap = mapOf(
            typeOf<Station>() to CustomNavType(Station.serializer()),
        )
    ) {
        val args = it.toRoute<MapNavigation.StationDetailsScreen>()
        StationDetailsScreen(
            station = args.station,
            onBack = {
                navController.navigateUp()
            },
        )
    }
}


sealed class MapNavigation {

    @Serializable
    object MapScreen


    @Serializable
    data class StationDetailsScreen(val station: Station)
}

