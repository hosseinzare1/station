package ir.romina.hossein.features.map.presentation.manager

import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.features.map.domain.entities.StationEntity

data class MapState(
    val operationStatus: OperationStatus = OperationStatus.IDLE,
    val errorMessage: String? = null,
    val stations: List<StationEntity> = emptyList(),
    val selectedStationId: String? = null
)
