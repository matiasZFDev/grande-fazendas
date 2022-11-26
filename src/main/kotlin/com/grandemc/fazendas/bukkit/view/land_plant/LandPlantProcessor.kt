package com.grandemc.fazendas.bukkit.view.land_plant

import com.grandemc.fazendas.bukkit.view.land.LandContext
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player

class LandPlantProcessor(
    private val itemsConfig: ItemsChunk,
    private val cropsConfig: CropsConfig,
    private val farmManager: FarmManager
) : MenuItemsProcessor<LandContext> {
    override fun process(player: Player, data: LandContext?, items: MenuItems): Collection<SlotItem> {
        requireNotNull(data)
        val baseItems = items.values()
        val cropItems = cropsConfig.get().crops()
            .zip(cropsConfig.get().menuSlots)
            .map { (crop, slot) ->
                val itemKey = if (farmManager.farm(player.uniqueId).level() >= crop.islandLevel)
                    "plantacao_disponivel"
                else
                    "plantacao_nao_disponivel"
                val item = crop.item
                    .displayFrom(itemsConfig.value(itemKey))
                    .formatName("{nome}" to crop.name).formatLore(
                        "{nivel}" to crop.islandLevel.toString(),
                        "{geracao}" to crop.reset.timeFormat()
                    )
                    .let {
                        if (itemKey == "plantacao_disponivel")
                            it.addNBTValue(
                                NBTReference.VIEW, "gfazendas.plant.crop", NBTTagByte(
                                    crop.id
                                )
                            )
                        else
                            it
                    }
                SlotItem(slot, item)
            }
        return baseItems + cropItems
    }
}