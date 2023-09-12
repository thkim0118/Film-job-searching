package com.fone.filmone.ui.recruiting.common.staff.model

import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender

abstract class StaffRecruitingViewModelState(
    open val staffRecruitingStep1UiModel: StaffRecruitingStep1UiModel = StaffRecruitingStep1UiModel(),
    open val staffRecruitingStep2UiModel: StaffRecruitingStep2UiModel = StaffRecruitingStep2UiModel(),
    open val staffRecruitingStep3UiModel: StaffRecruitingStep3UiModel = StaffRecruitingStep3UiModel(),
    open val staffRecruitingStep4UiModel: StaffRecruitingStep4UiModel = StaffRecruitingStep4UiModel(),
    open val staffRecruitingStep5UiModel: StaffRecruitingStep5UiModel = StaffRecruitingStep5UiModel(),
    open val registerButtonEnable: Boolean = false,
    open val staffRecruitingRegisterUiEvent: StaffRecruitingUiEvent = StaffRecruitingUiEvent.Clear,
) {
    fun toUiState(): StaffRecruitingUiModel =
        StaffRecruitingUiModel(
            staffRecruitingStep1UiModel = staffRecruitingStep1UiModel,
            staffRecruitingStep2UiModel = staffRecruitingStep2UiModel,
            staffRecruitingStep3UiModel = staffRecruitingStep3UiModel,
            staffRecruitingStep4UiModel = staffRecruitingStep4UiModel,
            staffRecruitingStep5UiModel = staffRecruitingStep5UiModel,
            registerButtonEnable = registerButtonEnable,
            staffRecruitingRegisterUiEvent = staffRecruitingRegisterUiEvent,
        )

    abstract fun copy(
        staffRecruitingStep1UiModel: StaffRecruitingStep1UiModel = this.staffRecruitingStep1UiModel,
        staffRecruitingStep2UiModel: StaffRecruitingStep2UiModel = this.staffRecruitingStep2UiModel,
        staffRecruitingStep3UiModel: StaffRecruitingStep3UiModel = this.staffRecruitingStep3UiModel,
        staffRecruitingStep4UiModel: StaffRecruitingStep4UiModel = this.staffRecruitingStep4UiModel,
        staffRecruitingStep5UiModel: StaffRecruitingStep5UiModel = this.staffRecruitingStep5UiModel,
        registerButtonEnable: Boolean = this.registerButtonEnable,
        staffRecruitingRegisterUiEvent: StaffRecruitingUiEvent = this.staffRecruitingRegisterUiEvent,
    ): StaffRecruitingViewModelState

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StaffRecruitingViewModelState) return false

        if (staffRecruitingStep1UiModel != other.staffRecruitingStep1UiModel) return false
        if (staffRecruitingStep2UiModel != other.staffRecruitingStep2UiModel) return false
        if (staffRecruitingStep3UiModel != other.staffRecruitingStep3UiModel) return false
        if (staffRecruitingStep4UiModel != other.staffRecruitingStep4UiModel) return false
        if (staffRecruitingStep5UiModel != other.staffRecruitingStep5UiModel) return false
        if (registerButtonEnable != other.registerButtonEnable) return false
        if (staffRecruitingRegisterUiEvent != other.staffRecruitingRegisterUiEvent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = staffRecruitingStep1UiModel.hashCode()
        result = 31 * result + staffRecruitingStep2UiModel.hashCode()
        result = 31 * result + staffRecruitingStep3UiModel.hashCode()
        result = 31 * result + staffRecruitingStep4UiModel.hashCode()
        result = 31 * result + staffRecruitingStep5UiModel.hashCode()
        result = 31 * result + registerButtonEnable.hashCode()
        result = 31 * result + staffRecruitingRegisterUiEvent.hashCode()
        return result
    }

    override fun toString(): String {
        return "StaffRecruitingViewModelState(staffRecruitingStep1UiModel=$staffRecruitingStep1UiModel, staffRecruitingStep2UiModel=$staffRecruitingStep2UiModel, staffRecruitingStep3UiModel=$staffRecruitingStep3UiModel, staffRecruitingStep4UiModel=$staffRecruitingStep4UiModel, staffRecruitingStep5UiModel=$staffRecruitingStep5UiModel, registerButtonEnable=$registerButtonEnable, staffRecruitingRegisterUiEvent=$staffRecruitingRegisterUiEvent)"
    }
}

data class StaffRecruitingUiModel(
    val staffRecruitingStep1UiModel: StaffRecruitingStep1UiModel,
    val staffRecruitingStep2UiModel: StaffRecruitingStep2UiModel,
    val staffRecruitingStep3UiModel: StaffRecruitingStep3UiModel,
    val staffRecruitingStep4UiModel: StaffRecruitingStep4UiModel,
    val staffRecruitingStep5UiModel: StaffRecruitingStep5UiModel,
    val registerButtonEnable: Boolean,
    val staffRecruitingRegisterUiEvent: StaffRecruitingUiEvent,
)

data class StaffRecruitingStep1UiModel(
    val titleText: String = "",
    val titleTextLimit: Int = 50,
    val categories: Set<Category> = emptySet(),
    val deadlineDate: String? = null,
    val deadlineTagEnable: Boolean = true,
    val recruitmentDomains: Set<Domain> = emptySet(),
    val recruitmentNumber: String = "",
    val recruitmentGender: Gender? = null,
    val genderTagEnable: Boolean = true,
    val ageRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val ageTagEnable: Boolean = true,
    val defaultAgeRange: ClosedFloatingPointRange<Float> = 1f..70f,
    val career: Career? = null,
    val careerTagEnable: Boolean = true,
)

data class StaffRecruitingStep2UiModel(
    val production: String = "",
    val workTitle: String = "",
    val directorName: String = "",
    val genre: String = "",
    val logLine: String = "",
    val logLineTextLimit: Int = 200,
    val isLogLinePrivate: Boolean = false,
)

data class StaffRecruitingStep3UiModel(
    val location: String = "",
    val period: String = "",
    val pay: String = "",
    val locationTagEnable: Boolean = true,
    val periodTagEnable: Boolean = true,
    val payTagEnable: Boolean = true,
)

data class StaffRecruitingStep4UiModel(
    val detailInfo: String = "",
    val detailInfoTextLimit: Int = 500,
)

data class StaffRecruitingStep5UiModel(
    val manager: String = "",
    val email: String = "",
)

sealed class StaffRecruitingDialogState {
    object Clear : StaffRecruitingDialogState()
    data class DomainSelectDialog(
        val selectedDomains: List<Domain>,
    ) : StaffRecruitingDialogState()
}

sealed class StaffRecruitingUiEvent {
    object RegisterComplete : StaffRecruitingUiEvent()
    object Clear : StaffRecruitingUiEvent()
}
