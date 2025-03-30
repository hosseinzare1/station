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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SynchronizeViewModel(
    private val synchronizeStationsUseCase: SynchronizeStationsUseCase,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private val _state = MutableStateFlow(SynchronizeState())
    val state: StateFlow<SynchronizeState> = _state

    private var synchronizationJob: Job? = null

    init {
        handleIntent(SynchronizeIntent.SyncStations)
        viewModelScope.launch {
            connectivityObserver.isConnected.map { isConnected ->
                isConnected && (state.value.synchronizeOperationsStatus == OperationStatus.IDLE || state.value.synchronizeOperationsStatus == OperationStatus.ERROR)
            }.distinctUntilChanged().collect { shouldSync ->
                if (shouldSync) synchronizeStations()
            }
        }
    }


    fun handleIntent(intent: SynchronizeIntent) {
        when (intent) {
            is SynchronizeIntent.SyncStations -> synchronizeStations()
        }
    }


    private fun synchronizeStations() {
        synchronizationJob?.cancel()

        _state.update { it.copy(synchronizeOperationsStatus = OperationStatus.LOADING) }

        synchronizationJob = viewModelScope.launch(Dispatchers.IO) {
            val result = synchronizeStationsUseCase.call()
            _state.update {
                when (result) {
                    is OperationResult.Success -> {
                        it.copy(
                            synchronizeOperationsStatus = OperationStatus.SUCCESS,
                        )
                    }

                    is OperationResult.Failure -> it.copy(
                        synchronizeOperationsStatus = OperationStatus.ERROR,
                        errorMessage = result.exception.message
                    )
                }
            }
        }
    }
}