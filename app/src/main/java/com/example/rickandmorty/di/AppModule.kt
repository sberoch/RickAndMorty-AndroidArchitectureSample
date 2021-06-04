package com.example.rickandmorty.di

import android.content.Context
import com.example.rickandmorty.data.local.AppDatabase
import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty.data.remote.CharacterService
import com.example.rickandmorty.data.repository.CharacterRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService = retrofit.create(CharacterService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService) = CharacterRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: CharacterRemoteDataSource,
                          localDataSource: AppDatabase) =
        CharacterRepository(remoteDataSource, localDataSource.characterDao())
}