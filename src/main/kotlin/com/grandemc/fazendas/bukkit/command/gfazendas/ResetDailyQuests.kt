package com.grandemc.fazendas.bukkit.command.gfazendas

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.post.external.lib.command.base.CommandModule
import org.bukkit.command.CommandSender

class ResetDailyQuests(
    private val playerManager: PlayerManager
) : CommandModule {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 1) {
            sender.respond("resetar_diarias.argumentos_invalidas")
            return
        }

        playerManager.allPlayers().forEach {
            if (it.farm() == null)
                return@forEach

            it.farm()!!.questMaster().resetDailyQuests()
        }
        sender.respond("resetar_diarias.resetadas")
    }
}