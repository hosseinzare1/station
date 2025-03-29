package ir.romina.hossein.features.map.presentation.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.core.ui.components.AppLoadingIndicator
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.presentation.manager.map.MapIntent
import ir.romina.hossein.features.map.presentation.manager.map.MapViewModel
import kotlinx.coroutines.launch

@Composable
fun MapMainView(
    mapViewModel: MapViewModel,
    onDetailsTap: (station: Station) -> Unit,
    modifier: Modifier = Modifier
) {

    val stationListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val state by mapViewModel.state.collectAsState()

    val firstStation = state.stations.firstOrNull()
    val firstStationPosition = firstStation?.let { LatLng(firstStation.lat, firstStation.lon) }
    val defaultPosition = firstStationPosition ?: LatLng(35.6892, 51.3890)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 14f)
    }

    val configuration = LocalConfiguration.current

    LaunchedEffect(firstStationPosition) {
        if (firstStationPosition != null && cameraPositionState.position.target == defaultPosition) {
            coroutineScope.launch {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLng(firstStationPosition)
                )
            }
        }
    }


    Box(modifier = modifier) {
        GoogleMapView(
            stations = state.stations,
            cameraPositionState = cameraPositionState,
            onClick = { station ->
                mapViewModel.handleIntent(MapIntent.SelectStation(station.stationId))
                coroutineScope.launch {
                    //TODO Move animate to another location that lunched after viewModel updates state
                    stationListState.animateScrollToItem(
                        state.stations.indexOf(station)
                    )
                }
            },
        )
        if (state.operationStatus == OperationStatus.LOADING) {
            AppLoadingIndicator()
        }
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchStationView(
                modifier = Modifier.weight(1f),
                onChange = { newText ->
                    mapViewModel.handleIntent(MapIntent.UpdateSearchQuery(newText))
                }
            )
            SyncStateView(
                modifier = Modifier.padding(end = 16.dp)
            )
        }

        StationsListView(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .apply {
                    if (configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                        this.windowInsetsPadding(WindowInsets.ime)
                    }
                },
            stations = state.stations,
            onDetailsTap = onDetailsTap,
            lazyListState = stationListState,
            selectedStationId = state.selectedStationId,
            onNavigationTap = { station ->
                coroutineScope.launch {
                    mapViewModel.handleIntent(MapIntent.SelectStation(station.stationId))
                    stationListState.animateScrollToItem(
                        state.stations.indexOf(station)
                    )
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLng(
                            LatLng(
                                station.lat, station.lon
                            )
                        )
                    )
                }
            },
        )
    }
}

