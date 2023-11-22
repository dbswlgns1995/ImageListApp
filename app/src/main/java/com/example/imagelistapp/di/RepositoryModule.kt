package com.example.imagelistapp.di

import com.example.imagelistapp.data.repository.SearchRepository
import com.example.imagelistapp.data.repository.SearchRepositoryImpl
import com.example.imagelistapp.data.source.remote.service.SearchService
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
    fun providesSearchRepository(
        searchService: SearchService
    ) : SearchRepository = SearchRepositoryImpl(searchService)
}