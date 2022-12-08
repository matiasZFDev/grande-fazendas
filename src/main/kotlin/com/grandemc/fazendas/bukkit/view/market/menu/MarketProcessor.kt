package com.grandemc.fazendas.bukkit.view.market.menu

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.bukkit.view.PageContext
import com.grandemc.fazendas.global.cut
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player

class MarketProcessor(
    private val marketManager: MarketManager,
    private val storageManager: StorageManager,
    private val itemsConfig: ItemsChunk
) : MenuItemsProcessor<PageContext>  {
    override fun process(player: Player, data: PageContext?, items: MenuItems): Collection<SlotItem> {
        requireNotNull(data)
        val categoriesPerPage = GrandeFazendas.SLOTS_PATTERN.size
        val categorizedItems = marketManager.getProducts()
            .fold(mutableMapOf<Byte, Double>()) { acc, cur ->
                acc[cur.itemId] = cur.amount + (acc[cur.itemId] ?: 0.0)
                acc
            }
            .entries.sortedBy { it.key }

        if (categorizedItems.isEmpty())
            return newItemProcessing(items) {
                remove("anterior")
                remove("seguinte")
            } + marketManager.emptyItems().categoriesItem

        val paginatedItems = categorizedItems
            .cut(categoriesPerPage * data.page, categoriesPerPage)
        val marketItems = paginatedItems
            .zip(GrandeFazendas.SLOTS_PATTERN)
            .map { (product, slot) ->
                val productConfig = storageManager.materialData(product.key)
                val item = productConfig.item
                    .displayFrom(itemsConfig.value("mercado_categoria"))
                    .formatName("{nome}" to productConfig.name)
                    .formatLore("{quantia}" to product.value.toFormat())
                    .addNBTValue(
                        NBTReference.VIEW,
                        "gfazendas.market.category",
                        NBTTagByte(product.key)
                    )
                SlotItem(slot, item)
            }
        val baseItems = newItemProcessing(items) {
            if (data.page == (0).toByte())
                remove("anterior")

            if (categoriesPerPage * data.page + categoriesPerPage >= categorizedItems.size)
                remove("seguinte")
        }
        return baseItems + marketItems
    }
}