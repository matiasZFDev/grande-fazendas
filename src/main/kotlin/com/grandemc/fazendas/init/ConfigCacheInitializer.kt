package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.cache.config.chunk.*
import com.grandemc.post.external.lib.global.allPropertiesAs
import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.view.MenuContainerManager
import com.grandemc.post.external.lib.util.CustomConfig
import com.grandemc.fazendas.init.model.ConfigCache
import com.grandemc.fazendas.init.model.ConfigCacheUpdater

class ConfigCacheInitializer(
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

    private fun chunks(): ConfigCache.Chunks {
        return ConfigCache.Chunks(
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
            MenusChunkImpl(menuContainerManager)
        )
    }
}