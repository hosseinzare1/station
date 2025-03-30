package ir.romina.hossein.features.map.presentation.manager.station

import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.features.map.domain.entities.Station

data class StationState(
    val operationStatus: OperationStatus = OperationStatus.IDLE,
    val errorMessage: String? = null,
    val stations: List<Station> = emptyList(),
    val selectedStationId: String? = null,
)
