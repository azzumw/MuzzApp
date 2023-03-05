package com.example.muzzapp.ui.chat

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.muzzapp.repository.FakeRepository
import com.example.muzzapp.repository.Repository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class ChatFragmentTest {

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = FakeRepository()
    }

    @Test
    fun launchFragment() {

        launchFragmentInContainer<ChatFragment>(null, com.example.muzzapp.R.style.Theme_MuzzApp)


    }


}