package ir.romina.hossein.features.map.presentation.manager.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.features.map.domain.usecases.GetStationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StationViewModel(
    private val getStationsUseCase: GetStationsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(StationState())
    val state: StateFlow<StationState> = _state

    private val _searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    private val debouncedQuery = _searchQuery
        .debounce(250L)
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Lazily, "")

    init {
        handleIntent(StationIntent.LoadStations)

        viewModelScope.launch {
            debouncedQuery.collect { query ->
                loadStations(query)
            }
        }
    }

    fun handleIntent(intent: StationIntent) {
        when (intent) {
            is StationIntent.LoadStations -> loadStations("")
            is StationIntent.UpdateSearchQuery -> updateSearchQuery(intent.searchQuery)
            is StationIntent.SelectStation -> selectStation(intent.stationId)
        }
    }

    private fun selectStation(stationId: String) {
        _state.value = _state.value.copy(selectedStationId = stationId)
    }

    private fun updateSearchQuery(searchText: String) {
        _searchQuery.value = searchText
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