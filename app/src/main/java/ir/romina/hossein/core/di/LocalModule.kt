package ir.romina.hossein.core.di

import androidx.room.Room
import ir.romina.hossein.core.constant.DBConst
import ir.romina.hossein.core.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, DBConst.DATABASE_NAME
        ).build()
    }
}