package ru.effectivemobile.ui.explorer.goodsscroll

import ru.effectivemobile.model.BestSellerGood
import ru.effectivemobile.model.HotSalesGood

sealed class GoodsScrollItem {
    data class HeaderItem(val headerName: String): GoodsScrollItem()
    data class BestSellerGoodItem(val data: BestSellerGood): GoodsScrollItem()
    data class HotSalesViewPagerItem(val data: List<HotSalesGood>): GoodsScrollItem()
}



