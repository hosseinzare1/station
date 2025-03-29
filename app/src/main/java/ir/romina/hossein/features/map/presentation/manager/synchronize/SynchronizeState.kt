package ir.romina.hossein.features.map.presentation.manager.synchronize

import ir.romina.hossein.core.enums.OperationStatus

data class SynchronizeState(
    val synchronizeOperationsStatus: OperationStatus = OperationStatus.IDLE,
    val errorMessage: String? = null,
)
