package com.grandemc.fazendas

import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.database.DatabaseManager
import com.grandemc.fazendas.init.*
import com.grandemc.fazendas.init.model.*
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.fazendas.provider.*
import com.grandemc.fazendas.registry.*
import com.grandemc.post.external.lib.util.state.LateMutableState
import org.bukkit.plugin.java.JavaPlugin

class PluginPostLoad(
    private val plugin: JavaPlugin,
    private val servicesData: ServicesData,
    private val configManager: ConfigManager,
    private val databaseManager: DatabaseManager,
    private val context: String
) : Runnable {
    private lateinit var apis: PluginAPIs
    private lateinit var configCache: ConfigCache
    private lateinit var guiManagers: GuiManagers
    private lateinit var pluginManagers: PluginManagers
    private lateinit var factories: Factories
    private lateinit var states: PluginStates

    override fun run() {
        val farmItemManagerState = LateMutableState<FarmItemManager>()
        initAPIs()
        guiManagers = GuiManagersInitializer().init()
        configCache = ConfigCacheInitializer(
            plugin, configManager, guiManagers.menuContainerManager, context,
            farmItemManagerState
        ).init()
        initProviders()
        initPluginManagers()
        farmItemManagerState.change(pluginManagers.farmItemManager)
        initFactories()
        initStates()
        registerViews()
        configCache.updater.update()
        registerExecutors()
        registerListeners()
        registerCommands()
    }

    private fun initAPIs() {
        apis = ApisInitializer(plugin).init()
    }

    private fun initProviders() {
        configCache.configs.run {
            GlobalMessagesProvider.provide(messages)
            GlobalSoundsProvider.provide(sounds)
            GlobalEffectsProvider.provide(effects)
        }
        GlobalMenuContainerProvider.provide(guiManagers.menuContainerManager)
        GlobalViewProvider.provide(guiManagers.viewManager)
    }

    private fun initPluginManagers() {
        pluginManagers = PluginManagersInitializer(
            servicesData.playerService,
            servicesData.marketService,
            plugin,
            configCache.configs,
        ).init()
    }

    private fun initFactories() {
        factories = FactoryInitializer().init()
    }

    private fun initStates() {
        states = PluginStateInitializer().init()
    }

    private fun registerViews() {
        MenuContainerRegistry(
            guiManagers.menuContainerManager, configManager, context
        ).registerAll()
        configCache.configs.menus.update()
        ViewRegistry(
            guiManagers.viewManager,
            pluginManagers,
            configCache.configs,
            apis,
            states
        ).registerAll()
    }

    private fun registerExecutors() {
        ExecutorRegistry(
            plugin,
            configCache.configs.crops,
            pluginManagers.playerManager,
            pluginManagers.landPlantManager,
            pluginManagers.marketManager,
            pluginManagers.storageManager,
            configCache.configs.industry,
            pluginManagers.islandManager,
            pluginManagers.statsManager,
            pluginManagers.farmManager,
            states.islandTopState,
            pluginManagers.taskManager
        ).startAll()
    }

    private fun registerListeners() {
        ListenerRegistry(plugin, configCache.configs, pluginManagers).registerAll()
    }

    private fun registerCommands() {
        CommandRegistry(
            plugin, configManager, configCache.updater, databaseManager,
            pluginManagers.islandManager, pluginManagers.islandGenerationManager,
            pluginManagers.farmItemManager, pluginManagers.playerManager,
            pluginManagers.landManager
        ).registerAll()
    }
}