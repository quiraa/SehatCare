package com.dicoding.asclepius.core.di

import com.dicoding.asclepius.core.data.repository.AppRepository
import com.dicoding.asclepius.core.data.source.local.AppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

	@Provides
	@ViewModelScoped
	fun provideRepository(dao: AppDao) = AppRepository(dao)
}