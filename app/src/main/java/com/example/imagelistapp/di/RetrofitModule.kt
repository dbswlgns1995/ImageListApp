package com.example.imagelistapp.di

import com.example.imagelistapp.MyApplication
import com.example.imagelistapp.R
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .create()
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            connectTimeout(5, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Client-ID ${MyApplication.appContext.getString(R.string.auth_key)}")
                    .build()
                chain.proceed(newRequest)
            }
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient.Builder,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MyApplication.appContext.getString(R.string.base_url))
            .addConverterFactory(gsonConverterFactory)
            .client(client.build())
            .build()
    }

}