package ir.romina.hossein.features.map.presentation.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.romina.hossein.features.map.domain.entities.StationEntity


@Composable
fun StationsListView(
    stations: List<StationEntity>,
    onNavigationTap: (station: StationEntity) -> Unit,
    onDetailsTap: (station: StationEntity) -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
    selectedStationId: String?,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState,
    ) {
        items(stations) { station ->
            StationCardView(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 18.dp),
                station = station,
                isSelected = station.stationId == selectedStationId,
                onNavigationTap = {
                    onNavigationTap(station)
                },
                onDetailsTap = {
                    onDetailsTap(station)
                },
            )
        }
    }
}