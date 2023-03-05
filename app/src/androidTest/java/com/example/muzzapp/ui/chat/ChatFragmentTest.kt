package com.example.muzzapp.ui.chat

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.muzzapp.ServiceLocator
import com.example.muzzapp.model.Message
import com.example.muzzapp.repository.FakeRepository
import com.example.muzzapp.repository.Repository
import com.example.muzzapp.ui.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class ChatFragmentTest {

    private lateinit var repository: Repository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeRepository()
        ServiceLocator.repository = repository

    }

    @After
    fun tearDown() = runBlocking {
        repository.clearMessages()
        ServiceLocator.resetRepository()
    }

    @Test
    fun displaysCorrectMessagesInChatWindow() = runTest {
        // GIVEN - list of messages
        val messages = listOf(
            Message("Hello", User.ME.ordinal, 20000L),
            Message("How are you?", User.YOU.ordinal, 30000L),
            Message("Are you there?", User.ME.ordinal, 40000L),
        )

        repository.insertMessage(messages.first())
        repository.insertMessage(messages[1])
        repository.insertMessage(messages.last())

        // WHEN - ChatFragment is launched
        launchFragmentInContainer<ChatFragment>(null, com.example.muzzapp.R.style.Theme_MuzzApp)

        // THEN - verify the correct messages are shown in the chat Window
        onView(withText("Hello")).check(matches(isDisplayed()))
        onView(withText("How are you?")).check(matches(isDisplayed()))
        onView(withText("Are you there?")).check(matches(isDisplayed()))

    }
}