package com.grandemc.fazendas.bukkit.view.craft.start

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class CraftStartMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(
    customConfig, context, section
) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.craft.start"
            addReference("iniciar_possivel", key, "start")
            addReference("voltar", key, "return")
            colorAll()
        }
    }
}