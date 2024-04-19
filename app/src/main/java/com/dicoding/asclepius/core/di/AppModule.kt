package com.dicoding.asclepius.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.asclepius.core.data.source.local.AppDao
import com.dicoding.asclepius.core.data.source.local.AppDatabase
import com.dicoding.asclepius.core.data.source.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun providesApi() : ApiService {
		val client = OkHttpClient.Builder()
			.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
			.connectTimeout(120, TimeUnit.SECONDS)
			.readTimeout(120, TimeUnit.SECONDS)
			.build()

		val retrofit = Retrofit.Builder()
			.baseUrl(ApiService.baseUrl)
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()

		return retrofit.create(ApiService::class.java)
	}

	@Provides
	@Singleton
	fun providesLocalDatabase(
		@ApplicationContext context: Context
	): AppDatabase {
		return Room.databaseBuilder(
			context,
			AppDatabase::class.java,
			AppDatabase.databaseName
		).fallbackToDestructiveMigration().build()
	}

	@Provides
	fun providesDao(database: AppDatabase): AppDao = database.dao
}