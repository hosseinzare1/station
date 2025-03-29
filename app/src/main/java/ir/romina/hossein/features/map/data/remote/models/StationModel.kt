package ir.romina.hossein.features.map.data.remote.models

import ir.romina.hossein.features.map.data.local.entities.StationEntity
import ir.romina.hossein.features.map.enums.RentalMethod
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
    val stationId: String
)


fun StationModel.asEntity(): StationEntity? {
    val latDouble = lat?.toDoubleOrNull()
    val lonDouble = lon?.toDoubleOrNull()

    if (latDouble == null || lonDouble == null || geocodedColumn == null || capacity == null) {
        return null
    }

    return StationEntity(
        capacity = capacity.toShortOrNull() ?: 0,
        geocodedColumn = geocodedColumn.asEntity(),
        lat = latDouble,
        lon = lonDouble,
        name = name,
        rentalMethod = rentalMethod?.split(",")
            ?.mapNotNull { method -> RentalMethod.entries.find { it.name == method } },
        //TODO review and change .toInt() . handle data type witch better way
        stationId = stationId.toInt()
    )
}