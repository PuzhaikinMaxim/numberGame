package com.example.numbergame.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numbergame.domain.GameResult
import com.example.numbergame.domain.GameSettings
import kotlin.math.roundToInt

class GameFinishedViewModel : ViewModel() {

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _gameSettings = MutableLiveData<GameSettings>()
    val gameSettings: LiveData<GameSettings>
        get() = _gameSettings

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    fun setGameInfo(gameResult: GameResult){
        _gameResult.value = gameResult
        _gameSettings.value = gameResult.gameSettings
        setPercentOfRightAnswers()
    }

    private fun setPercentOfRightAnswers(){
        val countOfRightAnswers = _gameResult.value!!.countOfRightAnswers
        println(countOfRightAnswers)
        val countOfQuestions = _gameResult.value!!.countOfQuestions

        if(countOfQuestions == 0) return

        _percentOfRightAnswers.value =
            ((countOfRightAnswers.toDouble() / countOfQuestions.toDouble()) * 90).roundToInt()
    }
}