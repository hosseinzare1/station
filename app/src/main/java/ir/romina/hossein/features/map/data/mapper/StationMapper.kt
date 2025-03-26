package ir.romina.hossein.features.map.data.mapper

import ir.romina.hossein.features.map.data.remote.models.StationModel
import ir.romina.hossein.features.map.domain.entities.StationEntity
import ir.romina.hossein.features.map.enums.RentalMethod

fun StationModel.toEntity() = StationEntity(
    capacity = capacity?.toShort(),
    geocodedColumn = geocodedColumn?.toEntity(),
    lat = lat?.toDouble(),
    lon = lon?.toDouble(),
    name = name,
    rentalMethod = rentalMethod?.split(",")
        ?.mapNotNull { method -> RentalMethod.entries.find { it.name == method } },
    stationId = stationId
)