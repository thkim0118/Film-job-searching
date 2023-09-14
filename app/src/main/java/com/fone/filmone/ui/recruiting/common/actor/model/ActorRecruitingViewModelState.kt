package com.fone.filmone.ui.recruiting.common.actor.model

import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender

abstract class ActorRecruitingViewModelState(
    open val actorRecruitingStep1UiModel: ActorRecruitingStep1UiModel = ActorRecruitingStep1UiModel(),
    open val actorRecruitingStep2UiModel: ActorRecruitingStep2UiModel = ActorRecruitingStep2UiModel(),
    open val actorRecruitingStep3UiModel: ActorRecruitingStep3UiModel = ActorRecruitingStep3UiModel(),
    open val actorRecruitingStep4UiModel: ActorRecruitingStep4UiModel = ActorRecruitingStep4UiModel(),
    open val actorRecruitingStep5UiModel: ActorRecruitingStep5UiModel = ActorRecruitingStep5UiModel(),
    open val actorRecruitingUiEvent: ActorRecruitingUiEvent = ActorRecruitingUiEvent.Clear,
    open val focusEvent: ActorRecruitingFocusEvent? = null,
) {
    fun toUiState(): ActorRecruitingUiModel = ActorRecruitingUiModel(
        actorRecruitingStep1UiModel = actorRecruitingStep1UiModel,
        actorRecruitingStep2UiModel = actorRecruitingStep2UiModel,
        actorRecruitingStep3UiModel = actorRecruitingStep3UiModel,
        actorRecruitingStep4UiModel = actorRecruitingStep4UiModel,
        actorRecruitingStep5UiModel = actorRecruitingStep5UiModel,
        actorRecruitingUiEvent = actorRecruitingUiEvent,
        focusEvent = focusEvent,
    )

    abstract fun copy(
        actorRecruitingStep1UiModel: ActorRecruitingStep1UiModel = this.actorRecruitingStep1UiModel,
        actorRecruitingStep2UiModel: ActorRecruitingStep2UiModel = this.actorRecruitingStep2UiModel,
        actorRecruitingStep3UiModel: ActorRecruitingStep3UiModel = this.actorRecruitingStep3UiModel,
        actorRecruitingStep4UiModel: ActorRecruitingStep4UiModel = this.actorRecruitingStep4UiModel,
        actorRecruitingStep5UiModel: ActorRecruitingStep5UiModel = this.actorRecruitingStep5UiModel,
        actorRecruitingUiEvent: ActorRecruitingUiEvent = this.actorRecruitingUiEvent,
        focusEvent: ActorRecruitingFocusEvent? = this.focusEvent,
    ): ActorRecruitingViewModelState

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ActorRecruitingViewModelState) return false

        if (actorRecruitingStep1UiModel != other.actorRecruitingStep1UiModel) return false
        if (actorRecruitingStep2UiModel != other.actorRecruitingStep2UiModel) return false
        if (actorRecruitingStep3UiModel != other.actorRecruitingStep3UiModel) return false
        if (actorRecruitingStep4UiModel != other.actorRecruitingStep4UiModel) return false
        if (actorRecruitingStep5UiModel != other.actorRecruitingStep5UiModel) return false
        if (actorRecruitingUiEvent != other.actorRecruitingUiEvent) return false
        if (focusEvent != other.focusEvent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = actorRecruitingStep1UiModel.hashCode()
        result = 31 * result + actorRecruitingStep2UiModel.hashCode()
        result = 31 * result + actorRecruitingStep3UiModel.hashCode()
        result = 31 * result + actorRecruitingStep4UiModel.hashCode()
        result = 31 * result + actorRecruitingStep5UiModel.hashCode()
        result = 31 * result + actorRecruitingUiEvent.hashCode()
        result = 31 * result + (focusEvent?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ActorRecruitingViewModelState(actorRecruitingStep1UiModel=$actorRecruitingStep1UiModel, actorRecruitingStep2UiModel=$actorRecruitingStep2UiModel, actorRecruitingStep3UiModel=$actorRecruitingStep3UiModel, actorRecruitingStep4UiModel=$actorRecruitingStep4UiModel, actorRecruitingStep5UiModel=$actorRecruitingStep5UiModel, actorRecruitingUiEvent=$actorRecruitingUiEvent, focusEvent=$focusEvent)"
    }
}

data class ActorRecruitingUiModel(
    val actorRecruitingStep1UiModel: ActorRecruitingStep1UiModel,
    val actorRecruitingStep2UiModel: ActorRecruitingStep2UiModel,
    val actorRecruitingStep3UiModel: ActorRecruitingStep3UiModel,
    val actorRecruitingStep4UiModel: ActorRecruitingStep4UiModel,
    val actorRecruitingStep5UiModel: ActorRecruitingStep5UiModel,
    val actorRecruitingUiEvent: ActorRecruitingUiEvent,
    val focusEvent: ActorRecruitingFocusEvent?,
)

data class ActorRecruitingStep1UiModel(
    val titleText: String = "",
    val titleTextLimit: Int = 50,
    val categories: List<Category> = emptyList(),
    val deadlineDate: String = "",
    val deadlineTagEnable: Boolean = true,
    val recruitmentActor: String = "",
    val recruitmentNumber: String = "",
    val recruitmentGender: Gender? = null,
    val genderTagEnable: Boolean = true,
    val ageRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val ageTagEnable: Boolean = true,
    val defaultAgeRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val career: Career? = null,
    val careerTagEnable: Boolean = true,
)

data class ActorRecruitingStep2UiModel(
    val production: String = "",
    val workTitle: String = "",
    val directorName: String = "",
    val genre: String = "",
    val logLine: String = "",
    val logLineTextLimit: Int = 200,
    val isLogLineTagEnable: Boolean = false,
)

data class ActorRecruitingStep3UiModel(
    val location: String = "",
    val period: String = "",
    val pay: String = "",
    val locationTagEnable: Boolean = true,
    val periodTagEnable: Boolean = true,
    val payTagEnable: Boolean = true,
)

data class ActorRecruitingStep4UiModel(
    val detailInfo: String = "",
    val detailInfoTextLimit: Int = 500,
)

data class ActorRecruitingStep5UiModel(
    val manager: String = "",
    val email: String = "",
)

sealed class ActorRecruitingUiEvent {
    object RegisterComplete : ActorRecruitingUiEvent()
    object Clear : ActorRecruitingUiEvent()
}

sealed interface ActorRecruitingFocusEvent {
    object Title : ActorRecruitingFocusEvent
    object Category : ActorRecruitingFocusEvent
    object Deadline : ActorRecruitingFocusEvent
    object RecruitmentNumber : ActorRecruitingFocusEvent
    object Production : ActorRecruitingFocusEvent
    object WorkTitle : ActorRecruitingFocusEvent
    object DirectorName : ActorRecruitingFocusEvent
    object Genre : ActorRecruitingFocusEvent
    object Detail : ActorRecruitingFocusEvent
    object Manager : ActorRecruitingFocusEvent
    object Email : ActorRecruitingFocusEvent
}
