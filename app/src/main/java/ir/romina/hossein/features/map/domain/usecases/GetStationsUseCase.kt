package ir.romina.hossein.features.map.domain.usecases

import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.base.domain.use_cases.BaseUseCase
import ir.romina.hossein.features.map.domain.entities.StationEntity
import ir.romina.hossein.features.map.domain.repositories.StationRepository

class GetStationsUseCase(private val stationRepository: StationRepository) :
    BaseUseCase<OperationResult<List<StationEntity>>, Unit> {
    override suspend fun call(arg: Unit): OperationResult<List<StationEntity>> {
        return stationRepository.getStations()
    }
}