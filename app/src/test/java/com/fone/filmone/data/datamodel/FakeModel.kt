package com.fone.filmone.data.datamodel

import com.fone.filmone.data.datamodel.request.imageupload.ImageUploadRequest
import com.fone.filmone.data.datamodel.request.imageupload.StageVariables
import com.fone.filmone.data.datamodel.request.inquiry.InquiryRequest
import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.request.user.UserUpdateRequest
import com.fone.filmone.data.datamodel.response.common.Pageable
import com.fone.filmone.data.datamodel.response.common.Sort
import com.fone.filmone.data.datamodel.response.imageupload.ImageUploadResponse
import com.fone.filmone.data.datamodel.response.inquiry.InquiryResponse
import com.fone.filmone.data.datamodel.response.inquiry.Question
import com.fone.filmone.data.datamodel.response.jobopenings.*
import com.fone.filmone.data.datamodel.response.user.*
import com.fone.filmone.domain.model.inquiry.InquiryType
import com.fone.filmone.domain.model.inquiry.InquiryVo
import com.fone.filmone.ui.signup.model.SignUpVo

val fakeUser = User(
    agreeToPersonalInformation = true,
    agreeToTermsOfServiceTermsOfUse = true,
    birthday = "",
    email = "",
    enabled = true,
    gender = Gender.MAN,
    id = 0,
    interests = listOf(),
    isReceiveMarketing = true,
    job = Job.ACTOR,
    nickname = "",
    phoneNumber = "",
    profileUrl = "",
    socialLoginType = SocialLoginType.APPLE
)

val fakeSigninRequest = SigninRequest(
    "accessToken",
    "test@test.com",
    "GOOGLE"
)

val fakeToken = Token(
    accessToken = fakeSigninRequest.accessToken,
    expiresIn = 0,
    issuedAt = "",
    refreshToken = "refreshToken",
    tokenType = ""
)

val fakeSigninResponse = SigninResponse(
    fakeToken,
    fakeUser
)

val fakeSignUpVo = SignUpVo(
    accessToken = "accessToken",
    agreeToPersonalInformation = true,
    birthday = "birthday",
    email = "email",
    gender = "gender",
    interests = listOf(),
    isReceiveMarketing = true,
    job = "job",
    nickname = "nickname",
    phoneNumber = "phoneNumber",
    profileUrl = "profileUrl",
    socialLoginType = "socialLoginType",
    agreeToTermsOfServiceTermsOfUse = true
)

val fakeSignUpResponse = SignUpResponse(fakeUser)

val fakeSignUpRequest = SignUpRequest(
    accessToken = "accessToken",
    agreeToPersonalInformation = true,
    birthday = "birthday",
    email = "email",
    gender = "gender",
    interests = listOf(),
    isReceiveMarketing = true,
    job = "job",
    nickname = "nickname",
    phoneNumber = "phoneNumber",
    profileUrl = "profileUrl",
    socialLoginType = "socialLoginType",
    agreeToTermsOfServiceTermsOfUse = true
)

val fakeQuestion = Question(
    id = 0,
    agreeToPersonalInformation = true,
    description = "description",
    email = "email",
    title = "title",
    type = InquiryType.USE_QUESTION.name
)

val fakeInquiryVo = InquiryVo(
    agreeToPersonalInformation = true,
    description = "description",
    email = "email",
    title = "title",
    type = InquiryType.USE_QUESTION
)

val fakeInquiryRequest = InquiryRequest(
    agreeToPersonalInformation = true,
    description = "description",
    email = "email",
    title = "title",
    type = InquiryType.USE_QUESTION.name
)

val fakeInquiryResponse = InquiryResponse(fakeQuestion)

val fakeImageUploadRequest = ImageUploadRequest(
    imageData = "",
    resource = "/image-upload/user-profile",
    stageVariables = StageVariables(
        stage = "prod"
    )
)

val fakeImageUploadResponse = ImageUploadResponse("https://test.com")

val fakeUserResponse = UserResponse(fakeUser)

val fakeUserUpdateRequest = UserUpdateRequest(
    interests = listOf(Category.INDEPENDENT_FILM),
    job = Job.ACTOR,
    nickname = "user_update_test",
    profileUrl = "https://test.com"
)

val fakeWork = Work(
    details = "details",
    director = "director",
    email = "test@test.com",
    genre = "genre",
    location = "location",
    logline = "logline",
    manager = "manager",
    pay = "pay",
    period = "period",
    produce = "produce",
    workTitle = "workTitle"
)

val fakeJobContent = Content(
    ageMax = 20,
    ageMin = 0,
    career = Career.IRRELEVANT,
    casting = "casting",
    categories = listOf(Category.ETC),
    dday = "dday",
    deadline = "deadline",
    domains = listOf(Domain.ART),
    gender = Gender.MAN,
    id = 0,
    isScrap = true,
    numberOfRecruits = 0,
    scrapCount = 1,
    title = "title",
    type = Type.ACTOR,
    viewCount = 1,
    work = fakeWork
)

val fakeSort = Sort(
    empty = false,
    sorted = false,
    unsorted = false
)

val fakeJobOpenings = JobOpenings(
    content = listOf(fakeJobContent),
    empty = false,
    first = false,
    last = false,
    number = 1,
    numberOfElements = 1,
    pageable = Pageable(),
    size = 1,
    sort = fakeSort
)

val fakeMyJobOpeningsResponse = MyJobOpeningsResponse(
    jobOpenings = fakeJobOpenings
)