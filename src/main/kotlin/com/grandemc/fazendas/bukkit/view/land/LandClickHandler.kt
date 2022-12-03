package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.fazendas.bukkit.view.LandPlantView
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.fazendas.manager.LandPlantManager
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class LandClickHandler(
    private val landManager: LandManager,
    private val islandManager: IslandManager,
    private val goldBank: GoldBank,
    private val landPlantManager: LandPlantManager,
    private val cropsConfig: CropsConfig
) : ViewClickHandler<LandContext> {
    override fun onClick(player: Player, data: LandContext?, item: ItemStack, event: InventoryClickEvent) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.land") {
            when (it) {
                "plant" -> player.openView(LandPlantView::class, data)
                "evolve" -> {
                    val requiredGold = landManager.landLevelConfig(
                        player.uniqueId, data.landId
                    ).evolution!!.gold
                    val land = landManager.land(player.uniqueId, data.landId)

                    if (!goldBank.has(player.uniqueId, requiredGold)) {
                        player.closeInventory()
                        player.respond("global.error")
                        return@useReferenceIfPresent
                    }

                    goldBank.withdraw(player.uniqueId, requiredGold)
                    landManager.upgradeLand(player.uniqueId, data.landId)
                    land.cropId()?.let { cropId ->
                        if (!land.isResetting())
                            return@let

                        val cropData = cropsConfig.get().getCrop(cropId)
                        landPlantManager.plant(player.uniqueId, data.landId, cropData)
                    }
                    islandManager.updateLandHologram(player.uniqueId, data.landId)
                    player.closeInventory()
                    player.respond("plantio.upado")
                }
            }
        }
    }
}