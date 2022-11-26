package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.global.intFormat
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.inventory.ItemStack

class FarmItemManager(
    private val itemsConfig: ItemsChunk,
    private val fertilizingConfig: FertilizingConfig
) {
    fun createFertilizing(id: Byte): ItemStack? {
        val fertilizing = fertilizingConfig.get().getById(id).let {
            if (it == null)
                return null
            it
        }

        return itemsConfig
            .value("fertilizante")
            .formatName("{nome}" to fertilizing.name)
            .formatLore("{reducao}" to fertilizing.boost.intFormat())
            .addNBTValue(NBTReference.ITEM, "gfazendas.fertilizing", NBTTagByte(id))
    }
}