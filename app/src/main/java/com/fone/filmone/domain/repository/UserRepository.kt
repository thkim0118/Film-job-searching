package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.request.user.ChangePasswordRequest
import com.fone.filmone.data.datamodel.request.user.EmailSignInRequest
import com.fone.filmone.data.datamodel.request.user.EmailSignUpRequest
import com.fone.filmone.data.datamodel.request.user.EmailValidationRequest
import com.fone.filmone.data.datamodel.request.user.FindIdRequest
import com.fone.filmone.data.datamodel.request.user.FindPasswordRequest
import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.request.user.UserUpdateRequest
import com.fone.filmone.data.datamodel.request.user.ValidatePasswordRequest
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse
import com.fone.filmone.data.datamodel.response.user.EmailSignInResponse
import com.fone.filmone.data.datamodel.response.user.EmailSignUpResponse
import com.fone.filmone.data.datamodel.response.user.EmailValidationResponse
import com.fone.filmone.data.datamodel.response.user.FindIdResponse
import com.fone.filmone.data.datamodel.response.user.FindPasswordResponse
import com.fone.filmone.data.datamodel.response.user.SignUpResponse
import com.fone.filmone.data.datamodel.response.user.SigninResponse
import com.fone.filmone.data.datamodel.response.user.UserResponse
import com.fone.filmone.domain.model.common.DataResult

interface UserRepository {
    suspend fun checkNicknameDuplication(nickname: String): DataResult<CheckNicknameDuplicationResponse>
    suspend fun signUp(signUpRequest: SignUpRequest): DataResult<SignUpResponse>
    suspend fun signIn(signinRequest: SigninRequest): DataResult<SigninResponse>
    suspend fun signOut(): DataResult<Unit>
    suspend fun getUserInfo(): DataResult<UserResponse>
    suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest): DataResult<UserResponse>
    suspend fun logout(): DataResult<Unit>
    suspend fun emailSignIn(emailSignInRequest: EmailSignInRequest): DataResult<EmailSignInResponse>
    suspend fun emailSignUp(emailSignUpRequest: EmailSignUpRequest): DataResult<EmailSignUpResponse>
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): DataResult<Unit>
    suspend fun validatePassword(validatePasswordRequest: ValidatePasswordRequest): DataResult<Unit>
    suspend fun findId(findIdRequest: FindIdRequest): DataResult<FindIdResponse>
    suspend fun findPassword(findPasswordRequest: FindPasswordRequest): DataResult<FindPasswordResponse>
    suspend fun validateEmail(emailValidationRequest: EmailValidationRequest): DataResult<EmailValidationResponse>
}
