package ru.effectivemobile.repository

import ru.effectivemobile.model.GoodsQuery

interface GoodsQueryRepository {
    suspend fun getGoodsQuery(): GoodsQuery
}