package com.grandemc.fazendas.registry

import com.grandemc.fazendas.manager.*
import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.command.base.CommandCompound
import com.grandemc.post.external.lib.factory.Factory
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.database.DatabaseManager
import com.grandemc.fazendas.registry.command.FazendasCommands
import com.grandemc.fazendas.registry.command.GFazendasCommands
import com.grandemc.fazendas.registry.command.XFazendasCommands
import org.bukkit.plugin.java.JavaPlugin

class CommandRegistry(
    private val plugin: JavaPlugin,
    private val configManager: ConfigManager,
    private val configCacheUpdater: Updatable,
    private val databaseManager: DatabaseManager,
    private val islandManager: IslandManager,
    private val islandGenerationManager: IslandGenerationManager,
    private val farmItemManager: FarmItemManager,
    private val playerManager: PlayerManager,
    private val landManager: LandManager,
    private val goldBank: GoldBank,
    private val questManager: QuestManager
) {
    fun registerAll() {
        factories()
            .map(Factory<CommandCompound>::create)
            .forEach(CommandCompound::register)
    }

    private fun factories(): List<Factory<CommandCompound>> {
        return listOf(fazendas(), gfazendas(), xfazendas())
    }

    private fun fazendas(): Factory<CommandCompound> = FazendasCommands(
        plugin, islandManager, islandGenerationManager, playerManager
    )

    private fun gfazendas(): Factory<CommandCompound> = GFazendasCommands(
        plugin, configManager, configCacheUpdater, databaseManager, farmItemManager,
        playerManager, goldBank, questManager
    )

    private fun xfazendas(): Factory<CommandCompound> = XFazendasCommands(
        plugin, landManager
    )
}