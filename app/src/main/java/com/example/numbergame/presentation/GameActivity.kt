package com.example.numbergame.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.numbergame.R
import com.example.numbergame.domain.DifficultyLevel

class GameActivity : AppCompatActivity() {

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        parseIntent()
        setObservers()
        startGame()
    }

    private fun parseIntent(){
        val difficultyLevel = intent.getParcelableExtra<DifficultyLevel>(EXTRA_LEVEL_NAME)
            ?: throw RuntimeException("Difficulty level is not set")
        gameViewModel.setSettings(difficultyLevel)
    }

    private fun startGame(){
        gameViewModel.startGame()
    }

    private fun setObservers(){
        val tvSum = findViewById<TextView>(R.id.tv_sum)
        val tvVisibleNumber = findViewById<TextView>(R.id.tv_left_number)

        gameViewModel.question.observe(this) {
            tvSum.text = it.sum.toString()
            tvVisibleNumber.text = it.visibleNumber.toString()
        }

        val tvTime = findViewById<TextView>(R.id.tv_timer)

        gameViewModel.time.observe(this) {
            tvTime.text = it.toString()
        }

        val tvAnswersProgress = findViewById<TextView>(R.id.tv_answers_progress)

        gameViewModel.amountOfRightAnswers.observe(this){
            tvAnswersProgress.text = it
        }

        gameViewModel.shouldCloseScreen.observe(this){
            val gameResult = gameViewModel.gameResult.value
            val intent = GameFinishedActivity.newIntent(this,gameResult!!)
            startActivity(intent)
        }

        setupOptions()
    }

    private fun setupOptions(){
        val tvOption1 = findViewById<TextView>(R.id.tv_option_1)
        val tvOption2 = findViewById<TextView>(R.id.tv_option_2)
        val tvOption3 = findViewById<TextView>(R.id.tv_option_3)
        val tvOption4 = findViewById<TextView>(R.id.tv_option_4)
        val tvOption5 = findViewById<TextView>(R.id.tv_option_5)
        val tvOption6 = findViewById<TextView>(R.id.tv_option_6)
        val textViews: List<TextView> = arrayListOf(
            tvOption1,
            tvOption2,
            tvOption3,
            tvOption4,
            tvOption5,
            tvOption6
        )
        gameViewModel.question.observe(this){
            for(i in textViews.indices){
                textViews[i].text = it.options[i].toString()
            }
        }
        for(textView in textViews){
            textView.setOnClickListener {
                gameViewModel.handleAnswer(textView.text.toString().toInt())
            }
        }
    }

    companion object {

        private const val EXTRA_LEVEL_NAME = "Difficulty level"

        fun newIntent(activity: Activity, difficultyLevel: DifficultyLevel): Intent{
            val intent = Intent(activity, GameActivity::class.java)
            intent.putExtra(EXTRA_LEVEL_NAME, difficultyLevel as Parcelable)
            return intent
        }
    }
}