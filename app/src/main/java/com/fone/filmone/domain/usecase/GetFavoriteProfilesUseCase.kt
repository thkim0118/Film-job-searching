package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.profiles.favorite.FavoriteProfilesResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ProfilesRepository
import javax.inject.Inject

class GetFavoriteProfilesUseCase @Inject constructor(
    private val profilesRepository: ProfilesRepository
) {
    suspend operator fun invoke(): DataResult<FavoriteProfilesResponse> {
        return profilesRepository.getFavoriteProfiles()
    }
}