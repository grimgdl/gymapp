package com.grimco.gymapp.data.di

import androidx.room.RoomDatabase
import com.grimco.gymapp.data.local.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module



actual val platformModule: Module = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder(get())
    }
}
