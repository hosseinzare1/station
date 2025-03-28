package ir.romina.hossein.features.map.presentation.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import ir.romina.hossein.features.map.domain.entities.StationEntity
import ir.romina.hossein.features.map.presentation.manager.MapIntent
import ir.romina.hossein.features.map.presentation.manager.MapViewModel
import kotlinx.coroutines.launch

@Composable
fun MapView(
    mapViewModel: MapViewModel,
    onDetailsTap: (station: StationEntity) -> Unit,
    modifier: Modifier = Modifier
) {


    val stationListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val state by mapViewModel.state.collectAsState()


    val first = state.stations.firstOrNull()
    val initPosition = LatLng(first?.lat ?: 45.99, first?.lon ?: 55.5)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initPosition, 10f)
    }

    Box(modifier = modifier) {
        GoogleMap(
            cameraPositionState = cameraPositionState
        ) {
            state.stations.map { station ->
                MapCircleView(
                    station = station,
                    maxCapacity = state.maxCapacity ?: 0,
                    onClick = {
                        mapViewModel.handleIntent(MapIntent.SelectStation(station.stationId))
                        coroutineScope.launch {
                            //TODO Move animate to another location that lunched after viewModel updates state
                            stationListState.animateScrollToItem(
                                state.stations.indexOf(station)
                            )
                        }
                    },
                )
            }
        }
        StationsListView(
            modifier = Modifier.align(Alignment.BottomCenter),
            stations = state.stations,
            onDetailsTap = onDetailsTap,
            lazyListState = stationListState,
            selectedStationId = state.selectedStationId,
            onNavigationTap = { station ->
                coroutineScope.launch {
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