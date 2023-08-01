package com.fone.filmone.domain.usecase.suite

import com.fone.filmone.domain.usecase.*
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