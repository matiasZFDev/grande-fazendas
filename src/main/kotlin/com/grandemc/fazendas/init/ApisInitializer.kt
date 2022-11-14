package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.fazendas.init.model.PluginAPIs
import org.bukkit.conversations.ConversationFactory
import org.bukkit.plugin.Plugin

class ApisInitializer(private val plugin: Plugin) : Initializer<PluginAPIs> {
    override fun init(): PluginAPIs {
        return PluginAPIs(ConversationFactory(plugin))
    }
}