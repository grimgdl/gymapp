package com.grimco.gymapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TrainingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExercisesEntity)
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


}