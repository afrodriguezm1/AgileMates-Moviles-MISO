package com.example.vinilos

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.example.vinilos.ui.album.AlbumsAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class UtilityTest {
    companion object {
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
}