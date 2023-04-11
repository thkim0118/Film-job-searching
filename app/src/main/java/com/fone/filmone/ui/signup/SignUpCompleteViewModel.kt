package com.fone.filmone.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.SignInUseCase
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpCompleteViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    fun signIn(
        accessToken: String,
        email: String,
        socialLoginType: String
    ) = viewModelScope.launch {
        signInUseCase.invoke(
            accessToken,
            email,
            socialLoginType
        ).onSuccess {
            FOneNavigator.navigateTo(FOneDestinations.Main)
        }.onFail {

        }
    }
}
