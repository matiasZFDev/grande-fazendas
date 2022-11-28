package com.grandemc.fazendas.bukkit.view.craft.select

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class CraftSelectMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(
    customConfig, context, section
) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            addReference("voltar", "gfazendas.craft.select", "return")
            colorAll()
        }
    }
}