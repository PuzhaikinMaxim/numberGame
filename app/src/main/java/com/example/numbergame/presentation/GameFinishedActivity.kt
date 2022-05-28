package com.example.numbergame.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.numbergame.R
import com.example.numbergame.domain.GameResult

class GameFinishedActivity : AppCompatActivity() {

    private lateinit var gameFinishedViewModel: GameFinishedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_finished)
        gameFinishedViewModel =
            ViewModelProvider(this).get(GameFinishedViewModel::class.java)
        parseIntent()
        setupResults()
        setRetryButton()
    }

    private fun parseIntent(){
        val gameResult = intent.getParcelableExtra<GameResult>(RESULT_EXTRA) ?:
            throw RuntimeException("Game result is not set")

        gameFinishedViewModel.setGameInfo(gameResult)
    }

    private fun setupResultImage(isWon: Boolean) {
        val imageResult = findViewById<ImageView>(R.id.image_result)
        if(isWon){
            imageResult.setImageResource(R.drawable.medal)
        }
        else{
            imageResult.setImageResource(R.drawable.thumb_down)
        }
    }

    private fun setupResults() {
        val tvScoreAnswers = findViewById<TextView>(R.id.tv_score_answers)

        val tvRequiredAnswers = findViewById<TextView>(R.id.tv_required_answers)

        val tvRequiredPercentage = findViewById<TextView>(R.id.tv_required_percentage)

        gameFinishedViewModel.gameResult.observe(this){
            tvRequiredAnswers.text = getString(R.string.required_score,
                it.gameSettings.minCountOfRightAnswers.toString()
            )
            tvRequiredPercentage.text = getString(R.string.required_percentage,
                it.gameSettings.minPercentageOfRightAnswers.toString()
            )
            tvScoreAnswers.text = getString(R.string.score_answers,
                it.countOfRightAnswers.toString()
            )

            setupResultImage(it.isWon)
        }

        val tvScorePercentage = findViewById<TextView>(R.id.tv_score_percentage)

        gameFinishedViewModel.percentOfRightAnswers.observe(this){
            println(it.toString())
            tvScorePercentage.text = getString(R.string.score_percentage, it.toString())
        }
    }

    private fun setRetryButton() {
        val buttonRetry = findViewById<Button>(R.id.button_retry)
        buttonRetry.setOnClickListener {
            val intent = ChooseLevelActivity.newIntent(this)
            startActivity(intent)
        }
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