package com.grandemc.fazendas.bukkit.view.market.selling

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.global.commaFormat
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
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import net.minecraft.server.v1_8_R3.NBTTagInt
import org.bukkit.entity.Player

class MarketSellingProcessor(
    private val marketManager: MarketManager,
    private val storageManager: StorageManager,
    private val itemsConfig: ItemsChunk
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        val baseItems = items.values()
        val playerProducts = marketManager
            .getPlayerProducts(player.uniqueId)

        if (playerProducts.isEmpty())
            return items.values() + marketManager.emptyItems().sellerItem

        val marketItems = playerProducts
            .zip(GrandeFazendas.SLOTS_PATTERN)
            .map { (marketItem, slot) ->
                val materialConfig = storageManager.materialData(marketItem.itemId)
                val item = materialConfig.item
                    .displayFrom(itemsConfig.value("mercado_iten_a_venda"))
                    .formatName("{id}" to marketItem.id().toString())
                    .formatLore(
                        "{produto}" to materialConfig.name,
                        "{quantia}" to marketItem.amount.commaFormat(),
                        "{preco}" to marketItem.goldPrice.toFormat(),
                        "{expiracao}" to marketItem.expiryTime().timeFormat()
                    )
                    .addNBTValue(
                        NBTReference.VIEW,
                        "gfazendas.market.selling.product",
                        NBTTagInt(marketItem.id())
                    )
                SlotItem(slot, item)
            }
        return baseItems + marketItems
    }
}