package com.grandemc.fazendas.bukkit.view.land_plant

import com.grandemc.fazendas.bukkit.view.land.LandContext
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.LandPlantManager
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
    private val landPlantManager: LandPlantManager
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
            landPlantManager.startPlantation(player.uniqueId, data.landId, cropData)
            player.closeInventory()
            player.respond("plantio.plantado") {
                replace(
                    "{plantacao}" to cropData.name
                )
            }
        }
    }
}