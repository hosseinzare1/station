package ir.romina.hossein.features.map.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.core.ui.components.AppLoadingIndicator
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.presentation.manager.station.StationIntent
import ir.romina.hossein.features.map.presentation.manager.station.StationViewModel

@Composable
fun StationsMainView(
    stationViewModel: StationViewModel,
    onDetailsTap: (station: Station) -> Unit,
    modifier: Modifier = Modifier
) {

    val stationState by stationViewModel.state.collectAsState()

    val stationListState = rememberLazyListState()
    val cameraPositionState = rememberCameraPositionState {}


    LaunchedEffect(stationState.selectedStationId) {
        val selectedStation = stationState.stations.find { it.stationId == stationState.selectedStationId }
        selectedStation?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(LatLng(it.lat, it.lon), 14f)
            )
            stationListState.animateScrollToItem(stationState.stations.indexOf(it))
        }
    }

    val stations = remember(stationState.stations) { stationState.stations }


    Box(modifier = modifier) {
        GoogleMapView(
            stations = stations,
            cameraPositionState = cameraPositionState,
            onClick = { station ->
                stationViewModel.handleIntent(StationIntent.SelectStation(station.stationId))
            },
        )
        if (stationState.operationStatus == OperationStatus.LOADING) {
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
            stations = stationState.stations,
            onDetailsTap = onDetailsTap,
            lazyListState = stationListState,
            selectedStationId = stationState.selectedStationId,
            onNavigationTap = { station ->
                stationViewModel.handleIntent(StationIntent.SelectStation(station.stationId))
            },
        )
    }
}

