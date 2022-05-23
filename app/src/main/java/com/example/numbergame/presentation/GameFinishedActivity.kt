package com.example.numbergame.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.numbergame.R
import com.example.numbergame.domain.GameResult
import com.example.numbergame.domain.GameSettings

class GameFinishedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_finished)
    }

    private fun setupResultImage() {

    }

    private fun setupResults() {

    }

    companion object {

        private const val SETTINGS_EXTRA = "extra_settings"
        private const val RESULT_EXTRA = "extra_result"

        fun newIntent(activity: Activity,
                      gameSettings: GameSettings,
                      gameResult: GameResult): Intent{
            val intent = Intent(activity, GameFinishedActivity::class.java)
            intent.putExtra(SETTINGS_EXTRA, gameSettings)
            intent.putExtra(RESULT_EXTRA, gameResult)
            return intent
        }
    }
}