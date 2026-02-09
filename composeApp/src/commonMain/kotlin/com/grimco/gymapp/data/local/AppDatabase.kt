package com.grimco.gymapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.model.TrainingEntity

@Database(entities = [TrainingEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
}


