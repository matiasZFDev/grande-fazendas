package com.grandemc.fazendas.bukkit.view.market.sell.material

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellContext
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player

class MarketSellMaterialProcessor(
    private val storageManager: StorageManager,
    private val itemsConfig: ItemsChunk
) : MenuItemsProcessor<MarketSellContext> {
    override fun process(
        player: Player, data: MarketSellContext?, items: MenuItems
    ): Collection<SlotItem> {
        requireNotNull(data)
        val baseItems = items.values()
        val materialItems = storageManager
            .items(player.uniqueId)
            .zip(GrandeFazendas.SLOTS_PATTERN)
            .map { (storageItem, slot) ->
                val materialConfig = storageManager.materialData(storageItem.id)
                val item = materialConfig.item
                    .displayFrom(itemsConfig.value("mercado_venda_escolher"))
                    .formatName("{nome}" to materialConfig.name)
                    .addNBTValue(
                        NBTReference.VIEW,
                        "gfazendas.market.sell.select.material",
                        NBTTagByte(storageItem.id)
                    )
                SlotItem(slot, item)
            }
        return baseItems + materialItems
    }
}