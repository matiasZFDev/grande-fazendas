package com.grandemc.fazendas.bukkit.command.gfazendas

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.post.external.lib.command.base.CommandModule
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class FinishQuest(
    private val questManager: QuestManager,
    private val playerManager: PlayerManager
) : CommandModule {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 2) {
            sender.respond("acabar_missao.argumentos_invalidos")
            return
        }
        val (_, playerName) = args
        val player = Bukkit.getPlayer(playerName).also {
            if (it == null) {
                sender.respond("geral.jogador_offline")
                return
            }
        }
        val master = questManager.master(player.uniqueId)
        if (master.current() == null) {
            sender.respond("acabar_missao.sem")
            return
        }
        val farmPlayer = playerManager.player(player.uniqueId)
        val questConfig = questManager.currentConfig(farmPlayer.id())
        master.concludeQuest()
        questConfig.rewards().send(farmPlayer)
        player.closeInventory()
        player.respond("missao.completada")
        sender.respond("acabar_missao.acabada")
    }
}