package com.fone.filmone.domain.usecase.suite

import com.fone.filmone.domain.usecase.*
import com.fone.filmone.domain.usecase.CheckNicknameDuplicationUseCaseTest
import com.fone.filmone.domain.usecase.GetMyJobOpeningsInfoUseCaseTest
import com.fone.filmone.domain.usecase.GetUserInfoUseCaseTest
import com.fone.filmone.domain.usecase.RequestPhoneVerificationUseCaseTest
import com.fone.filmone.domain.usecase.SignInUseCaseTest
import com.fone.filmone.domain.usecase.SignUpUseCaseTest
import com.fone.filmone.domain.usecase.SubmitInquiryUseCaseTest
import com.fone.filmone.domain.usecase.UpdateUserInfoUseCaseTest
import com.fone.filmone.domain.usecase.UploadImageUseCaseTest
import com.fone.filmone.domain.usecase.VerifySmsCodeUseCaseTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CheckNicknameDuplicationUseCaseTest::class,
    GetMyJobOpeningsInfoUseCaseTest::class,
    GetUserInfoUseCaseTest::class,
    RequestPhoneVerificationUseCaseTest::class,
    SignInUseCaseTest::class,
    SignUpUseCaseTest::class,
    SubmitInquiryUseCaseTest::class,
    UpdateUserInfoUseCaseTest::class,
    UploadImageUseCaseTest::class,
    VerifySmsCodeUseCaseTest::class,
    GetCompetitionsUseCaseTest::class
)
class UseCaseUnitTestSuite