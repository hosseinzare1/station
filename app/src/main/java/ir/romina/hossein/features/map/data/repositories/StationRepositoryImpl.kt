package ir.romina.hossein.features.map.data.repositories

import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.exception.toAppException
import ir.romina.hossein.features.map.data.mapper.toEntity
import ir.romina.hossein.features.map.data.remote.datasources.StationRemoteDataSource
import ir.romina.hossein.features.map.domain.entities.StationEntity
import ir.romina.hossein.features.map.domain.repositories.StationRepository

class StationRepositoryImpl(
    private val stationRemoteDataSource: StationRemoteDataSource
) : StationRepository {
    override suspend fun getStations(): OperationResult<List<StationEntity>> {
        try {
            val response =
                stationRemoteDataSource
                    .getStations()
                    .mapNotNull { it.toEntity() }

            return OperationResult.Success(
                response
            )
        } catch (e: Exception) {
            return OperationResult.Failure(
                e.toAppException()
            )
        }
    }
}