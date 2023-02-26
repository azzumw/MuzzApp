package com.example.muzzapp

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTests {

    @Test
    fun isToolbarTitleDisplayed() {
        val scenario = launch(MainActivity::class.java)

        onView(isAssignableFrom(Toolbar::class.java)).check(matches(hasDescendant(withText(R.string.app_name))))

        scenario.close()
    }

    @Test
    fun typeHello_displaysHelloInChat() {
        val scenario = launch(MainActivity::class.java)

        onView(withId(R.id.edit_messagebox)).perform(typeText("Hello"), closeSoftKeyboard())

        onView(withId(R.id.send_button)).perform(click())

        onView(withId(R.id.chat_recycler_view))
            .check(matches(hasDescendant(withText("Hello"))))

        scenario.close()
    }
}