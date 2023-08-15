package com.fone.filmone.core.util.timer

class HomeBannerTimer(
    private val onTimeChanged: () -> Unit
) : Timer(
    maxTime = Long.MAX_VALUE,
    delayTime = DELAY_TIME,
    onTimeChanged = { onTimeChanged() }
) {
    companion object {
        private const val DELAY_TIME = 5000L
    }
}
