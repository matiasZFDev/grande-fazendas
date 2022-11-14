package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.fazendas.init.model.PluginStates

class PluginStateInitializer : Initializer<PluginStates> {
    override fun init(): PluginStates {
        return PluginStates()
    }
}