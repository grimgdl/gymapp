package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.grimco.gymapp.data.service.TrainingService

class MainViewmodel(
    private val service: TrainingService
): ViewModel() {

    init {
        println("list")

    }

}