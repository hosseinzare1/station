package ir.romina.hossein.features.map.presentation.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.romina.hossein.features.map.domain.entities.Station


@Composable
fun StationsListView(
    stations: List<Station>,
    onNavigationTap: (station: Station) -> Unit,
    onDetailsTap: (station: Station) -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
    selectedStationId: String?,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState,
        contentPadding = PaddingValues(8.dp)
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