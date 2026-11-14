package com.grimco.gymapp.data.repository

import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.dtos.ExercisePrescription
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.dtos.WorkoutSessionWithExercises
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.GymMachineEntity
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity
import com.grimco.gymapp.data.model.WorkoutSessionEntity
import com.grimco.gymapp.data.model.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow

class TrainingRepository(private val trainingDao: TrainingDao) {
    fun getTrainingWithExercises(): Flow<List<TrainingExercise>> = trainingDao.getExerciseList()
    fun getTrainingList(): Flow<List<TrainingEntity>> = trainingDao.getAll()

    suspend fun updateImage(idTraining: Long, imagePath: String) {
        trainingDao.getTraining(idTraining).let { training -> trainingDao.insert(training.copy(image = imagePath)) }
    }

    fun getTrainingWithExercisesById(id: Long): Flow<TrainingExercise?> =
        trainingDao.getExerciseListById(id)

    suspend fun insertExercise(exercisesEntity: ExercisesEntity) = trainingDao.insertExercise(exercisesEntity)
    suspend fun updateTraining(trainingEntity: TrainingEntity) = trainingDao.insert(trainingEntity)
    suspend fun addExerciseTraining(idTraining: Long, exercisesEntity: ExercisesEntity) =
        trainingDao.addExercisesToTraining(idTraining, exercisesEntity)
    suspend fun updateExercisePlan(plan: TrainingExercisesEntity) =
        trainingDao.insertTrainingExercise(plan)
    suspend fun assignMachine(exerciseId: Long, machineId: Long?) =
        trainingDao.assignMachine(exerciseId, machineId)
    fun observeMachines(): Flow<List<GymMachineEntity>> = trainingDao.observeMachines()
    suspend fun getMachine(id: Long): GymMachineEntity? = trainingDao.getMachine(id)
    suspend fun createMachine(machine: GymMachineEntity): Long = trainingDao.insertMachine(machine)
    suspend fun updateMachine(machine: GymMachineEntity) = trainingDao.updateMachine(machine)
    suspend fun deleteMachine(machine: GymMachineEntity) = trainingDao.deleteMachine(machine)
    fun getExercises(): Flow<List<ExercisesEntity>> = trainingDao.getExercises()
    suspend fun deleteTrainingExercise(trainingExercisesEntity: TrainingExercisesEntity) =
        trainingDao.deleteTrainingExercise(trainingExercisesEntity)

    fun observeDraft(): Flow<WorkoutSessionEntity?> = trainingDao.observeDraft()
    suspend fun createSession(
        routineName: String,
        exercisePlans: List<ExercisePrescription>,
        startedAt: Long
    ): Long = trainingDao.createSession(routineName, exercisePlans, startedAt)
    fun observeSession(id: Long): Flow<WorkoutSessionWithExercises?> = trainingDao.observeSession(id)
    fun observeCompletedSessions(): Flow<List<WorkoutSessionWithExercises>> =
        trainingDao.observeCompletedSessions()
    suspend fun updateSet(set: WorkoutSetEntity) = trainingDao.updateSet(set)
    suspend fun finishSession(id: Long, endedAt: Long) = trainingDao.finishSession(id, endedAt)
    suspend fun deleteSession(id: Long) = trainingDao.deleteSession(id)
}