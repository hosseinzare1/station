package ir.romina.hossein.features.map.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.romina.hossein.core.base.presentation.screens.BaseScreen
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.core.ui.components.AppFailureView
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.presentation.manager.station.StationIntent
import ir.romina.hossein.features.map.presentation.manager.station.StationState
import ir.romina.hossein.features.map.presentation.views.StationsMainView

@Composable
fun MapScreen(
    state: StationState,
    onAction: (StationIntent) -> Unit,
    onNavigateToDetails: (station: Station) -> Unit,
) {

    BaseScreen {
        when {
            state.stations.isNotEmpty() || state.operationStatus == OperationStatus.SUCCESS || state.operationStatus == OperationStatus.LOADING ->
                StationsMainView(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = state,
                    onAction = onAction,
                    onDetailsTap = onNavigateToDetails,
                )

            state.operationStatus == OperationStatus.ERROR ->
                AppFailureView(
                    modifier = Modifier
                        .fillMaxSize(),
                    description = state.errorMessage,
                    onTryAgainClick = {
                        onAction(StationIntent.LoadStations)
                    },
                )

        }
    }

}

