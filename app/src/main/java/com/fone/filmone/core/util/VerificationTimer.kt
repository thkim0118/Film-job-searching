package com.fone.filmone.core.util

import kotlinx.coroutines.*
import java.util.*

class VerificationTimer(
    private var onChangedListener: (String) -> Unit
) {
    private var remain = MAX_TIMEOUT
    private var startTimeLong: Long = 0
    private var timerJob = createTimerJob()

    fun startVerificationTimer() {
        if (timerJob.isCancelled || timerJob.isCompleted) {
            timerJob.cancel()
            timerJob = createTimerJob()
        }
        startTimeLong = Calendar.getInstance().timeInMillis
        remain = MAX_TIMEOUT
        timerJob.start()
    }

    fun finishVerificationTimer() {
        timerJob.cancel()
    }

    private fun createTimerJob() = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
        while (remain.toInt() > 0) {
            remain = MAX_TIMEOUT + startTimeLong - Calendar.getInstance().timeInMillis
            if (remain < 1000) remain = 0
            val min = (remain / 60000).toInt()
            val sec = (remain % 60000).toInt() / 1000
            onChangedListener("0" + min + ":" + ((if (sec < 10) "0" else "") + sec))
            delay(1000)
        }
    }

    companion object {
        const val MAX_TIMEOUT = 3 * 60 * 1000 // Transmit time : 3 min
            .toLong()
        const val REQUEST_AGAIN_MIN_TIME = 10 * 1000 // Retransmit time : 10s
            .toLong()
    }
}