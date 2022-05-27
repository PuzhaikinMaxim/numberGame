package com.example.numbergame.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.numbergame.R
import com.example.numbergame.domain.GameResult
import com.example.numbergame.domain.GameSettings
import java.lang.RuntimeException

class GameFinishedActivity : AppCompatActivity() {

    private lateinit var gameFinishedViewModel: GameFinishedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_finished)
        gameFinishedViewModel =
            ViewModelProvider(this).get(GameFinishedViewModel::class.java)
        parseIntent()
        setupResults()
    }

    private fun parseIntent(){
        val gameResult = intent.getParcelableExtra<GameResult>(RESULT_EXTRA) ?:
            throw RuntimeException("Game result is not set")

        gameFinishedViewModel.setGameInfo(gameResult)
    }

    private fun setupResultImage() {

    }

    private fun setupResults() {
        val tvScoreAnswers = findViewById<TextView>(R.id.tv_score_answers)

        gameFinishedViewModel.gameResult.observe(this){

        }

        val tvRequiredAnswers = findViewById<TextView>(R.id.tv_required_answers)

        val tvRequiredPercentage = findViewById<TextView>(R.id.tv_required_percentage)

        gameFinishedViewModel.gameSettings.observe(this){

        }

        val tvScorePercentage = findViewById<TextView>(R.id.tv_score_percentage)

        gameFinishedViewModel.percentOfRightAnswers.observe(this){
            tvScorePercentage.text = getString(R.string.score_percentage, it.toString())
        }

        setupResultImage()
    }

    companion object {

        private const val RESULT_EXTRA = "extra_result"

        fun newIntent(activity: Activity,
                      gameResult: GameResult): Intent{
            val intent = Intent(activity, GameFinishedActivity::class.java)
            intent.putExtra(RESULT_EXTRA, gameResult)
            return intent
        }
    }
}