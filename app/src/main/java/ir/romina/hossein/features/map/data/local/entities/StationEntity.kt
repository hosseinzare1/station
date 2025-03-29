package ir.romina.hossein.features.map.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.romina.hossein.core.constant.DBConst
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.enums.RentalMethod

@Entity(tableName = DBConst.STATION_TABLE_NAME)
data class StationEntity(
    @PrimaryKey
    val stationId: Int,
    val capacity: Short,
    @Embedded
    val geocodedColumn: GeocodedColumnEntity,
    val lat: Double,
    val lon: Double,
    val name: String?,
    val rentalMethod: List<RentalMethod>?,
)


fun StationEntity.asExternalModel(): Station {

    return Station(
        capacity = capacity,
        geocodedColumn = geocodedColumn.asExternalModel(),
        lat = lat,
        lon = lon,
        name = name,
        rentalMethod = rentalMethod,
        stationId = stationId.toString()
    )
}