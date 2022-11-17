package ru.effectivemobile.model

data class GoodsQuery(
    val hotSalesGoods: List<HotSalesGood>,
    val bestSellerGood: List<BestSellerGood>
)