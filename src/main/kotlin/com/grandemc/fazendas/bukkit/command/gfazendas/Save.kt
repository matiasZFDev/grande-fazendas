package com.grandemc.fazendas.bukkit.command.gfazendas

import com.grandemc.post.external.lib.command.base.CommandModule
import com.grandemc.post.external.lib.manager.database.DatabaseManager
import com.grandemc.fazendas.global.respond
import org.bukkit.command.CommandSender

class Save(private val databaseManager: DatabaseManager) : CommandModule {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 1) {
            sender.respond("save.argumentos_invalidos")
            return
        }

        sender.respond("save.iniciando")
        databaseManager.saveData(true)
            .thenRun {
                sender.respond("save.salvos")
            }
    }
}