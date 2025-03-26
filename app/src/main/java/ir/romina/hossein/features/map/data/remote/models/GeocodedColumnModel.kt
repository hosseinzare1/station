package ir.romina.hossein.features.map.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class GeocodedColumnModel(
    val latitude: String?,
    val longitude: String?
)