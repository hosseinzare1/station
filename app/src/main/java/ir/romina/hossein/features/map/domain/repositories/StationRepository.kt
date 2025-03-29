package ir.romina.hossein.features.map.domain.repositories

import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.features.map.domain.entities.Station
import kotlinx.coroutines.flow.Flow

interface StationRepository {
    fun getStations(searchText: String): Flow<OperationResult<List<Station>>>

    suspend fun synchronize()
}