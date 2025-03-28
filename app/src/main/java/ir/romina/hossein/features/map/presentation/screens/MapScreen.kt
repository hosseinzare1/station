package ir.romina.hossein.features.map.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.core.ui.components.AppFailureView
import ir.romina.hossein.core.ui.components.AppLoadingIndicator
import ir.romina.hossein.features.map.domain.entities.StationEntity
import ir.romina.hossein.features.map.presentation.manager.MapIntent
import ir.romina.hossein.features.map.presentation.manager.MapViewModel
import ir.romina.hossein.features.map.presentation.widgets.MapView

@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    onNavigateToDetails: (station: StationEntity) -> Unit,
) {

    val state by mapViewModel.state.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(

        ) {
            when (state.operationStatus) {
                OperationStatus.IDLE -> Unit

                OperationStatus.LOADING -> AppLoadingIndicator(
                    modifier = Modifier.padding(innerPadding),
                )

                OperationStatus.SUCCESS -> {
                    MapView(
                        modifier = Modifier.fillMaxSize(),
                        mapViewModel = mapViewModel,
                        onDetailsTap = onNavigateToDetails
                    )
                }

                OperationStatus.ERROR -> AppFailureView(
                    modifier = Modifier.padding(innerPadding),

                    description = state.errorMessage,
                    onTryAgainClick = {
                        mapViewModel.handleIntent(MapIntent.LoadStations)
                    },
                )
            }
        }
    }

}

