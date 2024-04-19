package com.dicoding.asclepius.core.data.source.remote

import com.dicoding.asclepius.core.data.source.remote.response.HealthNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

	@GET("top-headlines")
	suspend fun getHealthTopHeadlines(
		@Query("q") query: String = "cancer",
		@Query("category") category: String = "health",
		@Query("language") language: String = "en",
		@Query("apiKey") apiKey: String = "a2b263d83e5d463dacfad1a07c402fe3"
	) : HealthNewsResponse

	companion object {
		const val baseUrl = "https://newsapi.org/v2/"
	}
}