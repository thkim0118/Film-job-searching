package com.fone.filmone.ui.main.job.profile.register.actor

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ActorProfileRegisterViewModel @Inject constructor(
) : BaseViewModel() {

    private val viewModelState = MutableStateFlow(ActorProfileRegisterViewModelState())

    val uiState = viewModelState
        .map(ActorProfileRegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun register() {
        val state = viewModelState.value

        if (state.registerButtonEnable.not()) {
            return
        }


    }

    fun updateName(name: String) {
        viewModelState.update { state ->
            state.copy(name = name)
        }

        updateRegisterButtonState()
    }

    fun updateBirthday(birthday: String) {
        viewModelState.update { state ->
            state.copy(birthday = birthday)
        }

        updateRegisterButtonState()
    }

    fun updateGender(gender: Gender) {
        viewModelState.update { state ->
            state.copy(gender = gender)
        }
    }

    fun updateGenderTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                gender = if (enable) {
                    Gender.IRRELEVANT
                } else {
                    null
                }
            )
        }
    }

    fun updateComments(comments: String) {
        viewModelState.update { state ->
            state.copy(hookingComments = comments)
        }

        updateRegisterButtonState()
    }

    fun updateHeight(height: String) {
        viewModelState.update { state ->
            state.copy(height = height)
        }

        updateRegisterButtonState()
    }

    fun updateWeight(weight: String) {
        viewModelState.update { state ->
            state.copy(weight = weight)
        }

        updateRegisterButtonState()
    }

    fun updateEmail(email: String) {
        viewModelState.update { state ->
            state.copy(email = email)
        }

        updateRegisterButtonState()
    }

    fun updateAbility(ability: String) {
        viewModelState.update { state ->
            state.copy(ability = ability)
        }
    }

    fun updateSns(sns: String) {
        viewModelState.update { state ->
            state.copy(sns = sns)
        }
    }

    fun updateDetailInfo(detailInfo: String) {
        viewModelState.update { state ->
            state.copy(detailInfo = detailInfo)
        }

        updateRegisterButtonState()
    }

    fun updateCareer(career: Career, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                career = if (enable) {
                    career
                } else {
                    null
                }
            )
        }
    }

    fun updateCareerDetail(careerDetail: String) {
        viewModelState.update { state ->
            state.copy(careerDetail = careerDetail)
        }
    }

    fun updateCategoryTag(enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                categoryTagEnable = enable,
                categories = if (enable) {
                    Category.values().toSet()
                } else {
                    emptySet()
                }
            )
        }
    }

    fun updateCategory(category: Category, enable: Boolean) {
        viewModelState.update { state ->
            state.copy(
                categories = if (enable) {
                    state.categories + setOf(category)
                } else {
                    state.categories.filterNot { it == category }.toSet()
                }
            )
        }
    }

    fun updateImage(encodedString: String, remove: Boolean) {
        viewModelState.update { state ->
            state.copy(
                pictureList = if (remove) {
                    val copyList = state.pictureList.toMutableList().apply {
                        remove(encodedString)
                    }

                    copyList
                } else {
                    state.pictureList + encodedString
                }
            )
        }
    }

    private fun updateRegisterButtonState() {
        val modelState = viewModelState.value
        val birthDayPattern = Pattern.compile("^(\\d{4})-(0[1-9]|1[0-2])-(0\\d|[1-2]\\d|3[0-1])+$")
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                    ")+"
        )

        val isEnable = modelState.name.isNotEmpty() && modelState.hookingComments.isNotEmpty() &&
                modelState.birthday.isNotEmpty() && birthDayPattern.matcher(modelState.birthday)
            .matches() &&
                modelState.height.isNotEmpty() && modelState.weight.isNotEmpty() && modelState.email.isNotEmpty() &&
                emailPattern.matcher(modelState.email)
                    .matches() && modelState.detailInfo.isNotEmpty()

        viewModelState.update { state ->
            state.copy(registerButtonEnable = isEnable)
        }
    }
}

private data class ActorProfileRegisterViewModelState(
    val pictureList: List<String> = emptyList(),
    val name: String = "",
    val hookingComments: String = "",
    val commentsTextLimit: Int = 50,
    val birthday: String = "",
    val gender: Gender? = Gender.IRRELEVANT,
    val genderTagEnable: Boolean = false,
    val height: String = "",
    val weight: String = "",
    val email: String = "",
    val ability: String = "",
    val sns: String = "",
    val detailInfo: String = "",
    val detailInfoTextLimit: Int = 200,
    val career: Career? = null,
    val careerDetail: String = "",
    val careerDetailTextLimit: Int = 500,
    val categories: Set<Category> = emptySet(),
    val categoryTagEnable: Boolean = false,
    val registerButtonEnable: Boolean = false,
) {
    fun toUiState(): ActorProfileRegisterUiModel = ActorProfileRegisterUiModel(
        pictureList = pictureList,
        name = name,
        hookingComments = hookingComments,
        commentsTextLimit = commentsTextLimit,
        birthday = birthday,
        gender = gender,
        genderTagEnable = genderTagEnable,
        height = height,
        weight = weight,
        email = email,
        ability = ability,
        sns = sns,
        detailInfo = detailInfo,
        detailInfoTextLimit = detailInfoTextLimit,
        career = career,
        careerDetail = careerDetail,
        careerDetailTextLimit = careerDetailTextLimit,
        categories = categories,
        categoryTagEnable = categoryTagEnable,
        registerButtonEnable = registerButtonEnable
    )
}

data class ActorProfileRegisterUiModel(
    val pictureList: List<String>,
    val name: String,
    val hookingComments: String,
    val commentsTextLimit: Int,
    val birthday: String,
    val gender: Gender?,
    val genderTagEnable: Boolean,
    val height: String,
    val weight: String,
    val email: String,
    val ability: String,
    val sns: String,
    val detailInfo: String,
    val detailInfoTextLimit: Int,
    val career: Career?,
    val careerDetail: String,
    val careerDetailTextLimit: Int,
    val categories: Set<Category>,
    val categoryTagEnable: Boolean,
    val registerButtonEnable: Boolean
)