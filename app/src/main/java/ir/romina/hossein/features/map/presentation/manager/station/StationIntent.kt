package ir.romina.hossein.features.map.presentation.manager.station

import ir.romina.hossein.features.map.domain.entities.Station


sealed class StationIntent {
    data object LoadStations : StationIntent()
    data class UpdateSearchQuery(val searchQuery: String) : StationIntent()
    data class SelectStation(val station: Station) : StationIntent()
}