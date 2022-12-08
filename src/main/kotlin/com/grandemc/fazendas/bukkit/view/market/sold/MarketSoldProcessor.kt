package com.grandemc.fazendas.bukkit.view.market.sold

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.fazendas.manager.controller.MarketSoldItemController
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.ApplyType
import com.grandemc.post.external.lib.global.applyPercentage
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.global.intFormat
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import net.minecraft.server.v1_8_R3.NBTTagInt
import org.bukkit.entity.Player

class MarketSoldProcessor(
    private val soldItemController: MarketSoldItemController,
    private val marketManager: MarketManager,
    private val storageManager: StorageManager,
    private val itemsConfig: ItemsChunk
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        val baseItems = items.values()
        val soldItems = soldItemController
            .getPlayerItems(player.uniqueId)
            .also {
                if (it.isEmpty())
                    return baseItems + marketManager.emptyItems().soldItem
            }
            .zip(GrandeFazendas.SLOTS_PATTERN)
            .mapIndexed { index, (product, slot) ->
                val materialConfig = storageManager.materialData(
                    product.itemId
                )
                val goldWithTax = product.goldPrice.applyPercentage(
                    marketManager.tax(), ApplyType.DECREMENT
                )
                val item = itemsConfig
                    .value("mercado_produto_vendido")
                    .formatName("{posicao}" to index.inc().toString())
                    .formatLore(
                        "{produto}" to materialConfig.name,
                        "{quantia}" to product.amount.commaFormat(),
                        "{preco}" to product.goldPrice.toFormat(),
                        "{taxa}" to marketManager.tax().intFormat(),
                        "{obtido}" to goldWithTax.toFormat()
                    )
                    .addNBTValue(
                        NBTReference.VIEW,
                        "gfazendas.market.sold.product",
                        NBTTagInt(product.id())
                    )
                SlotItem(slot, item)
            }
        return baseItems + soldItems
    }
}