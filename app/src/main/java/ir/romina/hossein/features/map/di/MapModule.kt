package ir.romina.hossein.features.map.di

import ir.romina.hossein.core.database.AppDatabase
import ir.romina.hossein.features.map.data.remote.datasources.StationRemoteDataSource
import ir.romina.hossein.features.map.data.repositories.StationRepositoryImpl
import ir.romina.hossein.features.map.domain.repositories.StationRepository
import ir.romina.hossein.features.map.domain.usecases.GetStationsUseCase
import ir.romina.hossein.features.map.domain.usecases.SynchronizeStationsUseCase
import ir.romina.hossein.features.map.presentation.manager.map.MapViewModel
import ir.romina.hossein.features.map.presentation.manager.synchronize.SynchronizeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mapModule = module {
    single {
        StationRemoteDataSource(get())
    }

    single {
        get<AppDatabase>().stationDao()
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
    factory {
        SynchronizeStationsUseCase(get())
    }

    viewModel { MapViewModel(get()) }
    viewModel { SynchronizeViewModel(get(), get()) }
}