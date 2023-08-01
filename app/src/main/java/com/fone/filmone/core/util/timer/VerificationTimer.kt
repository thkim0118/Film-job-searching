package com.fone.filmone.core.util.timer

class VerificationTimer(
    private val onChangedListener: (String) -> Unit
) : Timer(
    maxTime = MAX_TIMEOUT,
    onTimeChanged = { time -> onChangedListener("0" + time.min + ":" + ((if (time.sec < 10) "0" else "") + time.sec)) }
) {
    companion object {
        const val MAX_TIMEOUT = 3 * 60 * 1000 // Transmit time : 3 min
            .toLong()
        const val REQUEST_AGAIN_MIN_TIME = 10 * 1000 // Retransmit time : 10s
            .toLong()
    }
}
