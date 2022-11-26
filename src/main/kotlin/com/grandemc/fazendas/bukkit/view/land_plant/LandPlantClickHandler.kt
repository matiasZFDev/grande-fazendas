package com.grandemc.fazendas.bukkit.view.land_plant

import com.grandemc.fazendas.bukkit.view.land.LandContext
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.findWorld
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.global.toLocation
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.toByte
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class LandPlantClickHandler(
    private val cropsConfig: CropsConfig,
    private val islandManager: IslandManager,
    private val landManager: LandManager,
    private val islandConfig: IslandConfig
) : ViewClickHandler<LandContext> {
    override fun onClick(player: Player, data: LandContext?, item: ItemStack, event: InventoryClickEvent) {
        requireNotNull(data)
        item.useNBTValueIfPresent<NBTTagByte>(NBTReference.VIEW, "gfazendas.plant.crop") {
            val cropId = it.toByte()
            val cropData = cropsConfig.get().getCrop(cropId).let { crop ->
                if (crop == null) {
                    player.respond("geral.error")
                    player.closeInventory()
                    return
                }
                crop
            }
            val world = islandConfig.get().worldName.findWorld()
            val origin = islandManager.islandOrigin(
                player.uniqueId, false
            ).toLocation(world)
            val cropsArea = landManager.landSchematic(player.uniqueId, data.landId).cropVectors
            val process = cropData.process.type.initializeProcess(cropsArea)
            process.start(origin, cropData.process.startBlocks)
            player.closeInventory()
            player.respond("plantio.plantado") {
                replace(
                    "{plantacao}" to cropData.name
                )
            }
        }
    }
}