package ir.romina.hossein.features.map.domain.usecases

import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.base.domain.use_cases.BaseUseCase
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.domain.repositories.StationRepository
import kotlinx.coroutines.flow.Flow

class GetStationsUseCase(private val stationRepository: StationRepository) :
    BaseUseCase<Flow<OperationResult<List<Station>>>, String> {
    override suspend fun call(arg: String): Flow<OperationResult<List<Station>>> {
        return stationRepository.getStations(arg)
    }
}