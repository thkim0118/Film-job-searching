package com.fone.filmone.ui.profile.common.actor.model

import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender

abstract class ActorProfileViewModelState(
    open val pictureList: List<String> = emptyList(),
    open val name: String = "",
    open val hookingComments: String = "",
    open val commentsTextLimit: Int = 50,
    open val birthday: String = "",
    open val gender: Gender? = Gender.IRRELEVANT,
    open val genderTagEnable: Boolean = false,
    open val height: String = "",
    open val weight: String = "",
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
    open val actorProfileUiEvent: ActorProfileUiEvent = ActorProfileUiEvent.Clear,
    open val focusEvent: ActorProfileFocusEvent? = null,
) {
    fun toUiState(): ActorProfileUiModel = ActorProfileUiModel(
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
        actorProfileUiEvent = actorProfileUiEvent,
        focusEvent = focusEvent,
    )

    abstract fun copy(
        pictureList: List<String> = this.pictureList,
        name: String = this.name,
        hookingComments: String = this.hookingComments,
        commentsTextLimit: Int = this.commentsTextLimit,
        birthday: String = this.birthday,
        gender: Gender? = this.gender,
        genderTagEnable: Boolean = this.genderTagEnable,
        height: String = this.height,
        weight: String = this.weight,
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
        actorProfileUiEvent: ActorProfileUiEvent = this.actorProfileUiEvent,
        focusEvent: ActorProfileFocusEvent? = this.focusEvent,
    ): ActorProfileViewModelState

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ActorProfileViewModelState) return false

        if (pictureList != other.pictureList) return false
        if (name != other.name) return false
        if (hookingComments != other.hookingComments) return false
        if (commentsTextLimit != other.commentsTextLimit) return false
        if (birthday != other.birthday) return false
        if (gender != other.gender) return false
        if (genderTagEnable != other.genderTagEnable) return false
        if (height != other.height) return false
        if (weight != other.weight) return false
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
        if (actorProfileUiEvent != other.actorProfileUiEvent) return false
        if (focusEvent != other.focusEvent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pictureList.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + hookingComments.hashCode()
        result = 31 * result + commentsTextLimit
        result = 31 * result + birthday.hashCode()
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + genderTagEnable.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + weight.hashCode()
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
        result = 31 * result + actorProfileUiEvent.hashCode()
        return result
    }

    override fun toString(): String {
        return "ActorProfileViewModelState(pictureList=$pictureList, name='$name', hookingComments='$hookingComments', commentsTextLimit=$commentsTextLimit, birthday='$birthday', gender=$gender, genderTagEnable=$genderTagEnable, height='$height', weight='$weight', email='$email', ability='$specialty', sns='$sns', detailInfo='$detailInfo', detailInfoTextLimit=$detailInfoTextLimit, career=$career, careerDetail='$careerDetail', careerDetailTextLimit=$careerDetailTextLimit, categories=$categories, categoryTagEnable=$categoryTagEnable, registerButtonEnable=$registerButtonEnable, actorProfileUiEvent=$actorProfileUiEvent, focusEvent=$focusEvent)"
    }
}

data class ActorProfileUiModel(
    val pictureList: List<String>,
    val name: String,
    val nameTextLimit: Int = 10,
    val hookingComments: String,
    val commentsTextLimit: Int,
    val birthday: String,
    val gender: Gender?,
    val genderTagEnable: Boolean,
    val height: String,
    val weight: String,
    val email: String,
    val specialty: String,
    val specialtyTextLimit: Int = 50,
    val sns: String,
    val detailInfo: String,
    val detailInfoTextLimit: Int,
    val career: Career?,
    val careerDetail: String,
    val careerDetailTextLimit: Int,
    val categories: Set<Category>,
    val categoryTagEnable: Boolean,
    val registerButtonEnable: Boolean,
    val actorProfileUiEvent: ActorProfileUiEvent,
    val focusEvent: ActorProfileFocusEvent?,
)

sealed class ActorProfileUiEvent {
    object RegisterComplete : ActorProfileUiEvent()
    object Clear : ActorProfileUiEvent()
}

sealed interface ActorProfileFocusEvent {
    object PictureList : ActorProfileFocusEvent
    object Name : ActorProfileFocusEvent
    object HookingComment : ActorProfileFocusEvent
    object Birthday : ActorProfileFocusEvent
    object Height : ActorProfileFocusEvent
    object Weight : ActorProfileFocusEvent
    object Email : ActorProfileFocusEvent
    object Detail : ActorProfileFocusEvent
    object Career : ActorProfileFocusEvent
    object CareerDetail : ActorProfileFocusEvent
}
