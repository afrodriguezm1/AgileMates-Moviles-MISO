package com.example.vinilos

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.vinilos.ui.createAlbum.CreateAlbum
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

import androidx.test.espresso.matcher.ViewMatchers.isClickable

import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import java.util.EnumSet.allOf


@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateAlbumTest{
    private lateinit var scenario: ActivityScenario<CreateAlbum>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testCreateAlbum(){
        val albumName = "test1FromJUnit4"
        val cover = "test1"
        val recordLabel = "Salsa"
        val releaseDate = "test1"
        val genre = "Sony Music"
        val description = "test1"

        //Espresso Matcher and Action
        onView(withId(R.id.editTextAlbumName)).perform(typeText(albumName))
        onView(withId(R.id.editTextAlbumDescripcion)).perform(typeText(description))
        onView(withId(R.id.editTextAlbumFecha)).perform(typeText(releaseDate))
        //onView(withId(R.id.spinnerAlbumDisquera)).perform(typeText(recordLabel))
        //onView(withId(R.id.spinnerAlbumGenero)).perform(typeText(genre))
        onView(withId(R.id.editTextAlbumPortada)).perform(typeText(cover))

        //Espresso.closeSoftKeyboard()
        onView(withId(R.id.button2)).perform(click())

        //Assertion
        //onView(withId(R.id.editTextAlbumName)).check(matches(withText(albumName)))
        //onView(withId(R.id.editTextAlbumName)).check(matches(isDisplayed()))
    }



}