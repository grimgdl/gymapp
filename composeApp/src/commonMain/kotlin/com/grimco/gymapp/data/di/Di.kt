package com.grimco.gymapp.data.di

import com.grimco.gymapp.data.local.getRoomDatabase
import com.grimco.gymapp.data.local.getTrainingDao
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


expect val platformModule : Module


val databaseModule = module {
    singleOf(::getRoomDatabase)
    singleOf(::getTrainingDao)
}


