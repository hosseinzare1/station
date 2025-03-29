package ir.romina.hossein.features.map.data.local.datasources

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.romina.hossein.core.constant.DBConst
import ir.romina.hossein.features.map.data.local.entities.StationEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface StationDao {

    @Upsert
    fun upsertStations(stations: List<StationEntity>)

    @Query("SELECT * FROM ${DBConst.STATION_TABLE_NAME} WHERE name LIKE '%' || :searchText || '%'")
    fun getStations(searchText: String): Flow<List<StationEntity>>

}