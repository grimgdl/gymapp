package com.grimco.gymapp.data.repository

import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.dtos.TrainingExercise
import kotlinx.coroutines.flow.Flow

class TrainingRepository(
    private val trainingDao: TrainingDao
) {
    fun getTrainingWithExercises(): Flow<List<TrainingExercise>> {
        return trainingDao.getExerciseList()
    }

    fun getTrainingWithExercisesById(id: Long): Flow<TrainingExercise?> {
        return trainingDao.getExerciseListById(id)
    }
}