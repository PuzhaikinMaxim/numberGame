package com.example.numbergame.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numbergame.domain.DifficultyLevel
import com.example.numbergame.domain.GameResult
import com.example.numbergame.domain.GameSettings
import com.example.numbergame.domain.Question
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

class GameViewModel() : ViewModel() {

    private lateinit var _gameSettings: GameSettings
    val gameSettings: GameSettings
        get() = _gameSettings.copy()

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _time = MutableLiveData<Int>()
    val time: LiveData<Int>
        get() = _time

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _amountOfRightAnswers = MutableLiveData<String>()
    val amountOfRightAnswers: LiveData<String>
        get() = _amountOfRightAnswers

    private fun generateQuestion() {
        val sum = Random.nextInt(MIN_SUM_VALUE, _gameSettings.maxSumValue)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - AMOUNT_OF_OPTIONS, MIN_ANSWER_VALUE)
        val to = min(_gameSettings.maxSumValue, rightAnswer + AMOUNT_OF_OPTIONS)
        while (options.size < AMOUNT_OF_OPTIONS){
            options.add(Random.nextInt(from, to))
        }
        _question.value = Question(sum, visibleNumber, options.toList())
    }

    fun setSettings(difficultyLevel: DifficultyLevel){
        _gameSettings = getGameSettings(difficultyLevel)
        _gameResult.value = GameResult(
            false,
            0,
            0,
            _gameSettings
        )
        _time.value = _gameSettings.gameTimeInSeconds
    }

    private fun startTimer(){
        val timer = object : CountDownTimer(
            _gameSettings.gameTimeInSeconds.toLong() * MILLIS_IN_SEC,
            MILLIS_IN_SEC
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _time.value = _time.value?.minus(1)
            }

            override fun onFinish() {
                _gameResult.value = _gameResult.value!!.copy(
                    isWon = isWon()
                )
                _shouldCloseScreen.value = Unit
            }

        }
        timer.start()
    }

    private fun isWon(): Boolean {

        val countOfRightAnswers = _gameResult.value!!.countOfRightAnswers
        val minCountOfRightAnswers = _gameSettings.minCountOfRightAnswers
        if(minCountOfRightAnswers > countOfRightAnswers) return false

        val countOfQuestions = _gameResult.value!!.countOfQuestions
        val percentOfRightAnswers =
            ((countOfRightAnswers.toDouble() / countOfQuestions.toDouble()) * 90).roundToInt()

        val minPercentageOfRightAnswers = _gameSettings.minPercentageOfRightAnswers
        if(minPercentageOfRightAnswers > percentOfRightAnswers) return false

        return true
    }

    fun startGame(){
        setStatisticsText()
        generateQuestion()
        startTimer()
    }

    fun handleAnswer(answer: Int){
        if(answer == _question.value?.visibleNumber?.let
            { _question.value?.sum?.minus(it) }
            ?: RuntimeException("Question does not exist")){
            _gameResult.value = _gameResult.value?.copy(
                countOfRightAnswers = _gameResult.value?.countOfRightAnswers?.plus(1)!!,
                countOfQuestions = _gameResult.value?.countOfQuestions?.plus(1)!!
            )
        }
        else{
            _gameResult.value = _gameResult.value?.copy(
                countOfQuestions = _gameResult.value?.countOfQuestions?.plus(1)!!
            )
        }
        setStatisticsText()
        generateQuestion()
    }

    private fun setStatisticsText(){
        val rightAnswers = _gameResult.value?.countOfRightAnswers
        val allQuestions = _gameSettings.minCountOfRightAnswers
        _amountOfRightAnswers.value = "Правильных ответов $rightAnswers (минимум $allQuestions)"
    }

    companion object {

        private const val MILLIS_IN_SEC = 1000L

        private const val MIN_SUM_VALUE = 2

        private const val MIN_ANSWER_VALUE = 1

        private const val AMOUNT_OF_OPTIONS = 6

        private fun getGameSettings(difficultyLevel: DifficultyLevel): GameSettings{
            return when(difficultyLevel) {
                DifficultyLevel.TEST -> {
                    GameSettings(
                        10,
                        3,
                        50,
                        8
                    )
                }
                DifficultyLevel.EASY -> {
                    GameSettings(
                        10,
                        10,
                        70,
                        60
                    )
                }
                DifficultyLevel.NORMAL -> {
                    GameSettings(
                        20,
                        20,
                        80,
                        40
                    )
                }
                DifficultyLevel.HARD -> {
                    GameSettings(
                        30,
                        30,
                        90,
                        40
                    )
                }
            }
        }
    }
}