package com.fovsol.tictactoe.flexmp

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class ReflexViewModel : ViewModel() {

    val topTimer = MutableLiveData<Long>()
    val topCumTimer = MutableLiveData<Long>()

    val bottomTimer = MutableLiveData<Long>()
    val bottomCumTimer = MutableLiveData<Long>()


    private val endGameScore = MutableLiveData<Int>()
    val endGameScoreDisplay = endGameScore.map {
        it.div(1000)
    }

    val penaltyScore = MutableLiveData<Long>()
    val penaltyScoreDisplay = penaltyScore.map {
        it.toFloat().div(1000)
    }

    var topPlayerTurn = MutableLiveData<Boolean>()

    var topPlayerPenalty = MutableLiveData<Boolean>()
    var bottomPlayerPenalty = MutableLiveData<Boolean>()

    var gameStarted: Boolean = false
    var gameEnded: Boolean = false

    private val mInterval = 10L
    private var mHandler: Handler? = null

    private var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            if (topPlayerTurn.value!!) {
                topTimer.value = (topTimer.value)?.plus(mInterval)
                if (topTimer.value!! > penaltyScore.value!! && topPlayerPenalty.value!!)
                    topPlayerPenalty.value = false
            } else {
                bottomTimer.value = (bottomTimer.value)?.plus(mInterval)
                if (bottomTimer.value!! > penaltyScore.value!! && bottomPlayerPenalty.value!!)
                    bottomPlayerPenalty.value = false
            }
            if (topCumTimer.value!! + topTimer.value!! > endGameScore.value!! || bottomCumTimer.value!! + bottomTimer.value!! > endGameScore.value!!) {
                gameStarted = false
                gameEnded = true
                stopTimer()
            }
            if (!gameEnded)
                mHandler!!.postDelayed(this, mInterval)
        }
    }

    init {
        endGameScore.value = 5_000
        penaltyScore.value = 500L
        restartGame()
    }

    fun restartGame() {
        topTimer.value = 0L
        topCumTimer.value = 0L

        bottomTimer.value = 0L
        bottomCumTimer.value = 0L

        gameEnded = false
        gameStarted = false

        topPlayerTurn.value = false
        topPlayerPenalty.value = false
        bottomPlayerPenalty.value = false

        stopTimer()
    }

    private fun startTimer() {
        mHandler = Handler(Looper.getMainLooper())
        mStatusChecker.run()
    }

    private fun stopTimer() {
        // Update the observer on ReflexFragment class
        topPlayerTurn.value = !topPlayerTurn.value!!
        topPlayerTurn.value = !topPlayerTurn.value!!
        mHandler?.removeCallbacks(mStatusChecker)
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    private fun resetTurn(timer: MutableLiveData<Long>, cumTimer: MutableLiveData<Long>) {
        cumTimer.value = (cumTimer.value)?.plus(timer.value ?: 0)
        timer.value = 0L
    }

    fun topPlayerButton() {
        if (!gameEnded)
            if (gameStarted && !bottomPlayerPenalty.value!! && !topPlayerPenalty.value!!) {
                stopTimer()
                if (!topPlayerTurn.value!!) {
                    resetTurn(bottomTimer, bottomCumTimer)
                    topPlayerPenalty.value = true
                    topPlayerTurn.value = true
                } else {
                    resetTurn(topTimer, topCumTimer)
                    topPlayerTurn.value = false
                }
                startTimer()
            } else {
                gameStarted = true
                if (!bottomPlayerPenalty.value!! && !topPlayerPenalty.value!!) {
                    stopTimer()
                    topPlayerTurn.value = false
                    startTimer()
                }
            }
    }

    fun bottomPlayerButton() {
        if (!gameEnded)
            if (gameStarted && !bottomPlayerPenalty.value!! && !topPlayerPenalty.value!!) {
                stopTimer()
                if (topPlayerTurn.value!!) {
                    resetTurn(topTimer, topCumTimer)
                    bottomPlayerPenalty.value = true
                    topPlayerTurn.value = false
                } else {
                    resetTurn(bottomTimer, bottomCumTimer)
                    topPlayerTurn.value = true
                }
                startTimer()
            } else {
                gameStarted = true
                if (!bottomPlayerPenalty.value!! && !topPlayerPenalty.value!!) {
                    stopTimer()
                    topPlayerTurn.value = true
                    startTimer()
                }
            }
    }

    // Game Adjusting
    fun penaltyIncrease() {
        if (!gameStarted)
            penaltyScore.value = penaltyScore.value?.plus(100)
    }

    fun penaltyDecrease() = penaltyScore.value?.let {
        if (!gameStarted && it > 100)
            penaltyScore.value = it.minus(100)
    }

    fun endGameIncrease() {
        if (!gameStarted)
            endGameScore.value = endGameScore.value?.plus(1000)
    }

    fun endGameDecrease() = endGameScore.value?.let {
        if (!gameStarted && it > 1000)
            endGameScore.value = it.minus(1000)
    }
}