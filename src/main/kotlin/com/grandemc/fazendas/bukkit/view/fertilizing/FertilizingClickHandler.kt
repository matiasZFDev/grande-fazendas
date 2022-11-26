package com.grandemc.fazendas.bukkit.view.fertilizing

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.global.ApplyType
import com.grandemc.post.external.lib.global.applyPercentage
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.hasReferenceTag
import com.grandemc.post.external.lib.global.bukkit.nms.toByte
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import com.grandemc.post.external.lib.global.bukkit.reduceItemInHand
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class FertilizingClickHandler(
    private val fertilizingConfig: FertilizingConfig,
    private val landManager: LandManager,
    private val cropsConfig: CropsConfig
) : ViewClickHandler<FertilizingContext> {
    override fun onClick(player: Player, data: FertilizingContext?, item: ItemStack, event: InventoryClickEvent) {
        requireNotNull(data)
        item.useNBTValueIfPresent<NBTTagByte>(
            NBTReference.VIEW, "gfazendas.fertilizing.land"
        ) { id ->
            if (!player.itemInHand.hasReferenceTag(NBTReference.ITEM, "gfazendas.fertilizing")) {
                player.closeInventory()
                return
            }

            val fertilizing = fertilizingConfig.get().getById(data.fertilizingId).let {
                if (it == null) {
                    player.respond("geral.error")
                    player.closeInventory()
                    return
                }

                it
            }

            val landId = id.toByte()
            val fertilizingBoost = fertilizing.boost
            val land = landManager.land(player.uniqueId, landId)

            if (land.resetCountdown() < 0) {
                player.respond("aplicar_fertilizante.pronta")
                player.closeInventory()
                return
            }

            val cropResetTime = cropsConfig.get().getCrop(
                land.cropId()!!
            )!!.reset
            val reducedTime = cropResetTime.toDouble()
                .applyPercentage(fertilizingBoost, ApplyType.DECREMENT)
                .let { cropResetTime - it.toInt() }
                .let { if (land.resetCountdown() - it < 0) land.resetCountdown() else it }
            land.boost(data.fertilizingId, reducedTime)
            player.respond("aplicar_fertilizante.aplicado") {
                replace(
                    "{fertilizante}" to fertilizing.name,
                    "{plantio}" to landId.toString()
                )
            }
            player.closeInventory()
            player.reduceItemInHand()
        }
    }
}