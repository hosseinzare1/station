package ir.romina.hossein.core

import android.app.Application
import ir.romina.hossein.core.di.localModule
import ir.romina.hossein.core.di.networkModule
import ir.romina.hossein.features.map.di.mapModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class StationApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StationApplication)
            modules(networkModule, localModule, mapModule)
        }
    }
}