package com.grandemc.fazendas.bukkit.view.storage

import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.config.StorageConfig
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player

class StorageProcessor(
    private val storageManager: StorageManager,
    private val storageConfig: StorageConfig,
    private val materialsConfig: MaterialsConfig,
    private val itemsConfig: ItemsChunk,
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        val baseItems = items.values()
        val storageItems = storageManager
            .items(player.uniqueId)
            .zip(storageConfig.get().menuSlots)
            .map { (storageItem, slot) ->
                val materialConfig = materialsConfig.get().getById(storageItem.id)
                val item = materialConfig.item
                    .displayFrom(itemsConfig.value("material_armazem"))
                    .formatName("{nome}" to materialConfig.name)
                    .formatLore("{quantia}" to storageItem.amount.commaFormat())
                    .addNBTValue(
                        NBTReference.VIEW,
                        "gfazendas.storage.item",
                        NBTTagByte(storageItem.id)
                    )
                SlotItem(slot, item)
            }
        return baseItems + storageItems
    }
}