package com.grandemc.fazendas.bukkit.command.fazenda

import com.grandemc.fazendas.bukkit.view.UpgradesView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import com.grandemc.post.external.lib.command.base.PlayerCommandModule
import org.bukkit.entity.Player

class Upgrades : PlayerCommandModule(GlobalMessagesProvider.get()) {
    override fun execute(player: Player, args: Array<String>) {
        if (args.size != 1) {
            player.respond("melhorias.argumentos_invalidos")
            return
        }

        player.openView(UpgradesView::class)
    }
}