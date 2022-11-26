package com.grandemc.fazendas.bukkit.command.gfazendas

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.post.external.lib.command.base.CommandModule
import com.grandemc.post.external.lib.global.bukkit.giveItem
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class GiveLootBox(private val farmItemManager: FarmItemManager) : CommandModule {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 4) {
            sender.respond("darlootbox.argumentos_invalidos")
            return
        }

        val (_, playerName, lootBoxRawId, rawAmount) = args
        val player = Bukkit.getPlayer(playerName).also {
            if (it == null) {
                sender.respond("geral.jogador_offline")
                return
            }
        }
        val lootBoxId = lootBoxRawId.toByteOrNull().let {
            if (it == null) {
                sender.respond("darlootbox.lootbox_invalido")
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
        val lootBoxItem = farmItemManager.createLootBox(lootBoxId).let {
            if (it == null) {
                sender.respond("darlootbox.lootbox_invalido")
                return
            }
            it.amount = amount
            it
        }
        player.giveItem(lootBoxItem)
        sender.respond("darlootbox.enviado") {
            replace(
                "{quantia}" to rawAmount,
                "{lootbox}" to lootBoxItem.itemMeta.displayName,
                "{jogador}" to playerName
            )
        }
        player.respond("darlootbox.recebido") {
            replace(
                "{quantia}" to rawAmount,
                "{lootbox}" to lootBoxItem.itemMeta.displayName
            )
        }
    }
}