package com.grandemc.fazendas.init.model

import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.cache.config.chunk.base.*

data class ConfigCache(
    val updater: Updatable,
    val chunks: Chunks
) {
    data class Chunks(
        val messages: MessagesChunk,
        val sounds: SoundsChunk,
        val effects: EffectsChunk,
        val menus: MenusChunk
    )
}