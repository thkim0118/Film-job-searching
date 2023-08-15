package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.home.HomeItemResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeItemsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): DataResult<HomeItemResponse> {
        return homeRepository.getHomeItems()
    }
}
