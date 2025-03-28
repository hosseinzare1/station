package ir.romina.hossein.features.map.data.mapper

import ir.romina.hossein.features.map.data.remote.models.StationModel
import ir.romina.hossein.features.map.domain.entities.StationEntity
import ir.romina.hossein.features.map.enums.RentalMethod


fun StationModel.toEntity(): StationEntity? {
    val latDouble = lat?.toDoubleOrNull()
    val lonDouble = lon?.toDoubleOrNull()

    if (latDouble == null || lonDouble == null || geocodedColumn == null || capacity == null) {
        return null
    }

    return StationEntity(
        capacity = capacity.toShortOrNull() ?: 0,
        geocodedColumn = geocodedColumn.toEntity(),
        lat = latDouble,
        lon = lonDouble,
        name = name,
        rentalMethod = rentalMethod?.split(",")
            ?.mapNotNull { method -> RentalMethod.entries.find { it.name == method } },
        stationId = stationId
    )
}