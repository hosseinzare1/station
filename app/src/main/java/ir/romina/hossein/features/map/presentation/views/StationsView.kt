package ir.romina.hossein.features.map.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.core.ui.components.AppLoadingIndicator
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.presentation.manager.station.StationIntent
import ir.romina.hossein.features.map.presentation.manager.station.StationViewModel
import kotlinx.coroutines.launch

@Composable
fun MapMainView(
    stationViewModel: StationViewModel,
    onDetailsTap: (station: Station) -> Unit,
    modifier: Modifier = Modifier
) {

    val stationListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val state by stationViewModel.state.collectAsState()

    val firstStation = state.stations.firstOrNull()
    val firstStationPosition = firstStation?.let { LatLng(firstStation.lat, firstStation.lon) }
    val defaultPosition = firstStationPosition ?: LatLng(35.6892, 51.3890)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 14f)
    }

    LaunchedEffect(firstStationPosition) {
        if (firstStationPosition != null && cameraPositionState.position.target == defaultPosition) {
            coroutineScope.launch {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLng(firstStationPosition)
                )
            }
        }
    }

    LaunchedEffect(state.selectedStationId) {
        val selectedStation = state.stations.find { it.stationId == state.selectedStationId }
        selectedStation?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(LatLng(it.lat, it.lon), 14f)
            )
            stationListState.animateScrollToItem(state.stations.indexOf(it))
        }
    }

    val stations = remember(state.stations) { state.stations }


    Box(modifier = modifier) {
        GoogleMapView(
            stations = stations,
            cameraPositionState = cameraPositionState,
            onClick = { station ->
                stationViewModel.handleIntent(StationIntent.SelectStation(station.stationId))
            },
        )
        if (state.operationStatus == OperationStatus.LOADING) {
            AppLoadingIndicator()
        }
        SearchBarRow(
            modifier = Modifier.align(Alignment.TopCenter),
            onSearchQueryChange = { newText ->
                stationViewModel.handleIntent(StationIntent.UpdateSearchQuery(newText))
            }
        )
        StationsListView(
            modifier = Modifier.align(Alignment.BottomCenter),
            stations = state.stations,
            onDetailsTap = onDetailsTap,
            lazyListState = stationListState,
            selectedStationId = state.selectedStationId,
            onNavigationTap = { station ->
                stationViewModel.handleIntent(StationIntent.SelectStation(station.stationId))
            },
        )
    }
}

