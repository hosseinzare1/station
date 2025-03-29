package ir.romina.hossein.features.map.data.local.entities

import androidx.room.Entity
import ir.romina.hossein.features.map.domain.entities.GeocodedColumn

@Entity
data class GeocodedColumnEntity(
    val latitude: Double?,
    val longitude: Double?
)


fun GeocodedColumnEntity.asExternalModel() = GeocodedColumn(
    latitude = latitude,
    longitude = latitude,
)