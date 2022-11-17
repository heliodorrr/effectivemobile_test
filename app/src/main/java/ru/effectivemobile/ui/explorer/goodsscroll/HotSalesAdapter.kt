package ru.effectivemobile.ui.explorer.goodsscroll

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.effectivemobile.R
import ru.effectivemobile.databinding.GoodsScrollHotSalesViewholderBinding
import ru.effectivemobile.model.HotSalesGood
import ru.effectivemobile.ui.utils.pixelSize
import ru.effectivemobile.ui.utils.shimmerGlideRequestBuilder


internal fun attachHotSalesToViewPager(
    vp: ViewPager2,
    onClick: (Int)->Unit
): (List<HotSalesGood>)->Unit {

    attachHotSalesDecorations(vp)

    val adapter = ListDelegationAdapter(hotSalesDelegate(onClick))
    vp.adapter = adapter

    return {
        if (adapter.items!=it) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }

    }
}

private fun hotSalesDelegate(onClick: (Int) -> Unit)
= adapterDelegateViewBinding<HotSalesGood, HotSalesGood, GoodsScrollHotSalesViewholderBinding>(
    viewBinding = { li, vg->
        GoodsScrollHotSalesViewholderBinding.inflate(li, vg, false)
    }
) {

    bind {
        shimmerGlideRequestBuilder(binding.hotSalesShimmer)
            .load(item.picture)
            .into(binding.hotSalesImage)

        binding.isNew.visibility = if (item.isNew) View.VISIBLE else View.INVISIBLE
        binding.hotSalesTitle.text = item.title
        binding.hotSalesSubtitle.text = item.subtitle

        if (item.isBuy) {
            binding.hotSalesBuyButton.setOnClickListener {
                onClick(item.id)
            }
        } else {
            binding.hotSalesBuyButton.setOnClickListener {  }
        }
    }
}

private fun attachHotSalesDecorations(
    vp: ViewPager2,
) {
    val offset = vp.context.pixelSize(R.dimen.fragment_explorer_side_paddings)

    vp.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = offset
            outRect.right = offset
        }
    }
    )
}