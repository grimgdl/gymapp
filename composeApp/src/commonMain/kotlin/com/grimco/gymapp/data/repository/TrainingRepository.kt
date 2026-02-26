package com.grimco.gymapp.data.repository

import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity
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


    suspend fun updateTraining(trainingEntity: TrainingEntity) {
        trainingDao.insert(trainingEntity)
    }

    suspend fun addExerciseTraining(idTraining: Long, exercisesEntity: ExercisesEntity) {
        trainingDao.addExercisesToTraining(idTraining, exercisesEntity)
    }

    fun getExercises(): Flow<List<ExercisesEntity>> {
        return trainingDao.getExercises()
    }

    suspend fun deleteTrainingExercise(trainingExercisesEntity: TrainingExercisesEntity) {
        trainingDao.deleteTrainingExercise(trainingExercisesEntity)
    }

}