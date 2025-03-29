package ir.romina.hossein.features.map.presentation.manager.map


sealed class MapIntent {
    data object LoadStations : MapIntent()
    data class UpdateSearchQuery(val searchQuery: String) : MapIntent()
    data class SelectStation(val stationId: String) : MapIntent()
}