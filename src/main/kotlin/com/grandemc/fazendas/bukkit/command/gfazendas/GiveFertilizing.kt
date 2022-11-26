package com.grandemc.fazendas.bukkit.command.gfazendas

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.post.external.lib.command.base.CommandModule
import com.grandemc.post.external.lib.global.bukkit.giveItem
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class GiveFertilizing(private val farmItemManager: FarmItemManager) : CommandModule {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 4) {
            sender.respond("darfertilizante.argumentos_invalidos")
            return
        }

        val (_, playerName, fertilizingRawId, rawAmount) = args
        val player = Bukkit.getPlayer(playerName).also {
            if (it == null) {
                sender.respond("geral.jogador_offline")
                return
            }
        }
        val fertilizingId = fertilizingRawId.toByteOrNull().let {
            if (it == null) {
                sender.respond("darfertilizante.fertilizante_invalido")
                return
            }
            it
        }
        val amount = rawAmount.toIntOrNull().let {
            if (it == null) {
                sender.respond("geral.quantia_invalida")
                return
            }
            it
        }
        val fertilizingItem = farmItemManager.createFertilizing(fertilizingId).let {
            if (it == null) {
                sender.respond("darfertilizante.fertilizante_invalido")
                return
            }
            it.amount = amount
            it
        }
        player.giveItem(fertilizingItem)
        sender.respond("darfertilizante.enviado") {
            replace(
                "{quantia}" to rawAmount,
                "{fertilizante}" to fertilizingItem.itemMeta.displayName,
                "{jogador}" to playerName
            )
        }
        player.respond("darfertilizante.recebido") {
            replace(
                "{quantia}" to rawAmount,
                "{fertilizante}" to fertilizingItem.itemMeta.displayName
            )
        }
    }
}