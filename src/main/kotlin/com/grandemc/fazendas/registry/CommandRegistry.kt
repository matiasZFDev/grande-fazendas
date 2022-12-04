package com.grandemc.fazendas.registry

import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.fazendas.manager.IslandGenerationManager
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.command.base.CommandCompound
import com.grandemc.post.external.lib.factory.Factory
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.database.DatabaseManager
import com.grandemc.fazendas.registry.command.FazendasCommands
import com.grandemc.fazendas.registry.command.GFazendasCommands
import org.bukkit.plugin.java.JavaPlugin

class CommandRegistry(
    private val plugin: JavaPlugin,
    private val configManager: ConfigManager,
    private val configCacheUpdater: Updatable,
    private val databaseManager: DatabaseManager,
    private val islandManager: IslandManager,
    private val islandGenerationManager: IslandGenerationManager,
    private val farmItemManager: FarmItemManager,
    private val playerManager: PlayerManager
) {
    fun registerAll() {
        factories()
            .map(Factory<CommandCompound>::create)
            .forEach(CommandCompound::register)
    }

    private fun factories(): List<Factory<CommandCompound>> {
        return listOf(fazendas(), gfazendas())
    }

    private fun fazendas(): Factory<CommandCompound> = FazendasCommands(
        plugin, islandManager, islandGenerationManager, playerManager
    )
    private fun gfazendas(): Factory<CommandCompound> = GFazendasCommands(
        plugin, configManager, configCacheUpdater, databaseManager, farmItemManager
    )
}
