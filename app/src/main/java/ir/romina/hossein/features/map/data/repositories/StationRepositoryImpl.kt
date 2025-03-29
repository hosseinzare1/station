package ir.romina.hossein.features.map.data.repositories

import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.exception.toAppException
import ir.romina.hossein.features.map.data.local.datasources.StationDao
import ir.romina.hossein.features.map.data.local.entities.StationEntity
import ir.romina.hossein.features.map.data.local.entities.asExternalModel
import ir.romina.hossein.features.map.data.remote.datasources.StationRemoteDataSource
import ir.romina.hossein.features.map.data.remote.models.asEntity
import ir.romina.hossein.features.map.domain.entities.Station
import ir.romina.hossein.features.map.domain.repositories.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry

class StationRepositoryImpl(
    private val stationDao: StationDao,
    private val stationRemoteDataSource: StationRemoteDataSource
) : StationRepository {

    override fun getStations(searchText: String): Flow<OperationResult<List<Station>>> {
        return stationDao.getStations(searchText)
            .map<List<StationEntity>, OperationResult<List<Station>>> { stations ->
                OperationResult.Success(data = stations.map { it.asExternalModel() })
            }
            .retry(2) {
                true
            }
            .catch { e ->
                emit(
                    OperationResult.Failure(
                        exception = e.toAppException(),
                        data = emptyList()
                    )
                )
            }
    }

    override suspend fun synchronize() {
        val stations = stationRemoteDataSource.getStations().mapNotNull { it.asEntity() }
        stationDao.upsertStations(stations)
    }
}