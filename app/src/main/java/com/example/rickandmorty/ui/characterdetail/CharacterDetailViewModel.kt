package com.example.rickandmorty.ui.characterdetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.utils.Resource
import java.lang.Exception

class CharacterDetailViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    repository: CharacterRepository
) : ViewModel() {

    val character: LiveData<Resource<Character>> =
        repository.getCharacter(
            savedStateHandle.get<Int>("id")
                ?: throw Exception("You need to pass character id")
        )
}
