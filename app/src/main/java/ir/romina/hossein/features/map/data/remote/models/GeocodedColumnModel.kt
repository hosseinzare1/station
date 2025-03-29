package ir.romina.hossein.features.map.data.remote.models

import ir.romina.hossein.features.map.data.local.entities.GeocodedColumnEntity
import kotlinx.serialization.Serializable

@Serializable
data class GeocodedColumnModel(
    val latitude: String?,
    val longitude: String?
)

fun GeocodedColumnModel.asEntity() = GeocodedColumnEntity(
    latitude = latitude?.toDoubleOrNull(),
    longitude = latitude?.toDoubleOrNull(),
)