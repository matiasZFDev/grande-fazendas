package com.grandemc.fazendas.bukkit.command.gfazendas

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.post.external.lib.command.base.CommandModule
import com.grandemc.post.external.lib.global.doubleFromFormat
import com.grandemc.post.external.lib.global.isValidNumber
import com.grandemc.post.external.lib.global.toFormat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class GiveGold(private val goldBank: GoldBank) : CommandModule {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 3) {
            sender.respond("darouro.argumentos_invalidos")
            return
        }

        val (_, playerName, rawAmount) = args
        val player = Bukkit.getPlayer(playerName).also {
            if (it == null) {
                sender.respond("geral.jogador_offline")
                return
            }
        }
        val amount = rawAmount.let {
            if (!it.isValidNumber()) {
                sender.respond("geral.quantia_invalida")
                return
            }
            it.doubleFromFormat()
        }
        goldBank.deposit(player.uniqueId, amount)
        sender.respond("darouro.enviado") {
            replace(
                "{xp}" to amount.toFormat(),
                "{jogador}" to playerName
            )
        }
        player.respond("darouro.recebido") {
            replace(
                "{xp}" to amount.toFormat()
            )
        }
    }
}