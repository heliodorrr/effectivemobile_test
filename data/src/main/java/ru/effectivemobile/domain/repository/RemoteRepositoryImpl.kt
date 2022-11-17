package ru.effectivemobile.domain.repository

import ru.effectivemobile.domain.api.TestApi
import ru.effectivemobile.model.GoodsQuery
import ru.effectivemobile.model.SmartphoneGoodDetails
import ru.effectivemobile.repository.GoodsQueryRepository
import ru.effectivemobile.repository.SmartphoneDetailsRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val api: TestApi
): GoodsQueryRepository, SmartphoneDetailsRepository {
    override suspend fun getGoodsQuery(): GoodsQuery {
        return api.getGoodsQuery()
    }

    override suspend fun getSmartphoneDetails(): SmartphoneGoodDetails {
        return api.getSmartphoneDetails().toSmartphoneGoodDetails()
    }


}