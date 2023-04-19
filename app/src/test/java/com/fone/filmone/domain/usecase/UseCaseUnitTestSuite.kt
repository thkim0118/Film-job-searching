package com.fone.filmone.domain.usecase

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CheckNicknameDuplicationUseCaseTest::class,
    GetUserInfoUseCaseTest::class,
    RequestPhoneVerificationUseCaseTest::class,
    SignInUseCaseTest::class,
    SignUpUseCaseTest::class,
    SubmitInquiryUseCaseTest::class,
    UpdateUserInfoUseCaseTest::class,
    UploadImageUseCaseTest::class,
    VerifySmsCodeUseCaseTest::class
)
class UseCaseUnitTestSuite