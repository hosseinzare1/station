package ir.romina.hossein.features.map.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ir.romina.hossein.core.base.presentation.screens.BaseScreen
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.core.ui.components.AppFailureView
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.presentation.manager.station.StationIntent
import ir.romina.hossein.features.map.presentation.manager.station.StationViewModel
import ir.romina.hossein.features.map.presentation.views.StationsMainView

@Composable
fun MapScreen(
    stationViewModel: StationViewModel,
    onNavigateToDetails: (station: Station) -> Unit,
) {

    val state by stationViewModel.state.collectAsState()

    BaseScreen {
        when {
            state.stations.isNotEmpty() || state.operationStatus == OperationStatus.SUCCESS || state.operationStatus == OperationStatus.LOADING ->
                StationsMainView(
                    modifier = Modifier
                        .fillMaxSize(),
                    stationViewModel = stationViewModel,
                    onDetailsTap = onNavigateToDetails,
                )

            state.operationStatus == OperationStatus.ERROR ->
                AppFailureView(
                    modifier = Modifier
                        .fillMaxSize(),
                    description = state.errorMessage,
                    onTryAgainClick = {
                        stationViewModel.handleIntent(StationIntent.LoadStations)
                    },
                )

        }
    }

}

