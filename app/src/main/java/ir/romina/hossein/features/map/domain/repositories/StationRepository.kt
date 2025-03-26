package ir.romina.hossein.features.map.domain.repositories

import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.features.map.domain.entities.StationEntity

interface StationRepository {
    suspend fun getStations(): OperationResult<List<StationEntity>>
}