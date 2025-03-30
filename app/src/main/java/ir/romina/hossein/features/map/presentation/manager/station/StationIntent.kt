package ir.romina.hossein.features.map.presentation.manager.station


sealed class StationIntent {
    data object LoadStations : StationIntent()
    data class UpdateSearchQuery(val searchQuery: String) : StationIntent()
    data class SelectStation(val stationId: String) : StationIntent()
}