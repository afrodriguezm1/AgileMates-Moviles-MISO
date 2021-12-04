package com.example.vinilos


import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ColeccionistaListTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testColeccionistaLis(){
        //Validar que hay al menos un registro con los datos completos
        //Prerrequesitos: Se asume que existe al menos un registro en la lista de albums

        Thread.sleep(1000);

        //Va a la lista de coleccionistas
        onView(withId(R.id.navigation_collectors)).perform(ViewActions.click())
        Thread.sleep(5000)

        //Valida que existe la lista de coleccionistas
        onView(withId(R.id.collectorRv)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.collectorRv)).check(matches(UtilityTest.atPosition(0, hasDescendant(withText("Manolo Bellon")))))
    }
}
