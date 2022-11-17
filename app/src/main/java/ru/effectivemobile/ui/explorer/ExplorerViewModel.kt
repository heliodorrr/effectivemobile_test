package ru.effectivemobile.ui.explorer

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.effectivemobile.R
import ru.effectivemobile.Result
import ru.effectivemobile.model.Good
import ru.effectivemobile.model.GoodsQuery
import ru.effectivemobile.ui.explorer.goodsscroll.GoodsScrollItem
import ru.effectivemobile.usecase.GoodsDataUseCase
import ru.effectivemobile.utils.fastlog
import javax.inject.Inject


typealias GoodsFilter = (Good)->Boolean

class ExplorerViewModel @Inject constructor(
    private val goodsDataUseCase: GoodsDataUseCase,
    context: Context
): ViewModel() {
    private companion object {
        const val InitialCategoryIndex = 0
    }

    private val hotSalesHeader: String = context.getString(R.string.goods_scroll_hot_sales_header)
    private val bestSellerHeader: String = context.getString(R.string.goods_scroll_best_seller_header)


    private val categoryIndex = MutableLiveData(InitialCategoryIndex)
    internal val categoryNotifier: (Int)->Unit = { categoryIndex.value = it }

    private val goodsQueryStateFlow = MutableStateFlow(GoodsQuery(emptyList(), emptyList()))

    internal val filterStateFlow = MutableStateFlow<GoodsFilter> { true }

    private val brandsLiveData = goodsQueryStateFlow
        .map {
            HashSet<String>().apply {
                it.bestSellerGood.forEach { add(it.brand) }
            }.toList()
        }
        .flowOn(Dispatchers.Default)
        .asLiveData()

    private val goodsScrollItemsLiveData = goodsQueryStateFlow
        .combine(filterStateFlow) { gq, filter->
            fastlog("${Thread.currentThread().name}")
            mutableListOf<GoodsScrollItem>().apply {
                //HotSalesHeader
                add(GoodsScrollItem.HeaderItem(hotSalesHeader))
                    //HotSales
                add(GoodsScrollItem.HotSalesViewPagerItem(gq.hotSalesGoods))
                //BestSellerHeader
                add(GoodsScrollItem.HeaderItem(bestSellerHeader))
                //BestSeller
                gq.bestSellerGood.forEach {
                    if (filter(it)) {
                        add(GoodsScrollItem.BestSellerGoodItem(it))
                    }
                }
            }
        }
        .flowOn(Dispatchers.Default)
        .asLiveData()

    private var onFavoriteChanged: (Int)->Unit = {}

    internal val favoriteChanged: (Int)->Unit = {
        val item = goodsScrollItemsLiveData.value!![it] as GoodsScrollItem.BestSellerGoodItem
        val isFav = item.data.isFavorites
        goodsScrollItemsLiveData.value!![it]=
            item.copy(data = item.data.copy(isFavorites = !isFav))
        onFavoriteChanged(it)
    }

    init {

        viewModelScope.launch(Dispatchers.IO) {
            goodsDataUseCase.invoke().collect {
                when(it) {
                    is Result.Success<GoodsQuery> -> goodsQueryStateFlow.value = it.data
                    else -> {}
                }
            }

        }
    }

    internal fun setOnFavoriteChanged(onFavoriteChanged: (Int)->Unit) {
        this.onFavoriteChanged = onFavoriteChanged
    }

    internal fun subscriberToBrands(
        lo: LifecycleOwner,
        onBrandsChanged: (List<String>)->Unit
    ) {
        brandsLiveData.observe(lo) { onBrandsChanged(it) }
    }

    internal fun subscribeToCategoryIndex(lo: LifecycleOwner, lambda: (Int)->Unit) {
        categoryIndex.observe(lo, lambda)
    }

    internal fun subscribeGoodsScrollData(
        lo: LifecycleOwner,
        lambda: (List<GoodsScrollItem>) -> Unit
    ) {
        goodsScrollItemsLiveData.observe(lo,lambda)
    }

}