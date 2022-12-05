package com.grandemc.fazendas.bukkit.command.xfazendas

import com.grandemc.fazendas.manager.LandManager
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import com.grandemc.post.external.lib.command.base.PlayerCommandModule
import org.bukkit.entity.Player

class ChangeLand(
    private val landManager: LandManager
) : PlayerCommandModule(GlobalMessagesProvider.get()) {
    override fun execute(player: Player, args: Array<String>) {
        val landId = args[1].toByte()
        val landLevel = args[2].toByte()
        landManager.land(player.uniqueId, landId).levelSet(landLevel)
        landManager.buildLand(player.uniqueId, landId, landLevel)
    }
}