package ir.romina.hossein.features.map.presentation.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.features.map.domain.usecases.GetStationsUseCase
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
            is MapIntent.LoadStations -> loadStations()
            is MapIntent.FilterStations -> filterStations(intent.filter)
            is MapIntent.SelectStation -> selectStation(intent.stationId)
        }
    }

    private fun selectStation(stationId: String) {
        _state.value = _state.value.copy(selectedStationId = stationId)
    }

    private fun filterStations(filter: String) {
        TODO("Not yet implemented")
    }

    private fun loadStations() {
        _state.value = _state.value.copy(operationStatus = OperationStatus.LOADING)
        viewModelScope.launch {
            val stationsResult = getStationsUseCase.call(Unit)
            _state.value = when (stationsResult) {
                is OperationResult.Success -> {
                    val maxCapacity = stationsResult.data.maxByOrNull { it.capacity }?.capacity
                    _state.value.copy(
                        operationStatus = OperationStatus.SUCCESS,
                        stations = stationsResult.data,
                        maxCapacity = maxCapacity
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