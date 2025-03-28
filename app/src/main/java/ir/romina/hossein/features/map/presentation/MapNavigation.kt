package ir.romina.hossein.features.map.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ir.romina.hossein.core.ui.navigation.CustomNavType
import ir.romina.hossein.features.map.domain.entities.StationEntity
import ir.romina.hossein.features.map.presentation.screens.MapScreen
import ir.romina.hossein.features.map.presentation.screens.StationDetailsScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.typeOf


fun NavGraphBuilder.mapNavGraph(navController: NavHostController) {
    composable<MapNavigation.MapScreen> {
        MapScreen(
            mapViewModel = koinViewModel(),
            onNavigateToDetails = { station ->
                navController.navigate(MapNavigation.StationDetailsScreen(station))
            },
        )
    }
    composable<MapNavigation.StationDetailsScreen>(
        typeMap = mapOf(
            typeOf<StationEntity>() to CustomNavType(StationEntity.serializer()),
        )
    ) {
        val args = it.toRoute<MapNavigation.StationDetailsScreen>()
        StationDetailsScreen(
            station = args.station,
        )
    }
}


sealed class MapNavigation {

    @Serializable
    object MapScreen


    @Serializable
    data class StationDetailsScreen(val station: StationEntity)
}

