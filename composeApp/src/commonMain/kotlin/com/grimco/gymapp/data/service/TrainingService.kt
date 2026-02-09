package com.grimco.gymapp.data.service

import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.model.TrainingEntity
import kotlinx.coroutines.flow.Flow


class TrainingService(
    private val trainingDao: TrainingDao
) {
    fun getAllTrainings(): Flow<List<TrainingEntity>> {
        return trainingDao.getAll()
    }

    fun getAllTrainingTest(): List<TrainingEntity> {
        return trainingDao.getListTest()
    }
}