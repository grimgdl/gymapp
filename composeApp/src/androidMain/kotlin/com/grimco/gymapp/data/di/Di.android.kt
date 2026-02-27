package com.grimco.gymapp.data.di

import androidx.room.RoomDatabase
import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.local.AppDatabase
import com.grimco.gymapp.data.repository.AndroidStorage
import com.grimco.gymapp.data.repository.TrainingRepository
import com.grimco.gymapp.domain.repository.ImageStorage
import com.grimco.gymapp.presentation.viewmodel.EditTrainingViewModel
import com.grimco.gymapp.presentation.viewmodel.MainViewmodel
import com.grimco.gymapp.presentation.viewmodel.TrainingViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


actual val platformModule: Module = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder(get())
    }

    single<TrainingDao> { get<AppDatabase>().trainingDao()}
    single<ImageStorage> { AndroidStorage(get()) }

    singleOf(::TrainingRepository)
    viewModelOf(::MainViewmodel)
    viewModelOf(::TrainingViewModel)
    viewModelOf(::EditTrainingViewModel)
}
