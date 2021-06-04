package com.example.rickandmorty

import DummyObject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.runner.screenshot.Screenshot.capture
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.ui.characters.CharactersViewModel
import com.example.rickandmorty.utils.Resource
import com.example.rickandmorty.utils.getOrAwaitValue
import com.example.rickandmorty.utils.observeForTesting
import com.google.common.truth.Truth
import com.jraska.livedata.test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class CharactersViewModelTest : BaseTest() {

    override fun preTest() {
        testedObject = CharactersViewModel(charRepository)
    }

    // Subject under test
    private lateinit var testedObject: CharactersViewModel

    @RelaxedMockK
    private lateinit var charRepository: CharacterRepository

    @Test
    fun getAllChars() {
        val expected = MutableLiveData(Resource.success(DummyObject.fakeListChar))
        every {
            charRepository.getCharacters()
        }.returns(expected)
        testedObject.start()
        val obs = testedObject.characters.test()
        obs.assertHasValue()
            .assertValue {
                it == Resource.success(DummyObject.fakeListChar)
            }
    }

}
