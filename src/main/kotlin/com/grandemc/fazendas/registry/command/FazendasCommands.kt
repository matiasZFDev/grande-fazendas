package com.grandemc.fazendas.registry.command

import com.grandemc.post.external.lib.command.base.CommandCompound
import com.grandemc.post.external.lib.factory.Factory
import com.grandemc.post.external.lib.global.command.buildCommand
import com.grandemc.fazendas.bukkit.command.fazendaTabCompleter
import com.grandemc.fazendas.bukkit.command.fazendaHelp
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import org.bukkit.plugin.java.JavaPlugin

class FazendasCommands(private val plugin: JavaPlugin) : Factory<CommandCompound> {
    override fun create(): CommandCompound {
        return buildCommand(plugin, GlobalMessagesProvider.get()) {
            commandName("fazenda")
            tabCompleter(fazendaTabCompleter)

            handler {
                noSuchCommandKey("fazenda")

                module("ajuda", fazendaHelp)
            }
        }
    }
}