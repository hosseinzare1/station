package ir.romina.hossein.features.map.data.mapper

import ir.romina.hossein.features.map.data.remote.models.GeocodedColumnModel
import ir.romina.hossein.features.map.domain.entities.GeocodedColumnEntity

fun GeocodedColumnModel.toEntity() = GeocodedColumnEntity(
    latitude = latitude?.toDoubleOrNull(),
    longitude = latitude?.toDoubleOrNull(),
)