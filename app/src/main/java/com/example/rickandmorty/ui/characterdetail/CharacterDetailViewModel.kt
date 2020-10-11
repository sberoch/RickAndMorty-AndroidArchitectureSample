package com.example.rickandmorty.ui.characterdetail

import androidx.lifecycle.*
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.utils.Resource
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class CharacterDetailViewModel @AssistedInject constructor(
    repository: CharacterRepository,
    @Assisted val characterId: Int
) : ViewModel() {

    private val _character = repository.getCharacter(characterId)
    val character: LiveData<Resource<Character>> = _character

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(characterId: Int): CharacterDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            characterId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(characterId) as T
            }
        }
    }
}
