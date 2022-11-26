package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.config.LootBoxConfig
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatLoreList
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.global.formatReplace
import com.grandemc.post.external.lib.global.intFormat
import com.grandemc.post.external.lib.global.timeFormat
import net.minecraft.server.v1_8_R3.NBTTagByte
import net.minecraft.server.v1_8_R3.NBTTagDouble
import net.minecraft.server.v1_8_R3.NBTTagInt
import org.bukkit.inventory.ItemStack

class FarmItemManager(
    private val itemsConfig: ItemsChunk,
    private val fertilizingConfig: FertilizingConfig,
    private val lootBoxConfig: LootBoxConfig
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

    fun createLootBox(id: Byte): ItemStack? {
        val lootBox = lootBoxConfig.get().getLootBox(id).let {
            if (it == null)
                return null
            it
        }

        return itemsConfig
            .value("lootbox")
            .formatName("{nome}" to lootBox.name)
            .formatLoreList(
                "{<conteudo>}" to lootBox.content.map {
                    lootBoxConfig.get().boosterFormat()
                        .formatReplace(
                            "{boost}" to it.boost.intFormat(),
                            "{duracao}" to it.duration.timeFormat(),
                            "{chance}" to it.chance.intFormat()
                        )
                }
            )
            .addNBTValue(NBTReference.ITEM, "gfazendas.lootbox", NBTTagByte(id))
    }

    fun createBooster(boosterModel: LootBoxConfig.ChanceBooster): ItemStack {
        return itemsConfig
            .value("booster_xp")
            .formatLore(
                "{multiplicador}" to boosterModel.boost.intFormat(),
                "{duracao}" to boosterModel.duration.timeFormat()
            )
            .addNBTValue(
                NBTReference.ITEM, "gfazendas.xp_booster.multiplier", NBTTagDouble(
                    boosterModel.boost
                )
            )
            .addNBTValue(
                NBTReference.ITEM, "gfazendas.xp_booster.duration", NBTTagInt(
                    boosterModel.duration
                )
            )
    }
}