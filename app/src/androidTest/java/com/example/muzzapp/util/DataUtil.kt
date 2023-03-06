package com.example.muzzapp.util

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.muzzapp.R

const val ANIMATION_OFF = "settings put global animator_duration_scale 0.0"
const val TRANS_ANIMATION_OFF = "settings put global transition_animation_scale 0.0"
const val WIN_ANIMATION_OFF = "settings put global window_animation_scale 0.0"

val editMessageBox = Espresso.onView(ViewMatchers.withId(R.id.edit_messagebox))
val sendButton = Espresso.onView(ViewMatchers.withId(R.id.send_button))
val chatRecyclerView = Espresso.onView(ViewMatchers.withId(R.id.chat_recycler_view))
val menuSwitchUser = Espresso.onView(ViewMatchers.withId(R.id.user_switch))


fun ViewInteraction.typetext(text:String) = this.perform(
    ViewActions.typeText(text),
    ViewActions.closeSoftKeyboard()
)

fun ViewInteraction.click() = this.perform(ViewActions.click())