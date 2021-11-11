package com.example.vinilos

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.vinilos.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.recyclerview.widget.RecyclerView

import androidx.test.espresso.matcher.BoundedMatcher

import androidx.annotation.NonNull
import org.hamcrest.Description
import org.hamcrest.Matcher


@LargeTest
@RunWith(AndroidJUnit4::class)

class MainActivityTest{
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testListAlbum(){
        //Validar que hay al menos un registro con los datos completos
        //Prerrequesitos: Se asume que existe al menos un registro en la lista de albums

        Thread.sleep(5000);

        //Valida si la lista de albumes esta renderizada
        onView(withId(R.id.albumsRv)).check(matches(ViewMatchers.isDisplayed()))

        //Valida si el primer item de la lista esta renderizado y se muestra el nombre.
        // este segundo test debe estar en otra funcion con el @test
        onView(withId(R.id.albumsRv)).check(matches(atPosition(0, hasDescendant(withText("Buscando América")))))

        //Espresso.closeSoftKeyboard()
        //onView(withId(R.id.button2)).perform(click())

    }
    //TODO: Enviar a una clase utilitaria porque esta la podemos usar en todos los test.
    // La tome de starkoverflow
    fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
        checkNotNull(itemMatcher)
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}

