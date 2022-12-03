package com.grandemc.fazendas.bukkit.view.master

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class MasterMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.master"
            addReference("missoes", key, "quests")
            addReference("mercado", key, "market")
            addReference("ilha", key, "island")
            addReference("top", key)
            colorAll()
        }
    }
}