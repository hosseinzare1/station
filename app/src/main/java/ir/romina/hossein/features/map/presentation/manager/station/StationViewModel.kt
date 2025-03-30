package ir.romina.hossein.features.map.presentation.manager.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.romina.hossein.core.base.domain.OperationResult
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.features.map.domain.usecases.GetStationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StationViewModel(
    private val getStationsUseCase: GetStationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StationState())
    val state: StateFlow<StationState> = _state.asStateFlow()

    private val _searchQuery = MutableSharedFlow<String>(replay = 1)

    @OptIn(FlowPreview::class)
    private val debouncedQuery = _searchQuery
        .debounce(250L)
        .distinctUntilChanged()
        .onEach { query -> loadStations(query) }
        .launchIn(viewModelScope)

    init {
        handleIntent(StationIntent.LoadStations)
    }

    fun handleIntent(intent: StationIntent) {
        when (intent) {
            is StationIntent.LoadStations -> loadStations("")
            is StationIntent.UpdateSearchQuery -> _searchQuery.tryEmit(intent.searchQuery)
            is StationIntent.SelectStation -> _state.update { it.copy(selectedStationId = intent.stationId) }
        }
    }

    private var loadStationsJob: Job? = null

    private fun loadStations(searchText: String) {
        loadStationsJob?.cancel()

        _state.update { it.copy(operationStatus = OperationStatus.LOADING) }

        loadStationsJob = viewModelScope.launch(Dispatchers.IO) {
            getStationsUseCase.call(searchText).collect { stationsResult ->
                _state.update {
                    when (stationsResult) {
                        is OperationResult.Success -> it.copy(
                            operationStatus = OperationStatus.SUCCESS,
                            stations = stationsResult.data
                        )

                        is OperationResult.Failure -> it.copy(
                            operationStatus = OperationStatus.ERROR,
                            errorMessage = stationsResult.exception.message,
                            stations = stationsResult.data ?: it.stations
                        )
                    }
                }
            }
        }
    }
}