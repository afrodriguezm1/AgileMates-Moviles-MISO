package com.example.vinilos

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
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
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.vinilos.ui.album.AlbumsAdapter
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.BeforeClass
import java.util.EnumSet.allOf


@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateAlbumTest{
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.fab)).perform(click())
    }

    @Test
    fun testCreateAlbum(){
        val albumName = "test1FromJUnit4"
        val cover = "test1"
        val releaseDate = "2015-20-02"
        val description = "test1"
        val genre = "Salsa"
        val recordLabel = "Sony Music"

        onView(withId(R.id.editTextAlbumName)).perform(ViewActions.replaceText(albumName))
        closeSoftKeyboard()
        onView(withId(R.id.editTextAlbumDescripcion)).perform(ViewActions.replaceText(description))
        closeSoftKeyboard()
        onView(withId(R.id.editTextAlbumFecha)).perform(ViewActions.replaceText(releaseDate))
        closeSoftKeyboard()
        onView(withId(R.id.editTextAlbumPortada)).perform(ViewActions.replaceText(cover))
        closeSoftKeyboard()

        onView(withId(R.id.spinnerAlbumGenero)).perform(click())
        closeSoftKeyboard()
        onView(withText(genre)).perform(click());
        closeSoftKeyboard()
        onView(withId(R.id.spinnerAlbumDisquera)).perform(click())
        closeSoftKeyboard()
        onView(withText(recordLabel)).perform(click());
        onView(withId(R.id.button2)).perform(click())
        //Assertion

    }

    @Test
    fun testCancelCreateAlbum(){
        closeSoftKeyboard()
        onView(withId(R.id.button)).perform(click())
    }
}