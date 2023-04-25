package com.fone.filmone.domain.usecase.suite

import com.fone.filmone.domain.usecase.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CheckNicknameDuplicationUseCaseTest::class,
    GetMyJobOpeningsScrapsUseCaseTest::class,
    GetUserInfoUseCaseTest::class,
    RequestPhoneVerificationUseCaseTest::class,
    SignInUseCaseTest::class,
    SignUpUseCaseTest::class,
    SubmitInquiryUseCaseTest::class,
    UpdateUserInfoUseCaseTest::class,
    UploadImageUseCaseTest::class,
    VerifySmsCodeUseCaseTest::class,
    GetCompetitionsUseCaseTest::class,
    LogoutUseCaseTest::class,
    SignOutUseCaseTest::class,
    GetFavoriteProfilesUseCaseTest::class
)
class UseCaseUnitTestSuite