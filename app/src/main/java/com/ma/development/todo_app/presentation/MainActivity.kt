package com.ma.development.todo_app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ma.development.todo_app.R
import com.ma.development.todo_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        val navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)!!
            .findNavController()
        val config = AppBarConfiguration(navController.graph)
        binding.toolBar.setupWithNavController(navController, config)
    }
}