package com.dicoding.asclepius.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.asclepius.core.data.source.local.entity.PredictionResult

@Database(
	entities = [PredictionResult::class],
	version = 1,
	exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
	abstract val dao: AppDao

	companion object {
		const val databaseName = "history_database.db"
	}
}