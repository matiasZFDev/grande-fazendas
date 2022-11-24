package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.*
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.toByte
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.bukkit.BukkitWorld
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class LandsClickHandler(
    private val farmsConfig: FarmsConfig,
    private val landUpgradeManager: LandUpgradeManager,
    private val goldBank: GoldBank,
    private val storageManager: StorageManager,
    private val landManager: LandManager
) : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useNBTValueIfPresent<NBTTagByte>(
            NBTReference.VIEW,
            "gfazendas.lands.purchasable"
        ) {
            val farmId = it.toByte()
            val farm = farmsConfig.get().getFarmById(farmId)
            goldBank.withdraw(player.uniqueId, farm.config.requirements.gold)
            farm.config.requirements.items.forEach { requiredItem ->
                val materialId = storageManager.materialId(requiredItem.type)
                storageManager.withdraw(player.uniqueId, materialId, requiredItem.amount)
            }
            landUpgradeManager.buildLand(player.uniqueId, farmId, 1)
            player.closeInventory()
            player.respond("plantio.comprado")
        }
    }
}