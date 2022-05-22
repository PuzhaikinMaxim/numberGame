package com.example.numbergame.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.numbergame.R
import com.example.numbergame.domain.DifficultyLevel

class ChooseLevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_difficulty)
        setupButtons()
    }

    private fun setupButtons(){
        val testLevel = findViewById<Button>(R.id.button_level_test)
        setupButton(testLevel, DifficultyLevel.TEST)

        val easyLevel = findViewById<Button>(R.id.button_level_easy)
        setupButton(easyLevel, DifficultyLevel.EASY)

        val normalLevel = findViewById<Button>(R.id.button_level_normal)
        setupButton(normalLevel, DifficultyLevel.NORMAL)

        val hardLevel = findViewById<Button>(R.id.button_level_hard)
        setupButton(hardLevel, DifficultyLevel.HARD)
    }

    private fun setupButton(button: Button, difficultyLevel: DifficultyLevel){
        button.setOnClickListener {
            launchChooseLevelActivity(difficultyLevel)
        }
    }

    private fun launchChooseLevelActivity(difficultyLevel: DifficultyLevel){
        val intent = GameActivity.newIntent(this, difficultyLevel)
        startActivity(intent)
    }

    companion object {
        fun newIntent(activity: Activity): Intent {
            return Intent(activity, ChooseLevelActivity::class.java)
        }
    }
}