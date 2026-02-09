package com.grimco.gymapp.data.di

import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.local.AppDatabase
import com.grimco.gymapp.data.local.getRoomDatabase
import com.grimco.gymapp.data.service.TrainingService
import com.grimco.gymapp.presentation.viewmodel.MainViewmodel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual fun getModule(): Module = module {
    viewModelOf(::MainViewmodel)
    singleOf(::TrainingService)
    single {
        getRoomDatabase(get())
    }
    single { get<AppDatabase>().trainingDao() }

}