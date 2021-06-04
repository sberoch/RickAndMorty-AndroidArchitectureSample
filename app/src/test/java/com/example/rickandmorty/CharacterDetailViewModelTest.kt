package com.example.rickandmorty

import DummyObject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.ui.characterdetail.CharacterDetailViewModel
import com.example.rickandmorty.utils.Resource
import com.example.rickandmorty.utils.observeForTesting
import com.jraska.livedata.test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Test

class CharacterDetailViewModelTest : BaseTest() {
    @RelaxedMockK
    lateinit var repo: CharacterRepository
    lateinit var vm: CharacterDetailViewModel

    @RelaxedMockK
    lateinit var data: CharacterRemoteDataSource
    override fun preTest() {
        vm = CharacterDetailViewModel(repo)
    }

    @Test
    fun detail() {
        val expected: Resource<Character> = Resource.success(DummyObject.fakeCharacter)
        every {
            repo.getCharacter(1)
        } returns MutableLiveData(expected)
        vm.start(1)
        val obs = vm.character.test()
        obs.assertHasValue()
            .assertValue {
                it == expected
            }
    }
}