package com.grandemc.fazendas.registry.command

import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.command.base.CommandCompound
import com.grandemc.post.external.lib.factory.Factory
import com.grandemc.post.external.lib.global.command.buildCommand
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.database.DatabaseManager
import com.grandemc.fazendas.bukkit.command.*
import com.grandemc.fazendas.bukkit.command.gfazendas.GiveFertilizing
import com.grandemc.fazendas.bukkit.command.gfazendas.GiveLootBox
import com.grandemc.fazendas.bukkit.command.gfazendas.Reload
import com.grandemc.fazendas.bukkit.command.gfazendas.Save
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import org.bukkit.plugin.java.JavaPlugin

class GFazendasCommands(
    private val plugin: JavaPlugin,
    private val configManager: ConfigManager,
    private val configCacheUpdater: Updatable,
    private val databaseManager: DatabaseManager,
    private val farmItemManager: FarmItemManager
) : Factory<CommandCompound> {
    override fun create(): CommandCompound {
        return buildCommand(plugin, GlobalMessagesProvider.get()) {
            commandName("gfazendas")
            tabCompleter(gfazendasTabCompleter)

            handler {
                noSuchCommandKey("gfazendas")
                bypass(true)

                module("reload", Reload(configManager, configCacheUpdater))
                module("save", Save(databaseManager))
                module("ajuda", gfazendasHelp)
                module("darfertilizante", GiveFertilizing(farmItemManager))
                module("darlootbox", GiveLootBox(farmItemManager))
            }
        }
    }
}