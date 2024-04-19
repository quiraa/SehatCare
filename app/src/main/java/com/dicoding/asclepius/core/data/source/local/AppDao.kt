package com.dicoding.asclepius.core.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.core.data.source.local.entity.PredictionResult
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

	@Query("SELECT * FROM prediction")
	fun getAllHistory(): Flow<List<PredictionResult>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertPrediction(prediction: PredictionResult)

	@Delete
	suspend fun deletePrediction(prediction: PredictionResult)
}