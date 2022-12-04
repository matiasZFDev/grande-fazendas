package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.storage.player.model.FarmBooster
import com.grandemc.post.external.lib.global.bukkit.isRightClick
import com.grandemc.post.external.lib.global.bukkit.nms.*
import com.grandemc.post.external.lib.global.bukkit.reduceItemInHand
import net.minecraft.server.v1_8_R3.NBTTagFloat
import net.minecraft.server.v1_8_R3.NBTTagShort
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class BoosterConsumeListener(
    private val playerManager: PlayerManager
) : Listener {
    @EventHandler
    fun onConsume(event: PlayerInteractEvent) {
        if (!event.hasItem() || !event.isRightClick())
            return

        if (!event.item.hasReferenceTag(NBTReference.ITEM, "gfazendas.xp_booster.multiplier"))
            return

        val playerId = event.player.uniqueId

        if (playerManager.player(playerId).booster() != null) {
            event.player.respond("booster.ativo")
            return
        }

        val boost = event.item
            .getNBTValue<NBTTagFloat>(NBTReference.ITEM, "gfazendas.xp_booster.multiplier")
            .toFloat()
        val duration = event.item
            .getNBTValue<NBTTagShort>(NBTReference.ITEM, "gfazendas.xp_booster.duration")
            .toShort()
        event.player.reduceItemInHand()
        playerManager.player(playerId).setBooster(FarmBooster(boost, duration))
        event.player.respond("booster.ativado")
    }
}