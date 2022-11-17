package ru.effectivemobile.usecase

import ru.effectivemobile.repository.GoodsQueryRepository
import ru.effectivemobile.utils.generateFlow
import javax.inject.Inject

class GoodsDataUseCase @Inject constructor(
    private val goodsQueryRepository: GoodsQueryRepository
) {
    fun invoke() = generateFlow{ goodsQueryRepository.getGoodsQuery() }
}

