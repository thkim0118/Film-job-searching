package com.fone.filmone.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fone.filmone.data.datamodel.fakeFavoriteProfilesResponse
import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.repository.ProfilesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetFavoriteProfilesActorUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val profilesRepository = mock<ProfilesRepository>()

    private val getFavoriteProfilesActorUseCase by lazy {
        GetFavoriteProfilesActorUseCase(profilesRepository)
    }

    @Test
    fun get_favorite_profiles_success(): Unit = runBlocking {
        whenever(profilesRepository.getFavoriteProfiles(page = 1, type = Type.ACTOR))
            .thenReturn(
                DataResult.Success(fakeFavoriteProfilesResponse)
            )

        getFavoriteProfilesActorUseCase(page = 1)
            .onSuccess {
                assert(true)
            }.onFail {
                assert(false)
            }
    }
}