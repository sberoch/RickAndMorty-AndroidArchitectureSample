package com.example.rickandmorty.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.bumptech.glide.load.engine.Resource
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _invoke = MutableLiveData<Boolean>()


    fun start() {
        _invoke.value = true
    }

    var _char = _invoke.switchMap {
        repository.getCharacters()
    }

    val characters: LiveData<com.example.rickandmorty.utils.Resource<List<Character>>> = _char
}
