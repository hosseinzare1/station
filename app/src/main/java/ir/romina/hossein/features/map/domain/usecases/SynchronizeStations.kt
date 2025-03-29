package ir.romina.hossein.features.map.domain.usecases

import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.base.domain.use_cases.BaseUseCaseNoArg
import ir.romina.hossein.features.map.domain.repositories.StationRepository


class SynchronizeStationsUseCase(private val stationRepository: StationRepository) :
    BaseUseCaseNoArg<OperationResult<Unit>> {
    override suspend fun call(): OperationResult<Unit> {
        return stationRepository.synchronizeStations()
    }
}