package ir.romina.hossein.features.map.domain.entities

import ir.romina.hossein.features.map.enums.RentalMethod
import kotlinx.serialization.Serializable

@Serializable
data class Station(
    val capacity: Short,
    val geocodedColumn: GeocodedColumn,
    val lat: Double,
    val lon: Double,
    val name: String?,
    val rentalMethod: List<RentalMethod>?,
    val stationId: String
)