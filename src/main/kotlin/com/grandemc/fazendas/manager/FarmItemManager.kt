package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.config.LootBoxConfig
import com.grandemc.fazendas.global.dottedFormat
import com.grandemc.fazendas.global.intFormat
import com.grandemc.fazendas.global.timeFormat
import com.grandemc.fazendas.storage.player.model.CustomEnchant
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.global.*
import com.grandemc.post.external.lib.global.bukkit.*
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.global.bukkit.nms.addReferenceId
import net.minecraft.server.v1_8_R3.NBTTagByte
import net.minecraft.server.v1_8_R3.NBTTagFloat
import net.minecraft.server.v1_8_R3.NBTTagShort
import org.bukkit.inventory.ItemStack
import java.util.UUID

class FarmItemManager(
    private val itemsConfig: ItemsChunk,
    private val fertilizingConfig: FertilizingConfig,
    private val lootBoxConfig: LootBoxConfig,
    private val playerManager: PlayerManager
) {
    fun createFertilizing(fertilizing: FertilizingConfig.Fertilizing): ItemStack {
        return itemsConfig
            .value("fertilizante")
            .formatName("{nome}" to fertilizing.name)
            .formatLore("{reducao}" to fertilizing.boost.intFormat())
            .addNBTValue(
                NBTReference.ITEM, "gfazendas.fertilizing", NBTTagByte(fertilizing.id)
            )
    }

    fun createFertilizing(id: Byte): ItemStack? {
        val fertilizing = fertilizingConfig.get().getById(id).let {
            if (it == null)
                return null
            it
        }

        return createFertilizing(fertilizing)
    }

    fun createLootBox(id: Byte): ItemStack? {
        val lootBox = lootBoxConfig.get().getLootBox(id).let {
            if (it == null)
                return null
            it
        }

        return createLootBox(lootBox)
    }

    fun createLootBox(lootBox: LootBoxConfig.LootBox): ItemStack {
        return itemsConfig
            .value("lootbox")
            .formatName("{nome}" to lootBox.name)
            .formatLoreList(
                "{<conteudo>}" to lootBox.content.map {
                    lootBoxConfig.get().boosterFormat()
                        .formatReplace(
                            "{boost}" to it.booster.boost.intFormat(),
                            "{duracao}" to it.booster.duration.timeFormat(),
                            "{chance}" to it.chance.intFormat()
                        )
                }
            )
            .addNBTValue(
                NBTReference.ITEM, "gfazendas.lootbox", NBTTagByte(lootBox.id)
            )
    }

    fun createBooster(boosterModel: LootBoxConfig.Booster): ItemStack {
        return itemsConfig
            .value("booster_xp")
            .formatLore(
                "{multiplicador}" to boosterModel.boost.intFormat(),
                "{duracao}" to boosterModel.duration.timeFormat()
            )
            .addNBTValue(
                NBTReference.ITEM, "gfazendas.xp_booster.multiplier", NBTTagFloat(
                    boosterModel.boost
                )
            )
            .addNBTValue(
                NBTReference.ITEM, "gfazendas.xp_booster.duration", NBTTagShort(
                    boosterModel.duration
                )
            )
    }

    fun createFarmTool(playerId: UUID): ItemStack {
        return itemsConfig
            .value("enxada_farm")
            .addReferenceId(NBTReference.ITEM, "gfazendas.farm_tool")
            .clone()
            .let { updateFarmTool(playerId, it) }
    }

    private fun updateFarmTool(playerId: UUID, farmTool: ItemStack): ItemStack {
        updateFarmToolName(playerId, farmTool)
        updateFarmToolLore(playerId, farmTool)
        return farmTool
    }

    fun updateFarmToolName(playerId: UUID, farmTool: ItemStack) {
        val farmHoe = playerManager.player(playerId).hoe()
        val itemModel = itemsConfig.value("enxada_farm")
        farmTool.setName(
            itemModel.itemMeta.displayName.formatReplace(
                "{contagem}" to farmHoe.collectCount().toFormat()
            )
        )
    }

    fun updateFarmToolLore(playerId: UUID, farmTool: ItemStack) {
        val farmHoe = playerManager.player(playerId).hoe()
        val itemModel = itemsConfig.value("enxada_farm")
        farmTool.setLore(
            itemModel.itemMeta.lore.format(
                "{experiente}" to farmHoe.enchantLevel(CustomEnchant.EXPERIENT).dottedFormat(),
                "{replantar}" to farmHoe.enchantLevel(CustomEnchant.REPLANT).dottedFormat(),
                "{radar}" to farmHoe.enchantLevel(CustomEnchant.RADAR).dottedFormat()
            )
        )
    }
}