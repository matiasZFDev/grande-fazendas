package com.grandemc.fazendas.bukkit.view.upgrades

import com.grandemc.fazendas.storage.player.model.FarmUpgradeType
import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class UpgradesMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.upgrades"
            addReference("voltar", key, "return")
            FarmUpgradeType.values.forEach {
                addReference(it.configName(), key)
            }
            colorAll()
        }
    }
}