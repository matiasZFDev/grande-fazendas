package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.database.base.DatabaseService
import com.grandemc.post.external.lib.global.allPropertiesAs
import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.database.BaseDatabaseManager
import com.grandemc.post.external.lib.manager.database.DatabaseManager
import com.grandemc.fazendas.init.model.ServicesData
import org.bukkit.plugin.Plugin

class DatabaseInitializer(
    private val plugin: Plugin,
    private val configManager: ConfigManager,
    private val servicesData: ServicesData
) : Initializer<DatabaseManager> {
    override fun init(): DatabaseManager {
        val services = servicesData.allPropertiesAs<DatabaseService<*, *>>()
        return BaseDatabaseManager(
            plugin,
            configManager,
            services
        )
    }
}