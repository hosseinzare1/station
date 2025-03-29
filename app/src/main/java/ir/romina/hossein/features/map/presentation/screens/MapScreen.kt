package ir.romina.hossein.features.map.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.core.ui.components.AppFailureView
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.presentation.manager.map.MapIntent
import ir.romina.hossein.features.map.presentation.manager.map.MapViewModel
import ir.romina.hossein.features.map.presentation.widgets.MapMainView

@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    onNavigateToDetails: (station: Station) -> Unit,
) {

    val state by mapViewModel.state.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when {

            state.stations.isNotEmpty() || state.operationStatus == OperationStatus.SUCCESS || state.operationStatus == OperationStatus.LOADING ->
                MapMainView(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    mapViewModel = mapViewModel,
                    onDetailsTap = onNavigateToDetails,
                )

            state.operationStatus == OperationStatus.ERROR ->
                AppFailureView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    description = state.errorMessage,
                    onTryAgainClick = {
                        mapViewModel.handleIntent(MapIntent.LoadStations)
                    },
                )

        }
    }

}

