package com.example.muzzapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.muzzapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        setContentView(binding.root)

    }
}