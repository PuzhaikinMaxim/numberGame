package com.example.numbergame.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numbergame.domain.GameResult
import kotlin.math.roundToInt

class GameFinishedViewModel : ViewModel() {

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    fun setGameInfo(gameResult: GameResult){
        _gameResult.value = gameResult
        setPercentOfRightAnswers()
    }

    private fun setPercentOfRightAnswers(){
        val countOfRightAnswers = _gameResult.value!!.countOfRightAnswers
        val countOfQuestions = _gameResult.value!!.countOfQuestions

        if(countOfQuestions == 0){
            _percentOfRightAnswers.value = 0
            return
        }

        _percentOfRightAnswers.value =
            ((countOfRightAnswers.toDouble() / countOfQuestions.toDouble()) * 100).roundToInt()
    }
}