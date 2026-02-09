package com.grimco.gymapp.data.di

import com.grimco.gymapp.presentation.viewmodel.MainViewmodel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual fun getModule(): Module = module {
    viewModelOf(::MainViewmodel)
}