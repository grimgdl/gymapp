package com.grimco.gymapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.grimco.gymapp.data.model.TrainingEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TrainingDao {
    @Insert
    suspend fun insert(item: TrainingEntity)
    @Query("select * from trainingentity")
    fun getAll(): Flow<List<TrainingEntity>>

    @Query("select * from trainingentity")
    fun getListTest(): List<TrainingEntity>
}