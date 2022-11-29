package com.grandemc.fazendas.bukkit.view.market.purchase

import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.copyNBTs
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.offlinePlayer
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class MarketPurchaseProcessor(
    private val storageManager: StorageManager
) : MenuItemsProcessor<MarketPurchaseContext> {
    override fun process(player: Player, data: MarketPurchaseContext?, items: MenuItems): Collection<SlotItem> {
        requireNotNull(data)
        return newItemProcessing(items) {
            val product = data.product
            val materialConfig = storageManager.materialData(product.itemId)

            modify("info") {
                materialConfig.item
                    .displayFrom(it)
                    .copyNBTs(it)
                    .formatLore(
                        "{vendedor}" to product.sellerId.offlinePlayer().name,
                        "{produto}" to materialConfig.name,
                        "{quantia}" to product.amount.commaFormat(),
                        "{preco}" to product.goldPrice.toFormat()
                    )
            }
        }
    }
}