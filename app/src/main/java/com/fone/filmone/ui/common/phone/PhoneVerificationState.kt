package com.fone.filmone.ui.common.phone

sealed interface PhoneVerificationState {
    object Complete : PhoneVerificationState
    object ShouldVerify : PhoneVerificationState
    object Retransmit : PhoneVerificationState
}