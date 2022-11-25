package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class LandMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.land"
            addReference("fertilizante_inativo", key, "fertilizing")
            addReference("evolucao_upavel", key, "evolve")
            addReference("plantar_disponivel", key, "plant")
            colorAll()
        }
    }
}