package com.grandemc.fazendas.bukkit.view.market.sell.menu

import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.copyNBTs
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class MarketSellProcessor(
    private val storageManager: StorageManager
) : MenuItemsProcessor<MarketSellContext> {
    override fun process(player: Player, data: MarketSellContext?, items: MenuItems): Collection<SlotItem> {
        requireNotNull(data)
        return newItemProcessing(items) {
            if (data.materialId == null)
                remove("material_escolhido")
            else {
                val materialConfig = storageManager.materialData(data.materialId)
                remove("material_nada")
                modify("material_escolhido") {
                    materialConfig.item
                        .displayFrom(it)
                        .copyNBTs(it)
                        .formatName("{nome}" to materialConfig.name)
                }
            }

            modify("escolher_quantia") {
                it.formatLore("{quantia}" to data.amount.commaFormat())
            }

            modify("escolher_preco") {
                it.formatLore("{preco}" to data.price.toFormat())
            }

            if (data.materialId == null) {
                remove("vender_quantia")
                remove("vender_possivel")
            }

            else if (storageManager.has(player.uniqueId, data.materialId, data.amount)) {
                remove("vender_iten")
                remove("vender_quantia")
            }
        }
    }
}