package com.example.rickandmorty.ui.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _character = _id.switchMap { id ->
        repository.getCharacter(id)
    }

    val character: LiveData<Resource<Character>> = _character


    fun start(id: Int) {
        _id.value = id
    }

}
