package com.grandemc.fazendas.bukkit.command.fazenda

import com.grandemc.fazendas.bukkit.view.IslandMainMenuView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.IslandGenerationManager
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import com.grandemc.post.external.lib.command.base.PlayerCommandModule
import org.bukkit.entity.Player

class Island(
    private val islandManager: IslandManager,
    private val islandGenerationManager: IslandGenerationManager
) : PlayerCommandModule(GlobalMessagesProvider.get()) {
    override fun execute(player: Player, args: Array<String>) {
        if (islandGenerationManager.isGeneratingIsland(player.uniqueId)) {
            player.respond("ilha.gerando_ilha_fila")
            return
        }

        if (!islandManager.hasIsland(player.uniqueId)) {
            islandGenerationManager.newIsland(player.uniqueId)
            return
        }

        player.openView(IslandMainMenuView::class)
    }
}