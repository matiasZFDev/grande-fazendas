package com.grandemc.fazendas.init.model

import com.grandemc.post.external.lib.cache.config.Updatable

class ConfigCacheUpdater(private val updatables: List<Updatable>) : Updatable {
    override fun update() = updatables.forEach(Updatable::update)
}