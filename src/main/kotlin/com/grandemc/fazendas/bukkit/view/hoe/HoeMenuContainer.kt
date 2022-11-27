package com.grandemc.fazendas.bukkit.view.hoe

import com.grandemc.fazendas.storage.player.model.CustomEnchant
import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class HoeMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            CustomEnchant.values().forEach {
                addReference(it.configName(), "gfazendas.hoe.enchant")
                colorAll()
            }
        }
    }
}