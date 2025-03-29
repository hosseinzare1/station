package ir.romina.hossein.core.di

import ir.romina.hossein.core.utils.ConnectivityObserver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val utilModule = module {
    single<ConnectivityObserver> {
        ConnectivityObserver(androidContext())
    }
}