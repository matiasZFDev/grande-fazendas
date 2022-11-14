package com.grandemc.fazendas

import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.database.DatabaseManager
import com.grandemc.fazendas.init.*
import com.grandemc.fazendas.init.model.*
import com.grandemc.fazendas.provider.*
import com.grandemc.fazendas.registry.*
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
        initAPIs()
        guiManagers = GuiManagersInitializer().init()
        configCache = ConfigCacheInitializer(
            configManager, guiManagers.menuContainerManager, context
        ).init()
        initProviders()
        initPluginManagers()
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
        configCache.chunks.run {
            GlobalMessagesProvider.provide(messages)
            GlobalSoundsProvider.provide(sounds)
            GlobalEffectsProvider.provide(effects)
        }
        GlobalMenuContainerProvider.provide(guiManagers.menuContainerManager)
        GlobalViewProvider.provide(guiManagers.viewManager)
    }

    private fun initPluginManagers() {
        pluginManagers = PluginManagersInitializer().init()
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
        configCache.chunks.menus.update()
        ViewRegistry(
            guiManagers.viewManager
        ).registerAll()
    }

    private fun registerExecutors() {
        ExecutorRegistry(plugin).startAll()
    }

    private fun registerListeners() {
        ListenerRegistry(plugin).registerAll()
    }

    private fun registerCommands() {
        CommandRegistry(
            plugin, configManager, configCache.updater, databaseManager
        ).registerAll()
    }
}