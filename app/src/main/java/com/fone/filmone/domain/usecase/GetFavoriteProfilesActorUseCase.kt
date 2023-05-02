package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.profiles.favorite.FavoriteProfilesResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ProfilesRepository
import javax.inject.Inject

class GetFavoriteProfilesActorUseCase @Inject constructor(
    private val profilesRepository: ProfilesRepository
) {
    suspend operator fun invoke(page: Int, size: Int = 20): DataResult<FavoriteProfilesResponse> {
        return profilesRepository.getFavoriteProfiles(
            page = page,
            size = size,
            type = Type.ACTOR
        )
    }
}