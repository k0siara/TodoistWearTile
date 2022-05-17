package com.patrykkosieradzki.todoist.wear.tile

import android.app.Activity
import android.os.Bundle
import com.patrykkosieradzki.todoist.wear.tile.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}