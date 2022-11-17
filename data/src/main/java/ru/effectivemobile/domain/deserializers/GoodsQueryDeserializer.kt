package ru.effectivemobile.domain.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.effectivemobile.domain.model.BestSellerGoodDto
import ru.effectivemobile.domain.model.HotSalesGoodDto
import ru.effectivemobile.model.BestSellerGood
import ru.effectivemobile.model.GoodsQuery
import ru.effectivemobile.model.HotSalesGood
import java.lang.reflect.Type

val JsonElement.stringOrNull: String? get() = if (isJsonNull) null else asString

class GoodsQueryDeserializer: JsonDeserializer<GoodsQuery> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GoodsQuery {
        if (context==null) throw IllegalStateException()
        val root = json ?: throw IllegalStateException()

        val hotSalesGoods = root.asJsonObject?.get("home_store")?.asJsonArray?.map {
            context.deserialize<HotSalesGoodDto>(it, HotSalesGoodDto::class.java)
                .toHotSalesGood()
        } ?: throw IllegalStateException()

        val bestSellerGoods = root.asJsonObject?.get("best_seller")?.asJsonArray?.map {
            context.deserialize<BestSellerGoodDto>(it, BestSellerGoodDto::class.java)
                .toBestSellerGood()
        } ?: throw IllegalStateException()

        return GoodsQuery(hotSalesGoods, bestSellerGoods)
    }

}