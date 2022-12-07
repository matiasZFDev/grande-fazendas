package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.bukkit.view.FertilizingView
import com.grandemc.fazendas.bukkit.view.fertilizing.FertilizingContext
import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.global.bukkit.isRightClick
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.toByte
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class FertilizingUsageListener(
    private val fertilizingConfig: FertilizingConfig,
    private val islandManager: IslandManager
) : Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (!event.isRightClick() || !event.hasItem())
            return

        event.item.useNBTValueIfPresent<NBTTagByte>(
            NBTReference.ITEM, "gfazendas.fertilizing"
        ) { id ->
            event.isCancelled = true

            val playerId = event.player.uniqueId
            if (!islandManager.hasIsland(playerId)) {
                event.player.respond("fazenda.sem_ilha")
                return
            }

            if (!islandManager.insideIsland(playerId)) {
                event.player.respond("fertilizante.dentro_ilha")
                return
            }

            val fertilizingId = id.toByte()
            fertilizingConfig.get().getById(fertilizingId).let {
                if (it == null) {
                    event.player.respond("aplicar_fertilizante.invalido")
                    return
                }
            }

            event.player.openView(FertilizingView::class, FertilizingContext(fertilizingId))
        }
    }
}