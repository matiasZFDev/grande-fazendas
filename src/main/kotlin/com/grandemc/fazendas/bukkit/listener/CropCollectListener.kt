package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.global.findWorld
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.global.subtract
import com.grandemc.fazendas.global.toWeVector
import com.grandemc.fazendas.manager.*
import com.grandemc.post.external.lib.global.bukkit.isLeftClick
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.hasReferenceTag
import com.grandemc.post.external.lib.global.dottedFormat
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class CropCollectListener(
    private val islandConfig: IslandConfig,
    private val islandManager: IslandManager,
    private val landManager: LandManager,
    private val cropsConfig: CropsConfig,
    private val storageManager: StorageManager,
    private val farmItemManager: FarmItemManager,
    private val playerManager: PlayerManager
) : Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (!event.isLeftClick() || !event.hasItem() || !event.hasBlock())
            return

        if (event.player.world != islandConfig.get().worldName.findWorld())
            return

        if (!event.item.hasReferenceTag(NBTReference.ITEM, "gfazendas.farm_tool"))
            return

        val islandOrigin = islandManager.islandOrigin(event.player.uniqueId, false)
        val possibleCropVector = event.clickedBlock.location.toWeVector().subtract(
            islandOrigin.blockX,
            0,
            islandOrigin.blockZ
        )
        landManager
            .playerLands(event.player.uniqueId)
            .find {
                it.resetCountdown() < 0 && landManager
                    .landSchematic(event.player.uniqueId, it.level())
                    .cropVectors.mapped().contains(possibleCropVector)
            }
            ?.let { land ->
                event.isCancelled = true
                event.clickedBlock.type = Material.AIR

                val landCrop = land.cropId()!!
                val cropData = cropsConfig.get().getCrop(landCrop).let {
                    if (it == null)
                        return
                    it
                }
                playerManager.player(event.player.uniqueId).hoe().incrementCollectCount()
                farmItemManager.updateFarmToolName(
                    event.player.uniqueId,
                    event.player.itemInHand
                )
                storageManager.deposit(event.player.uniqueId, landCrop, 1)
                land.addXp(cropData.xp)
                event.player.respond("plantio.coletada") {
                    replace(
                        "{plantacao}" to cropData.name,
                        "{xp}" to cropData.xp.toString()
                    )
                }
            }
    }
}