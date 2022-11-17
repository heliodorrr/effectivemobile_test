package ru.effectivemobile.ui.explorer.goodsscroll

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.effectivemobile.R
import ru.effectivemobile.databinding.ExplorerHeaderBinding
import ru.effectivemobile.databinding.GoodsScrollBestSellerViewholderBinding
import ru.effectivemobile.ui.utils.ListSubscriber
import ru.effectivemobile.ui.explorer.goodsscroll.GoodsScrollItem.HeaderItem
import ru.effectivemobile.ui.explorer.goodsscroll.GoodsScrollItem.HotSalesViewPagerItem
import ru.effectivemobile.ui.explorer.goodsscroll.GoodsScrollItem.BestSellerGoodItem
import ru.effectivemobile.ui.utils.DataSubscriber
import ru.effectivemobile.ui.utils.decoratePrice
import ru.effectivemobile.ui.utils.shimmerGlideRequestBuilder
import ru.effectivemobile.utils.fastlog

fun attachGoodsScrollAdapterToRecycler(
    rv: RecyclerView,
    dataSubscriber: ListSubscriber<GoodsScrollItem>,
    notifyFavoriteChanged: (Int)->Unit ,
    notifierSubscriber: DataSubscriber<Int>,
    onClick: (Int)->Unit,
) {
    val adapter = ListDelegationAdapter<List<GoodsScrollItem>>(
        headerDelegate(),
        hotSalesViewPagerDelegate(onClick),
        bestSellerDelegate(onClick, notifyFavoriteChanged = notifyFavoriteChanged)
    )

    dataSubscriber {
        adapter.items = it
        adapter.notifyDataSetChanged()
    }

    notifierSubscriber {
        adapter.notifyItemChanged(it)
    }

    attachGoodsScrollDecorations(rv, adapter)

    rv.adapter = adapter

}

private fun headerDelegate()
= adapterDelegateViewBinding<HeaderItem, GoodsScrollItem, ExplorerHeaderBinding>(
    viewBinding = { i, c-> ExplorerHeaderBinding.inflate(i,c,false) }
) {
    bind { binding.headerText.text = item.headerName }
}



private fun hotSalesViewPagerDelegate(onClick: (Int) -> Unit)
= adapterDelegate<HotSalesViewPagerItem, GoodsScrollItem>(
    layout = R.layout.goods_scroll_hot_sales_viewpager
) {

    val vp = itemView as ViewPager2
    val binder = attachHotSalesToViewPager(vp, onClick)

    bind {
        binder(item.data)
    }

}

private typealias BestSellerBinding = GoodsScrollBestSellerViewholderBinding

private fun bestSellerDelegate(
    onClick: (Int) -> Unit,
    notifyFavoriteChanged: (Int)->Unit,
) = adapterDelegateViewBinding<BestSellerGoodItem, GoodsScrollItem, BestSellerBinding>(
    viewBinding = { i,c -> BestSellerBinding.inflate(i, c, false) }
) {

    binding.root.setOnClickListener {
        onClick(item.data.id)
    }

    binding.bestSellerFavButton.setOnClickListener {
        notifyFavoriteChanged(adapterPosition)
    }

    binding.bestSellerPreviousPrice.run {
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    val favoriteChosenDrawable = getDrawable(R.drawable.icon_heart_filled)
    val favoriteNoChosenDrawable = getDrawable(R.drawable.icon_heart_hollow)

    bind {
        val data = item.data

        shimmerGlideRequestBuilder(binding.bestSellerShimmer)
            .load(data.picture)
            .into(binding.bestSellerImage)

        binding.bestSellerName.text = data.title
        binding.bestSellerPreviousPrice.text = decoratePrice(data.priceWithoutDiscount)
        binding.besetSellerPrice.text = decoratePrice(data.discountPrice)

        binding.bestSellerFavButton.setImageDrawable(
            if (data.isFavorites) favoriteChosenDrawable else favoriteNoChosenDrawable
        )

    }
}