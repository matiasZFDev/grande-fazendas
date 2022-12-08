package com.grandemc.fazendas.bukkit.view.market.category

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.global.cut
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.fazendas.manager.model.MarketFilter
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.global.bukkit.offlinePlayer
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import net.minecraft.server.v1_8_R3.NBTTagInt
import org.bukkit.entity.Player

class MarketCategoryProcessor(
    private val marketManager: MarketManager,
    private val itemsConfig: ItemsChunk,
    private val storageManager: StorageManager
) : MenuItemsProcessor<MarketCategoryContext> {
    override fun process(
        player: Player, data: MarketCategoryContext?, items: MenuItems
    ): Collection<SlotItem> {
        requireNotNull(data)
        val productsPerPage = GrandeFazendas.SLOTS_PATTERN.size
        val categorizedItems = marketManager.getProductsById(
            data.categoryId, data.filter
        )

        if (categorizedItems.isEmpty())
            return newItemProcessing(items) {
                remove("anterior")
                remove("seguinte")
            } + marketManager.emptyItems().productsItem

        val paginatedItems = categorizedItems.cut(
            productsPerPage * data.page, productsPerPage
        )
        val materialConfig = storageManager.materialData(data.categoryId)
        val marketItems = paginatedItems
            .zip(GrandeFazendas.SLOTS_PATTERN)
            .map { (product, slot) ->
                val item = if (product.sellerId == player.uniqueId)
                    materialConfig.item
                        .displayFrom(itemsConfig.value("mercado_produto_jogador"))
                        .formatName(
                            "{nome}" to materialConfig.name,
                            "{id}" to product.id().toString()
                        )
                        .formatLore(
                            "{produto}" to materialConfig.name,
                            "{quantia}" to product.amount.commaFormat(),
                            "{preco}" to product.goldPrice.toFormat(),
                            "{expiracao}" to product.expiryTime().timeFormat()
                        )
                else
                    materialConfig.item
                        .displayFrom(itemsConfig.value("mercado_produto"))
                        .formatName(
                            "{nome}" to materialConfig.name,
                            "{id}" to product.id().toString()
                        )
                        .formatLore(
                            "{vendedor}" to product.sellerId.offlinePlayer().name,
                            "{produto}" to materialConfig.name,
                            "{quantia}" to product.amount.commaFormat(),
                            "{preco}" to product.goldPrice.toFormat(),
                            "{expiracao}" to product.expiryTime().timeFormat()
                        )
                        .addNBTValue(
                            NBTReference.VIEW,
                            "gfazendas.market.category.product",
                            NBTTagInt(product.id())
                        )
                SlotItem(slot, item)
            }
        val baseItems = newItemProcessing(items) {
            if (data.page == (0).toByte())
                remove("anterior")

            if (productsPerPage * data.page + productsPerPage >= categorizedItems.size)
                remove("seguinte")

            MarketFilter.values().forEach {
                if (it != data.filter)
                    remove(it.configName())
            }
        }
        return baseItems + marketItems
    }
}