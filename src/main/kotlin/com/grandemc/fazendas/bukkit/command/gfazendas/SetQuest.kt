package com.grandemc.fazendas.bukkit.command.gfazendas

import com.grandemc.fazendas.bukkit.view.QuestsView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.global.updateView
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.post.external.lib.command.base.CommandModule
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class SetQuest(
    private val questManager: QuestManager,
    private val playerManager: PlayerManager
) : CommandModule {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 3) {
            sender.respond("setar_missao.argumentos_invalidos")
            return
        }

        val (_, playerName, rawId) = args
        val player = Bukkit.getPlayer(playerName).also {
            if (it == null) {
                sender.respond("geral.jogador_offline")
                return
            }
        }
        if (playerManager.player(player.uniqueId).farm() == null) {
            sender.respond("")
            return
        }
        val questId = rawId.toShortOrNull().let {
            if (it == null || !questManager.hasQuest(it)) {
                sender.respond("setar_missao.missao_invalida")
                return
            }
            it
        }
        val questConfig = questManager.questConfig(questId)
        val quest = FarmQuest(questId, questConfig.type())
        questManager.master(player.uniqueId).startQuest(quest)
        player.updateView<QuestsView>()
        sender.respond("setar_missao.setada")
    }
}