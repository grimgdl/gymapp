package com.grimco.gymapp.data.di

import androidx.room.RoomDatabase
import com.grimco.gymapp.data.local.AppDatabase
import com.grimco.gymapp.data.service.TrainingService
import com.grimco.gymapp.presentation.viewmodel.MainViewmodel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module



actual val platformModule: Module = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder(get())
    }

    viewModelOf(::MainViewmodel)
    singleOf(::TrainingService)
}
