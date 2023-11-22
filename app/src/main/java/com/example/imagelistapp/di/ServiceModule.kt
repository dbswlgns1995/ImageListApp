package com.example.imagelistapp.di

import com.example.imagelistapp.data.source.remote.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun providesContentService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)
}