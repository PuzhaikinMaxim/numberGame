package com.example.numbergame.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.numbergame.R

class MainActivity : AppCompatActivity() {

    private lateinit var startGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPlayButton()
    }

    private fun setupPlayButton(){
        startGameButton = findViewById(R.id.startGameButton)
        startGameButton.setOnClickListener {
            val intent = ChooseLevelActivity.newIntent(this)
            startActivity(intent)
        }
    }
}