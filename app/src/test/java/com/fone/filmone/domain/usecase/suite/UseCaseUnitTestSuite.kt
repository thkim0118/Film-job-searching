package com.fone.filmone.domain.usecase.suite

import com.fone.filmone.domain.usecase.CheckNicknameDuplicationUseCaseTest
import com.fone.filmone.domain.usecase.GetCompetitionsScrapsUseCaseTest
import com.fone.filmone.domain.usecase.GetFavoriteProfilesActorUseCaseTest
import com.fone.filmone.domain.usecase.GetJobOpeningsActorScrapsUseCaseTest
import com.fone.filmone.domain.usecase.GetUserInfoUseCaseTest
import com.fone.filmone.domain.usecase.LogoutUseCaseTest
import com.fone.filmone.domain.usecase.RequestPhoneVerificationUseCaseTest
import com.fone.filmone.domain.usecase.SignOutUseCaseTest
import com.fone.filmone.domain.usecase.SignUpUseCaseTest
import com.fone.filmone.domain.usecase.SocialSignInUseCaseTest
import com.fone.filmone.domain.usecase.SubmitInquiryUseCaseTest
import com.fone.filmone.domain.usecase.UpdateUserInfoUseCaseTest
import com.fone.filmone.domain.usecase.UploadImageUseCaseTest
import com.fone.filmone.domain.usecase.VerifySmsCodeUseCaseTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CheckNicknameDuplicationUseCaseTest::class,
    GetJobOpeningsActorScrapsUseCaseTest::class,
    GetUserInfoUseCaseTest::class,
    RequestPhoneVerificationUseCaseTest::class,
    SocialSignInUseCaseTest::class,
    SignUpUseCaseTest::class,
    SubmitInquiryUseCaseTest::class,
    UpdateUserInfoUseCaseTest::class,
    UploadImageUseCaseTest::class,
    VerifySmsCodeUseCaseTest::class,
    GetCompetitionsScrapsUseCaseTest::class,
    LogoutUseCaseTest::class,
    SignOutUseCaseTest::class,
    GetFavoriteProfilesActorUseCaseTest::class
)
class UseCaseUnitTestSuite
