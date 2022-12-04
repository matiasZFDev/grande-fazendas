package com.grandemc.fazendas.bukkit.command.fazenda

import com.grandemc.fazendas.global.intFormat
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.global.timeFormat
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import com.grandemc.post.external.lib.command.base.PlayerCommandModule
import org.bukkit.entity.Player

class Booster(
    private val playerManager: PlayerManager
) : PlayerCommandModule(GlobalMessagesProvider.get()) {
    override fun execute(player: Player, args: Array<String>) {
        if (args.size != 1) {
            player.respond("booster.argumentos_invalidos")
            return
        }

        val booster = playerManager.player(player.uniqueId).booster()

        if (booster == null)
            player.respond("booster.status_inativo")
        else
            player.respond("booster.status_ativo") {
                replace(
                    "{boost}" to booster.boost().intFormat(),
                    "{duracao}" to booster.timeLeft().timeFormat()
                )
            }
    }
}