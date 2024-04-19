package com.dicoding.asclepius.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prediction")
data class PredictionResult(
	@PrimaryKey
	val id: Int? = 0,
	val result: String? = "",
	val predictionPercentage: String? = "",
	val image: String = "",
)
