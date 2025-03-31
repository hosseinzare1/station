package ir.romina.hossein.features.map.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import ir.romina.hossein.features.map.presentation.manager.station.StationState

@Composable
fun StationsMainView(
    state: StationState,
    onAction: (StationIntent) -> Unit,
    onDetailsTap: (station: Station) -> Unit,
    modifier: Modifier = Modifier
) {


    val stationListState = rememberLazyListState()


    val cameraPositionState = rememberCameraPositionState {
        val position = (state.selectedStation ?: state.stations.firstOrNull())?.let {
            LatLng(it.lat, it.lon)
        } ?: LatLng(35.6892, 51.3890)

        CameraPosition.fromLatLngZoom(position, 14f)
    }

    LaunchedEffect(state.selectedStation) {
        state.selectedStation?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(LatLng(it.lat, it.lon), 14f)
            )
            stationListState.animateScrollToItem(state.stations.indexOf(it))
        }
    }


    Box(modifier = modifier) {
        GoogleMapView(
            stations = state.stations,
            cameraPositionState = cameraPositionState,
            onClick = { station ->
                onAction(StationIntent.SelectStation(station))
            },
        )
        if (state.operationStatus == OperationStatus.LOADING) {
            AppLoadingIndicator(
                modifier = Modifier.fillMaxSize()
            )
        }
        SearchBarView(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            onSearchQueryChange = { newText ->
                onAction(StationIntent.UpdateSearchQuery(newText))
            }
        )
        StationsListView(
            modifier = Modifier.align(Alignment.BottomCenter),
            stations = state.stations,
            onDetailsTap = onDetailsTap,
            lazyListState = stationListState,
            selectedStationId = state.selectedStation?.stationId,
            onNavigationTap = { station ->
                onAction(StationIntent.SelectStation(station))
            },
        )
    }
}

