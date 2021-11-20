package com.example.vinilos

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)

class PerformersListTest{
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testListAlbum(){
        //Validar que hay al menos un registro con los datos completos
        //Prerrequesitos: Se asume que existe al menos un registro en la lista de artistas

        Thread.sleep(1000);
        onView(withId(R.id.navigation_performers)).perform(ViewActions.click())
        Thread.sleep(5000);

        //Valida si la lista de artistas esta renderizada
        onView(withId(R.id.performersRv)).check(matches(ViewMatchers.isDisplayed()))

        //Valida si el primer item de la lista esta renderizado y se muestra el nombre.
        onView(withId(R.id.performersRv)).check(matches(UtilityTest.atPosition(0, hasDescendant(withText("Rubén Blades Bellido de Luna")))))
    }
}
