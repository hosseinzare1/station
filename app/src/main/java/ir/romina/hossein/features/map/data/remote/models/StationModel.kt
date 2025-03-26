package ir.romina.hossein.features.map.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StationModel(
    val capacity: String?,
    @SerialName("geocoded_column")
    val geocodedColumn: GeocodedColumnModel?,
    val lat: String?,
    val lon: String?,
    val name: String?,
    @SerialName("rental_method")
    val rentalMethod: String?,
    @SerialName("station_id")
    val stationId: String?
)