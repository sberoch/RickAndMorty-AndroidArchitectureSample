package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty.utils.performGetOperation
import com.example.rickandmorty.utils.wrapEspressoIdlingResource
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao
) {

    fun getCharacter(id: Int) = wrapEspressoIdlingResource {
            performGetOperation(
                databaseQuery = { localDataSource.getCharacter(id) },
                networkCall = { remoteDataSource.getCharacter(id) },
                saveCallResult = { localDataSource.insert(it) }
            )
        }

    fun getCharacters() = wrapEspressoIdlingResource {
        performGetOperation(
            databaseQuery = { localDataSource.getAllCharacters() },
            networkCall = { remoteDataSource.getCharacters() },
            saveCallResult = { localDataSource.insertAll(it.results) }
        )
    }
}