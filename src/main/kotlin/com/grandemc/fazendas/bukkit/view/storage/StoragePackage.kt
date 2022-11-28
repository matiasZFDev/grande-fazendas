package com.grandemc.fazendas.bukkit.view.storage

import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.config.StorageConfig
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class StoragePackage(
    storageManager: StorageManager,
    storageConfig: StorageConfig,
    materialsConfig: MaterialsConfig,
    itemsConfig: ItemsChunk,
) : StatelessPackage(
    StorageMenuContainer::class,
    StorageProcessor(storageManager, storageConfig, materialsConfig, itemsConfig),
    StorageClickHandler()
)