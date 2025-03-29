package ir.romina.hossein.features.map.presentation.manager.synchronize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.core.utils.ConnectivityObserver
import ir.romina.hossein.features.map.domain.usecases.SynchronizeStationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SynchronizeViewModel(
    private val synchronizeStationsUseCase: SynchronizeStationsUseCase,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {
    private val _state = MutableStateFlow(SynchronizeState())
    val state: StateFlow<SynchronizeState> = _state

    init {
        handleIntent(SynchronizeIntent.SyncStations)
        viewModelScope.launch {
            connectivityObserver.isConnected.collect { isConnected ->
                if (isConnected &&
                    (state.value.synchronizeOperationsStatus == OperationStatus.IDLE ||
                            state.value.synchronizeOperationsStatus == OperationStatus.ERROR)
                ) {
                    handleIntent(SynchronizeIntent.SyncStations)
                }
            }
        }
    }


    fun handleIntent(intent: SynchronizeIntent) {
        when (intent) {
            is SynchronizeIntent.SyncStations -> synchronizeStations()
        }
    }

    private var synchronizationJob: Job? = null

    private fun synchronizeStations() {
        _state.value = _state.value.copy(
            synchronizeOperationsStatus = OperationStatus.LOADING,
        )
        synchronizationJob?.cancel()

        synchronizationJob = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(synchronizeOperationsStatus = OperationStatus.LOADING)
            val result = synchronizeStationsUseCase.call()

            _state.value = when (result) {
                is OperationResult.Success -> {
                    _state.value.copy(
                        synchronizeOperationsStatus = OperationStatus.SUCCESS,
                    )
                }

                is OperationResult.Failure -> _state.value.copy(
                    synchronizeOperationsStatus = OperationStatus.ERROR,
                    errorMessage = result.exception.message
                )
            }

        }
    }


}