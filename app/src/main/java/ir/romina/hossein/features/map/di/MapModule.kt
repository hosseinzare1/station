package ir.romina.hossein.features.map.di

import ir.romina.hossein.features.map.data.remote.datasources.StationRemoteDataSource
import ir.romina.hossein.features.map.data.repositories.StationRepositoryImpl
import ir.romina.hossein.features.map.domain.repositories.StationRepository
import ir.romina.hossein.features.map.domain.usecases.GetStationsUseCase
import ir.romina.hossein.features.map.presentation.manager.MapViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mapModule = module {
    single {
        StationRemoteDataSource(get())
    }
    single<StationRepository> {
        StationRepositoryImpl(
            get(),
            get(),
        )
    }
    factory {
        GetStationsUseCase(get())
    }
    viewModel { MapViewModel(get()) }
}