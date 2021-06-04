package com.example.rickandmorty

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.ui.characterdetail.CharacterDetailFragment
import com.example.rickandmorty.ui.characterdetail.CharacterDetailFragmentArgs
import com.example.rickandmorty.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@HiltAndroidTest
class CharaterDetailPageTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: CharacterRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun activityDisplayed_UI() {
        // WHEN - Details fragment launched to display task
        val bundle = CharacterDetailFragmentArgs(1).toBundle()
        launchFragmentInHiltContainer<CharacterDetailFragment>(bundle, R.style.AppTheme)
        onView(withId(R.id.character_detail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}