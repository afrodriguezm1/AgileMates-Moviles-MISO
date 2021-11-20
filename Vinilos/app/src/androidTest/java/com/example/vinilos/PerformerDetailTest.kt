package com.example.vinilos

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)

class PerformerDetailTest{
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testDetailPerformer(){
        //Validar que hay al menos un registro con los datos completos
        //Prerrequesitos: Se asume que existe al menos un registro en la lista de artistas

        Thread.sleep(1000);
        onView(withId(R.id.navigation_performers)).perform(ViewActions.click())
        Thread.sleep(5000);

        //Valida si la lista de artistas esta renderizada
        onView(withId(R.id.performersRv)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.textViewPerformerName)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.textViewCreationDate)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.scrollAlbumList)).check(matches(ViewMatchers.isDisplayed()))

        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.performersRv)).check(matches(ViewMatchers.isDisplayed()))
    }
}
