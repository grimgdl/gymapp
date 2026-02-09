package com.grimco.gymapp.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


fun getDatabaseBuilder(context: Context): AppDatabase{
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("my_training.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    ).build()
}

fun getRoomDatabase(context: Context): AppDatabase
    = getDatabaseBuilder(context = context)