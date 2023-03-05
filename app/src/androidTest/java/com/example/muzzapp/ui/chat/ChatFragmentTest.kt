package com.example.muzzapp.ui.chat

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.muzzapp.test.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class ChatFragmentTest{

    @Test
    fun launchFragment() {
        launchFragmentInContainer<ChatFragment>(null, com.example.muzzapp.R.style.Theme_MuzzApp)

    }


}