package com.grandemc.fazendas.bukkit.view.fertilizing

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.ApplyType
import com.grandemc.post.external.lib.global.applyPercentage
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.global.intFormat
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player

class FertilizingProcessor(
    private val fertilizingConfig: FertilizingConfig,
    private val farmsConfig: FarmsConfig,
    private val landManager: LandManager,
    private val itemsConfig: ItemsChunk,
    private val cropsConfig: CropsConfig
) : MenuItemsProcessor<FertilizingContext> {
    override fun process(player: Player, data: FertilizingContext?, items: MenuItems): Collection<SlotItem> {
        requireNotNull(data)
        val baseItems = items.values()
        val landItems = farmsConfig.get().farms
            .sortedBy { it.config.id }
            .map(FarmsConfig.Farm::config)
            .zip(fertilizingConfig.get().menuSlots())
            .map { (farm, slot) ->
                val item = if (!landManager.hasLand(player.uniqueId, farm.id))
                    itemsConfig.value("fertilizante_plantio_bloqueado")
                else {
                    val land = landManager.land(player.uniqueId, farm.id)

                    if (land.resetCountdown() < 0 || land.cropId() == null)
                        itemsConfig.value("fertilizante_plantio_nao_aplicavel")
                            .formatName("{id}" to farm.id.toString())
                    else if (!land.canBoost())
                        itemsConfig
                            .value("fertilizante_plantio_aplicado")
                            .formatName("{id}" to farm.id.toString())
                    else {
                        val fertilizingBoost = fertilizingConfig.get().getById(
                            data.fertilizingId
                        )!!.boost
                        val cropResetTime = cropsConfig.get().getCrop(
                            land.cropId()!!
                        )!!.reset
                        val reducedTime = cropResetTime.toDouble()
                            .applyPercentage(fertilizingBoost, ApplyType.DECREMENT)
                            .let { cropResetTime - it.toInt() }
                        val boostedCountdown = (land.resetCountdown() - reducedTime).let {
                            if (it < 0) 0 else it
                        }
                        itemsConfig
                            .value("fertilizante_plantio_aplicavel")
                            .formatName("{id}" to farm.id.toString())
                            .formatLore(
                                "{atual}" to land.resetCountdown().timeFormat(),
                                "{depois}" to boostedCountdown.timeFormat(),
                                "{reducao}" to fertilizingBoost.intFormat()
                            )
                            .addNBTValue(
                                NBTReference.VIEW,
                                "gfazendas.fertilizing.land",
                                NBTTagByte(farm.id)
                            )
                    }
                }

                SlotItem(slot, item)
            }
        return baseItems + landItems
    }
}