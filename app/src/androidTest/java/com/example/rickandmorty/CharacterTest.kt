package com.example.rickandmorty

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.ui.MainActivity
import com.example.rickandmorty.ui.characters.CharacterViewHolder
import com.example.rickandmorty.utils.DataBindingIdlingResource
import com.example.rickandmorty.utils.EspressoIdlingResource
import com.example.rickandmorty.utils.Resource
import com.example.rickandmorty.utils.monitorActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class CharacterPageTest {
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var viewModel: CharacterRepository

    @Before
    fun init(){
        hiltRule.inject()
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    @Test
    fun `checkLaunchDetail`() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        doViewActionWithId(R.id.characters_rv){
            val recyclerView = it as RecyclerView
            val adapter = recyclerView.adapter
            assertThat("list is not empty",adapter!!.itemCount > 1)
        }
        onView(withId(R.id.characters_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CharacterViewHolder>(0,
                doViewActionWithId(R.id.root){
                    it.performClick()
                }))

        //verify
        onView(withId(R.id.character_detail)).check(matches(isDisplayed()))
        //close
        activityScenario.close()
    }
}


fun doViewActionWithId(id: Int,action: (view : View) -> Unit): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Click on a child view with specified id."
        }

        override fun perform(uiController: UiController, v: View) {
            val view = v.findViewById<View>(id)
            action(view)
        }
    }
}