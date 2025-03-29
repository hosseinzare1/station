package ir.romina.hossein.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.romina.hossein.features.map.data.local.datasources.StationDao
import ir.romina.hossein.features.map.data.local.entities.RentalMethodConverter
import ir.romina.hossein.features.map.data.local.entities.StationEntity


@Database(entities = [StationEntity::class], version = 1)
@TypeConverters(RentalMethodConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao
}