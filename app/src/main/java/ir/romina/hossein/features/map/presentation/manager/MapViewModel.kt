package ir.romina.hossein.features.map.presentation.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.features.map.domain.usecases.GetStationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(
    private val getStationsUseCase: GetStationsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state

    init {
        handleIntent(MapIntent.LoadStations)
    }

    fun handleIntent(intent: MapIntent) {
        when (intent) {
            is MapIntent.LoadStations -> loadStations("")
            is MapIntent.FilterStations -> filterStations(intent.filter)
            is MapIntent.SelectStation -> selectStation(intent.stationId)
        }
    }

    private fun selectStation(stationId: String) {
        _state.value = _state.value.copy(selectedStationId = stationId)
    }

    private fun filterStations(searchText: String) {
        loadStations(searchText)
    }

    private var loadStationsJob: Job? = null

    private fun loadStations(searchText: String) {
        loadStationsJob?.cancel()
        _state.value = _state.value.copy(operationStatus = OperationStatus.LOADING)
        loadStationsJob = viewModelScope.launch(Dispatchers.IO) {
            getStationsUseCase.call(searchText).collect { stationsResult ->
                _state.value = when (stationsResult) {
                    is OperationResult.Success -> {
                        _state.value.copy(
                            operationStatus = OperationStatus.SUCCESS,
                            stations = stationsResult.data
                        )
                    }

                    is OperationResult.Failure -> _state.value.copy(
                        operationStatus = OperationStatus.ERROR,
                        errorMessage = stationsResult.exception.message,
                        stations = stationsResult.data ?: _state.value.stations
                    )
                }
            }
        }
    }


}