package com.dicoding.asclepius.core.data.source.remote.response

import com.google.gson.annotations.SerializedName


data class HealthNewsResponse(
	@SerializedName("articles")
    val articles: List<Article>,
	@SerializedName("status")
    val status: String,
	@SerializedName("totalResults")
    val totalResults: Int
)