package com.fone.filmone.ui.profile.common.staff.model

import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender

abstract class StaffProfileViewModelState(
    open val pictureEncodedDataList: List<String> = emptyList(),
    open val name: String = "",
    open val hookingComments: String = "",
    open val commentsTextLimit: Int = 50,
    open val birthday: String = "",
    open val gender: Gender? = Gender.IRRELEVANT,
    open val genderTagEnable: Boolean = false,
    open val domains: Set<Domain> = emptySet(),
    open val email: String = "",
    open val specialty: String = "",
    open val sns: String = "",
    open val detailInfo: String = "",
    open val detailInfoTextLimit: Int = 200,
    open val career: Career? = null,
    open val careerDetail: String = "",
    open val careerDetailTextLimit: Int = 500,
    open val categories: Set<Category> = emptySet(),
    open val categoryTagEnable: Boolean = false,
    open val registerButtonEnable: Boolean = false,
    open val staffProfileUiEvent: StaffProfileUiEvent = StaffProfileUiEvent.Clear,
    open val focusEvent: StaffProfileFocusEvent? = null,
) {
    fun toUiState(): StaffProfileUiModel = StaffProfileUiModel(
        pictureEncodedDataList = pictureEncodedDataList,
        name = name,
        hookingComments = hookingComments,
        commentsTextLimit = commentsTextLimit,
        birthday = birthday,
        gender = gender,
        genderTagEnable = genderTagEnable,
        domains = domains,
        email = email,
        specialty = specialty,
        sns = sns,
        detailInfo = detailInfo,
        detailInfoTextLimit = detailInfoTextLimit,
        career = career,
        careerDetail = careerDetail,
        careerDetailTextLimit = careerDetailTextLimit,
        categories = categories,
        categoryTagEnable = categoryTagEnable,
        registerButtonEnable = registerButtonEnable,
        staffProfileUiEvent = staffProfileUiEvent,
        focusEvent = focusEvent,
    )

    abstract fun copy(
        pictureEncodedDataList: List<String> = this.pictureEncodedDataList,
        name: String = this.name,
        hookingComments: String = this.hookingComments,
        commentsTextLimit: Int = this.commentsTextLimit,
        birthday: String = this.birthday,
        gender: Gender? = this.gender,
        genderTagEnable: Boolean = this.genderTagEnable,
        domains: Set<Domain> = this.domains,
        email: String = this.email,
        specialty: String = this.specialty,
        sns: String = this.sns,
        detailInfo: String = this.detailInfo,
        detailInfoTextLimit: Int = this.detailInfoTextLimit,
        career: Career? = this.career,
        careerDetail: String = this.careerDetail,
        careerDetailTextLimit: Int = this.careerDetailTextLimit,
        categories: Set<Category> = this.categories,
        categoryTagEnable: Boolean = this.categoryTagEnable,
        registerButtonEnable: Boolean = this.registerButtonEnable,
        staffProfileUiEvent: StaffProfileUiEvent = this.staffProfileUiEvent,
        focusEvent: StaffProfileFocusEvent? = this.focusEvent,
    ): StaffProfileViewModelState

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StaffProfileViewModelState) return false

        if (pictureEncodedDataList != other.pictureEncodedDataList) return false
        if (name != other.name) return false
        if (hookingComments != other.hookingComments) return false
        if (commentsTextLimit != other.commentsTextLimit) return false
        if (birthday != other.birthday) return false
        if (gender != other.gender) return false
        if (genderTagEnable != other.genderTagEnable) return false
        if (domains != other.domains) return false
        if (email != other.email) return false
        if (specialty != other.specialty) return false
        if (sns != other.sns) return false
        if (detailInfo != other.detailInfo) return false
        if (detailInfoTextLimit != other.detailInfoTextLimit) return false
        if (career != other.career) return false
        if (careerDetail != other.careerDetail) return false
        if (careerDetailTextLimit != other.careerDetailTextLimit) return false
        if (categories != other.categories) return false
        if (categoryTagEnable != other.categoryTagEnable) return false
        if (registerButtonEnable != other.registerButtonEnable) return false
        if (staffProfileUiEvent != other.staffProfileUiEvent) return false
        if (focusEvent != other.focusEvent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pictureEncodedDataList.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + hookingComments.hashCode()
        result = 31 * result + commentsTextLimit
        result = 31 * result + birthday.hashCode()
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + genderTagEnable.hashCode()
        result = 31 * result + domains.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + specialty.hashCode()
        result = 31 * result + sns.hashCode()
        result = 31 * result + detailInfo.hashCode()
        result = 31 * result + detailInfoTextLimit
        result = 31 * result + (career?.hashCode() ?: 0)
        result = 31 * result + careerDetail.hashCode()
        result = 31 * result + careerDetailTextLimit
        result = 31 * result + categories.hashCode()
        result = 31 * result + categoryTagEnable.hashCode()
        result = 31 * result + registerButtonEnable.hashCode()
        result = 31 * result + staffProfileUiEvent.hashCode()
        result = 31 * result + focusEvent.hashCode()
        return result
    }

    override fun toString(): String {
        return "StaffProfileViewModelState(pictureEncodedDataList=$pictureEncodedDataList, name='$name', hookingComments='$hookingComments', commentsTextLimit=$commentsTextLimit, birthday='$birthday', gender=$gender, genderTagEnable=$genderTagEnable, domains=$domains, email='$email', specialty='$specialty', sns='$sns', detailInfo='$detailInfo', detailInfoTextLimit=$detailInfoTextLimit, career=$career, careerDetail='$careerDetail', careerDetailTextLimit=$careerDetailTextLimit, categories=$categories, categoryTagEnable=$categoryTagEnable, registerButtonEnable=$registerButtonEnable, staffProfileUiEvent=$staffProfileUiEvent, focusEvent=$focusEvent)"
    }
}

data class StaffProfileUiModel(
    val pictureEncodedDataList: List<String>,
    val name: String,
    val hookingComments: String,
    val commentsTextLimit: Int,
    val birthday: String,
    val gender: Gender?,
    val genderTagEnable: Boolean,
    val domains: Set<Domain>,
    val email: String,
    val specialty: String,
    val sns: String,
    val detailInfo: String,
    val detailInfoTextLimit: Int,
    val career: Career?,
    val careerDetail: String,
    val careerDetailTextLimit: Int,
    val categories: Set<Category>,
    val categoryTagEnable: Boolean,
    val registerButtonEnable: Boolean,
    val staffProfileUiEvent: StaffProfileUiEvent,
    val focusEvent: StaffProfileFocusEvent?,
)

sealed class StaffProfileDialogState {
    object Clear : StaffProfileDialogState()
    data class DomainSelectDialog(
        val selectedDomains: List<Domain>,
    ) : StaffProfileDialogState()
}

sealed class StaffProfileUiEvent {
    object RegisterComplete : StaffProfileUiEvent()
    object Clear : StaffProfileUiEvent()
}

sealed interface StaffProfileFocusEvent {
    object PictureList : StaffProfileFocusEvent
    object Name : StaffProfileFocusEvent
    object HookingComment : StaffProfileFocusEvent
    object Birthday : StaffProfileFocusEvent
    object Domain : StaffProfileFocusEvent
    object Email : StaffProfileFocusEvent
    object Detail : StaffProfileFocusEvent
    object Career : StaffProfileFocusEvent
    object CareerDetail : StaffProfileFocusEvent
}
