package com.example2.roomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example2.roomapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.tvMainview.text = "Layout inflated"
        setContentView(binding.root)
    }
}