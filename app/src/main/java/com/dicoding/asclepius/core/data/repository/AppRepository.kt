package com.dicoding.asclepius.core.data.repository

import com.dicoding.asclepius.core.data.source.local.AppDao
import com.dicoding.asclepius.core.data.source.local.entity.PredictionResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val dao: AppDao) {
	fun getAllHistory() = dao.getAllHistory()
	suspend fun insertHistory(prediction: PredictionResult) = dao.insertPrediction(prediction)
	suspend fun deleteHistory(prediction: PredictionResult) = dao.deletePrediction(prediction)
}