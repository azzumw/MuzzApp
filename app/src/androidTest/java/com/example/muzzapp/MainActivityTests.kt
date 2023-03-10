package com.example.muzzapp

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.action.ViewActions.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.muzzapp.util.*
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass

@RunWith(AndroidJUnit4::class)
class MainActivityTests {

    companion object {

        private lateinit var uiDevice: UiDevice

        @BeforeClass
        @JvmStatic
        fun setDevicePreferences() {
            //set up animations settings = off
            uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            uiDevice.executeShellCommand(ANIMATION_OFF)
            uiDevice.executeShellCommand(TRANS_ANIMATION_OFF)
            uiDevice.executeShellCommand(WIN_ANIMATION_OFF)
        }
    }


    @Before
    fun setUp() {
        ServiceLocator.resetRepository()
    }

    @After
    fun tearDown() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun isToolbarTitleDisplayed() {
        val scenario = launch(MainActivity::class.java)

        onView(isAssignableFrom(Toolbar::class.java)).check(matches(hasDescendant(withText(R.string.frag_title_user_name_you))))

        scenario.close()
    }

    @Test
    fun sender_user_types_hello_displays_Hello_In_Chat_Window() {
        val scenario = launch(MainActivity::class.java)

        editMessageBox.typetext("Hello")

        sendButton.click()

        chatRecyclerView
            .check(matches(hasDescendant(withText("Hello"))))

        scenario.close()
    }

    @Test
    fun other_user_types_message_displays_the_message() {
        val scenario = launch(MainActivity::class.java)

        //change to Other User
        menuSwitchUser.click()

        onView(isAssignableFrom(Toolbar::class.java)).check(matches(hasDescendant(withText(R.string.frag_title_user_name_me))))

        editMessageBox.typetext("Hello")
        sendButton.click()

        onView(withText("Hello")).check(matches(isDisplayed()))
        scenario.close()
    }
}