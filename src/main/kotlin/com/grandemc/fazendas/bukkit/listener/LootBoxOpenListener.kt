package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.config.LootBoxConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.post.external.lib.global.bukkit.giveItem
import com.grandemc.post.external.lib.global.bukkit.isRightClick
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.toByte
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import com.grandemc.post.external.lib.global.bukkit.reduceItemInHand
import com.grandemc.post.external.lib.global.intFormat
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.util.random.RandomUtils
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class LootBoxOpenListener(
    private val lootBoxConfig: LootBoxConfig,
    private val farmItemManager: FarmItemManager
) : Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (!event.isRightClick() || !event.hasItem())
            return

        event.item.useNBTValueIfPresent<NBTTagByte>(
            NBTReference.ITEM, "gfazendas.lootbox"
        ) { id ->
            event.isCancelled = true

            val lootBoxId = id.toByte()

            val lootBox = lootBoxConfig.get().getLootBox(lootBoxId).let {
                if (it == null) {
                    event.player.respond("abrir_lootbox.invalido")
                    return
                }
                it
            }

            if (!event.player.isSneaking) {
                event.player.reduceItemInHand()
                lootBox.content
                    .filter { RandomUtils.roll(it.chance) }
                    .also {
                        if (it.isEmpty()) {
                            event.player.respond("abrir_lootbox.nada")
                            return
                        }
                    }
                    .forEach {
                        val booster = it.booster
                        val boosterItem = farmItemManager.createBooster(booster)
                        event.player.giveItem(boosterItem)
                        event.player.respond("abrir_lootbox.recompensa") {
                            replace(
                                "{multiplicador}" to booster.boost.intFormat(),
                                "{duracao}" to booster.duration.timeFormat()
                            )
                        }
                    }
            }

            else {
                val times = event.player.itemInHand.amount
                event.player.itemInHand = null
                val lootBoxes = (0 until times)
                    .map { _ ->
                        lootBox.content.filter { RandomUtils.roll(it.chance) }
                    }
                    .flatten()
                    .also {
                        if (it.isEmpty()) {
                            event.player.respond("abrir_lootbox.nada")
                            return
                        }
                    }
                val lastBox = lootBoxes.last()
                lootBoxes.forEach {
                    val boosterItem = farmItemManager.createBooster(it.booster)
                    event.player.giveItem(boosterItem)
                }
                event.player.respond("abrir_lootbox.recompensa") {
                    replace(
                        "{multiplicador}" to lastBox.booster.boost.intFormat(),
                        "{duracao}" to lastBox.booster.duration.timeFormat()
                    )
                }
            }
        }
    }
}