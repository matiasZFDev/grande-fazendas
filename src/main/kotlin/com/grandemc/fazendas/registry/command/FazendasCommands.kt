package com.grandemc.fazendas.registry.command

import com.grandemc.fazendas.bukkit.command.fazenda.Booster
import com.grandemc.fazendas.bukkit.command.fazenda.Island
import com.grandemc.fazendas.bukkit.command.fazenda.Market
import com.grandemc.fazendas.bukkit.command.fazenda.Upgrades
import com.grandemc.post.external.lib.command.base.CommandCompound
import com.grandemc.post.external.lib.factory.Factory
import com.grandemc.post.external.lib.global.command.buildCommand
import com.grandemc.fazendas.bukkit.command.fazendaTabCompleter
import com.grandemc.fazendas.bukkit.command.fazendaHelp
import com.grandemc.fazendas.manager.IslandGenerationManager
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import org.bukkit.plugin.java.JavaPlugin

class FazendasCommands(
    private val plugin: JavaPlugin,
    private val islandManager: IslandManager,
    private val islandGenerationManager: IslandGenerationManager,
    private val playerManager: PlayerManager
) : Factory<CommandCompound> {
    override fun create(): CommandCompound {
        return buildCommand(plugin, GlobalMessagesProvider.get()) {
            commandName("fazendas")
            tabCompleter(fazendaTabCompleter)

            handler {
                noSuchCommandKey("fazendas")
                emptyArgsModule(Island(islandManager, islandGenerationManager))

                module("ajuda", fazendaHelp)
                module("mercado", Market())
                module("booster", Booster(playerManager))
                module("melhorias", Upgrades())
            }
        }
    }
}