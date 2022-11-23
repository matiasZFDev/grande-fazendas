package com.grandemc.fazendas.init

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.config.LandsConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.global.createIfNotExists
import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.cache.config.chunk.*
import com.grandemc.post.external.lib.global.allPropertiesAs
import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.view.MenuContainerManager
import com.grandemc.post.external.lib.util.CustomConfig
import com.grandemc.fazendas.init.model.ConfigCache
import com.grandemc.fazendas.init.model.ConfigCacheUpdater
import com.grandemc.fazendas.util.lazyValue
import org.bukkit.plugin.Plugin
import java.io.File

class ConfigCacheInitializer(
    private val plugin: Plugin,
    private val configManager: ConfigManager,
    private val menuContainerManager: MenuContainerManager,
    private val context: String
) : Initializer<ConfigCache> {
    override fun init(): ConfigCache {
        val chunks = chunks()
        val chunkList = chunks.allPropertiesAs<Updatable>()
        return ConfigCache(
            ConfigCacheUpdater(chunkList),
            chunks
        )
    }

    private fun config(configName: String): CustomConfig {
        return configManager.getWrapper(configName)
    }

    private fun chunks(): ConfigCache.Configs {
        val islandConfig = IslandConfig(
            config("farm/ilha"),
            lazyValue {
                val file = File(plugin.dataFolder, "farm/base.schematic")
                if (!file.exists()) {
                    file.createIfNotExists()
                    plugin.saveResource("farm/base.schematic", true)
                }
                file
            }
        )
        islandConfig.update()
        return ConfigCache.Configs(
            MessagesChunkImpl(
                configManager.getWrapper("resposta/mensagens"),
                "resposta/mensagens.yml",
                context
            ),
            SoundsChunkImpl(
                configManager.getWrapper("resposta/sons"),
                "resposta/sons.yml",
                context
            ),
            EffectsChunkImpl(
                configManager.getWrapper("resposta/efeitos"),
                "resposta/efeitos.yml",
                context
            ),
            MenusChunkImpl(menuContainerManager),
            islandConfig,
            FarmsConfig(
                plugin, "farm", islandConfig.get().worldName,
                islandConfig.get().cropBlock
            ),
            LandsConfig(config("plantios")),
            MaterialsConfig(config("materiais")),
            BaseItemsChunk(config("itens"), "itens.yml", context)
        )
    }
}