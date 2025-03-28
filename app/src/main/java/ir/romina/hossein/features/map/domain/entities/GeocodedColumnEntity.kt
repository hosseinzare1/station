package ir.romina.hossein.features.map.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class GeocodedColumnEntity(
    val latitude: Double?,
    val longitude: Double?
)