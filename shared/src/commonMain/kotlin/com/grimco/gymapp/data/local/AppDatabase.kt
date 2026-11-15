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
    version = 5
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

private object Migration4To5 : Migration(4, 5) {
    override fun migrate(connection: SQLiteConnection) {
        connection.prepare(
            "ALTER TABLE training_exercises ADD COLUMN machineId INTEGER REFERENCES gym_machines(id) ON UPDATE NO ACTION ON DELETE SET NULL"
        ).use { it.step() }
        connection.prepare(
            "CREATE INDEX IF NOT EXISTS index_training_exercises_machineId ON training_exercises(machineId)"
        ).use { it.step() }
        connection.prepare(
            "UPDATE training_exercises SET machineId = (SELECT machineId FROM exercises WHERE exercises.id = training_exercises.idExercise)"
        ).use { it.step() }

        connection.prepare("DROP INDEX IF EXISTS index_exercises_machineId").use { it.step() }
        connection.prepare("ALTER TABLE exercises RENAME TO exercises_v4").use { it.step() }
        connection.prepare(
            "CREATE TABLE exercises_v5 (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL)"
        ).use { it.step() }
        connection.prepare(
            "INSERT INTO exercises_v5 (id, name) SELECT id, name FROM exercises_v4"
        ).use { it.step() }
        connection.prepare("DROP TABLE exercises_v4").use { it.step() }
        connection.prepare("ALTER TABLE exercises_v5 RENAME TO exercises").use { it.step() }
    }
}

fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .addMigrations(Migration1To2, Migration2To3, Migration3To4, Migration4To5)
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

fun getTrainingDao(appDatabase: AppDatabase): TrainingDao = appDatabase.trainingDao()
