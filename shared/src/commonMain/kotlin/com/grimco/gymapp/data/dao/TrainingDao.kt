package com.grimco.gymapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.grimco.gymapp.data.dtos.ExercisePrescription
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.dtos.WorkoutSessionWithExercises
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.GymMachineEntity
import com.grimco.gymapp.data.model.SessionExerciseEntity
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity
import com.grimco.gymapp.data.model.WorkoutSessionEntity
import com.grimco.gymapp.data.model.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TrainingEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExercisesEntity)
    @Query("UPDATE exercises SET machineId = :machineId WHERE id = :exerciseId")
    suspend fun assignMachine(exerciseId: Long, machineId: Long?)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingExercise(trainingExercisesEntity: TrainingExercisesEntity)

    @Query("select * from trainings where id=:id")
    suspend fun getTraining(id: Long): TrainingEntity
    @Query("select * from exercises")
    fun getExercises(): Flow<List<ExercisesEntity>>
    @Query("SELECT * FROM gym_machines ORDER BY name COLLATE NOCASE")
    fun observeMachines(): Flow<List<GymMachineEntity>>
    @Query("SELECT * FROM gym_machines WHERE id = :machineId")
    suspend fun getMachine(machineId: Long): GymMachineEntity?
    @Insert
    suspend fun insertMachine(machine: GymMachineEntity): Long
    @Update
    suspend fun updateMachine(machine: GymMachineEntity)
    @Delete
    suspend fun deleteMachine(machine: GymMachineEntity)
    @Query("select * from trainings")
    fun getAll(): Flow<List<TrainingEntity>>
    @Query("select * from trainings")
    fun getListTest(): List<TrainingEntity>

    @Transaction
    @Query("select * from trainings")
    fun getExerciseList(): Flow<List<TrainingExercise>>

    @Transaction
    @Query("select * from trainings where id = :id")
    fun getExerciseListById(id: Long): Flow<TrainingExercise?>

    @Transaction
    suspend fun addExercisesToTraining(idTraining: Long, exercise: ExercisesEntity) {
        insertTrainingExercise(TrainingExercisesEntity(idTraining, exercise.id))
    }

    @Delete
    suspend fun deleteTrainingExercise(trainingExercisesEntity: TrainingExercisesEntity)

    @Insert
    suspend fun insertSession(session: WorkoutSessionEntity): Long
    @Insert
    suspend fun insertSessionExercise(exercise: SessionExerciseEntity): Long
    @Insert
    suspend fun insertSet(set: WorkoutSetEntity): Long
    @Update
    suspend fun updateSet(set: WorkoutSetEntity)
    @Query("UPDATE workout_sessions SET status = 'COMPLETED', endedAt = :endedAt WHERE id = :sessionId")
    suspend fun finishSession(sessionId: Long, endedAt: Long)
    @Query("DELETE FROM workout_sessions WHERE id = :sessionId")
    suspend fun deleteSession(sessionId: Long)
    @Query("SELECT * FROM workout_sessions WHERE status = 'DRAFT' ORDER BY startedAt DESC LIMIT 1")
    fun observeDraft(): Flow<WorkoutSessionEntity?>
    @Transaction
    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId")
    fun observeSession(sessionId: Long): Flow<WorkoutSessionWithExercises?>
    @Transaction
    @Query("SELECT * FROM workout_sessions WHERE status = 'COMPLETED' ORDER BY startedAt DESC")
    fun observeCompletedSessions(): Flow<List<WorkoutSessionWithExercises>>

    @Transaction
    suspend fun createSession(
        routineName: String,
        exercisePlans: List<ExercisePrescription>,
        startedAt: Long
    ): Long {
        val sessionId = insertSession(WorkoutSessionEntity(
            routineName = routineName,
            startedAt = startedAt
        ))
        exercisePlans.forEachIndexed { index, plan ->
            val exerciseId = insertSessionExercise(SessionExerciseEntity(
                sessionId = sessionId,
                exerciseName = plan.name,
                machineName = plan.machineName,
                position = index
            ))
            repeat(plan.sets.coerceAtLeast(1)) { setIndex ->
                insertSet(
                    WorkoutSetEntity(
                        sessionExerciseId = exerciseId,
                        setNumber = setIndex + 1,
                        reps = plan.reps.coerceAtLeast(1)
                    )
                )
            }
        }
        return sessionId
    }
}
