package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.fazendas.init.model.PluginManagers

class PluginManagersInitializer() : Initializer<PluginManagers> {
    override fun init(): PluginManagers {
        return PluginManagers()
    }
}