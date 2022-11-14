package com.grandemc.fazendas.bukkit.command.gfazendas

import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.command.base.CommandModule
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.fazendas.global.respond
import org.bukkit.command.CommandSender

class Reload(
    private val configManager: ConfigManager,
    private val configCacheUpdater: Updatable
) : CommandModule {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.size != 1) {
            sender.respond("reload.argumentos_invalidos")
            return
        }

        configManager.reloadAll()
        configCacheUpdater.update()
        sender.respond("reload.sucesso")
    }
}