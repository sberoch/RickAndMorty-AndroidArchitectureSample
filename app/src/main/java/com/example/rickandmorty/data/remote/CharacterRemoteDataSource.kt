package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.OpenForTesting
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService
): BaseDataSource() {

    suspend fun getCharacters() = getResult { characterService.getAllCharacters() }
    suspend fun getCharacter(id: Int) = getResult { characterService.getCharacter(id) }
}