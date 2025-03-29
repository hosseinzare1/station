package ir.romina.hossein.features.map.presentation.manager.synchronize


sealed class SynchronizeIntent {
    data object SyncStations : SynchronizeIntent()
}