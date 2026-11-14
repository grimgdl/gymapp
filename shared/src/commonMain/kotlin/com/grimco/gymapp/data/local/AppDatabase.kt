package com.grimco.gymapp.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.GymMachineEntity
import com.grimco.gymapp.data.model.SessionExerciseEntity
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity
import com.grimco.gymapp.data.model.WorkoutSessionEntity
import com.grimco.gymapp.data.model.WorkoutSetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

const val DATABASE_NAME = "my_training.db"

@Database(
    entities = [
        TrainingEntity::class,
        ExercisesEntity::class,
        TrainingExercisesEntity::class,
        WorkoutSessionEntity::class,
        SessionExerciseEntity::class,
        WorkoutSetEntity::class,
        GymMachineEntity::class
    ],
    version = 4
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
}

@Suppress("kotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

private object Migration1To2 : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        listOf(
            "CREATE TABLE IF NOT EXISTS workout_sessions (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, routineName TEXT NOT NULL, startedAt INTEGER NOT NULL, endedAt INTEGER, status TEXT NOT NULL)",
            "CREATE TABLE IF NOT EXISTS session_exercises (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, sessionId INTEGER NOT NULL, exerciseName TEXT NOT NULL, position INTEGER NOT NULL, FOREIGN KEY(sessionId) REFERENCES workout_sessions(id) ON UPDATE NO ACTION ON DELETE CASCADE)",
            "CREATE INDEX IF NOT EXISTS index_session_exercises_sessionId ON session_exercises(sessionId)",
            "CREATE TABLE IF NOT EXISTS workout_sets (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, sessionExerciseId INTEGER NOT NULL, setNumber INTEGER NOT NULL, reps INTEGER NOT NULL, weightKg REAL NOT NULL, completed INTEGER NOT NULL, FOREIGN KEY(sessionExerciseId) REFERENCES session_exercises(id) ON UPDATE NO ACTION ON DELETE CASCADE)",
            "CREATE INDEX IF NOT EXISTS index_workout_sets_sessionExerciseId ON workout_sets(sessionExerciseId)"
        ).forEach { sql ->
            connection.prepare(sql).use { it.step() }
        }
    }
}

private object Migration2To3 : Migration(2, 3) {
    override fun migrate(connection: SQLiteConnection) {
        connection.prepare(
            "ALTER TABLE training_exercises ADD COLUMN plannedSets INTEGER NOT NULL DEFAULT 3"
        ).use { it.step() }
        connection.prepare(
            "ALTER TABLE training_exercises ADD COLUMN plannedReps INTEGER NOT NULL DEFAULT 10"
        ).use { it.step() }
    }
}

private object Migration3To4 : Migration(3, 4) {
    override fun migrate(connection: SQLiteConnection) {
        connection.prepare(
            "CREATE TABLE IF NOT EXISTS gym_machines (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, notes TEXT, imagePath TEXT)"
        ).use { it.step() }
        connection.prepare(
            "ALTER TABLE exercises ADD COLUMN machineId INTEGER REFERENCES gym_machines(id) ON UPDATE NO ACTION ON DELETE SET NULL"
        ).use { it.step() }
        connection.prepare(
            "CREATE INDEX IF NOT EXISTS index_exercises_machineId ON exercises(machineId)"
        ).use { it.step() }
        connection.prepare(
            "ALTER TABLE session_exercises ADD COLUMN machineName TEXT"
        ).use { it.step() }
    }
}

fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .addMigrations(Migration1To2, Migration2To3, Migration3To4)
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

fun getTrainingDao(appDatabase: AppDatabase): TrainingDao = appDatabase.trainingDao()
