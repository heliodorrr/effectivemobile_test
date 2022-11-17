package ru.effectivemobile.domain.api

import retrofit2.http.GET
import ru.effectivemobile.domain.model.SmartphoneGoodDetailsDto
import ru.effectivemobile.model.GoodsQuery
import ru.effectivemobile.model.SmartphoneGoodDetails

interface TestApi {
    @GET("v3/654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getGoodsQuery(): GoodsQuery

    @GET("v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getSmartphoneDetails(): SmartphoneGoodDetailsDto
}