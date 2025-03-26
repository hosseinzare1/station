package ir.romina.hossein.features.map.presentation.manager


sealed class MapIntent {
    data object LoadStations : MapIntent()
    data class FilterStations(val filter: String) : MapIntent()
    data class SelectStation(val stationId: String) : MapIntent()
}