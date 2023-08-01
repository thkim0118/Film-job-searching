package com.fone.filmone.core.util.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

abstract class Timer(
    private val maxTime: Long,
    private val delayTime: Long = 1000,
    private val onTimeChanged: (Time) -> Unit
) {
    private var remainTime: Long = maxTime
    private var startTime: Long = 0L
    private var timerJob = createTimerJob()

    fun startVerificationTimer() {
        if (timerJob.isCancelled || timerJob.isCompleted) {
            timerJob.cancel()
            timerJob = createTimerJob()
        }
        startTime = Calendar.getInstance().timeInMillis
        remainTime = maxTime
        timerJob.start()
    }

    fun finishVerificationTimer() {
        timerJob.cancel()
    }

    private fun createTimerJob() =
        CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
            while (remainTime > 0) {
                delay(delayTime)
                remainTime = maxTime + startTime - Calendar.getInstance().timeInMillis
                if (remainTime < 1000) remainTime = 0
                val min = (remainTime / 60000).toInt()
                val sec = (remainTime % 60000).toInt() / 1000
                onTimeChanged(Time(min, sec))
            }
        }
}

data class Time(
    val min: Int,
    val sec: Int
)
