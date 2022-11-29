package com.grandemc.fazendas.config.model

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.getShort
import com.grandemc.post.external.lib.global.bukkit.intList
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration

class MarketConfig(customConfig: CustomConfig) : StateConfig<MarketConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        val categoriesMenuSlots: List<Int>,
        val productsMenuSlots: List<Int>,
        val expiryTime: Int,
        val productLimit: Short,
        val tax: Double
    )

    override fun fetch(config: FileConfiguration): MarketConfig.Config {
        return config.run {
            Config(
                intList("categorias_menu_slots"),
                intList("produtos_menu_slots"),
                getInt("tempo_expiracao"),
                getShort("limite_itens_a_venda"),
                getDouble("taxa")
            )
        }
    }
}