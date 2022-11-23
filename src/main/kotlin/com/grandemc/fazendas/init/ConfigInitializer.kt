package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.config.ConfigManagerImpl
import org.bukkit.plugin.Plugin

class ConfigInitializer(private val plugin: Plugin) : Initializer<ConfigManager> {
    override fun init(): ConfigManager {
        return ConfigManagerImpl(plugin, configList())
    }

    private fun configList(): Array<String> {
        return (respondFiles() + rootFiles() + menuFiles()).toTypedArray()
    }

    private fun respondFiles(): List<String> = listOf("mensagens", "sons", "efeitos").map { "resposta/$it" }
    private fun menuFiles(): List<String> = listOf(
        "plantios"
    ).map { "menu/$it" }
    private fun rootFiles(): List<String> = listOf(
        "database", "enxada", "fertilizante", "industria", "itens", "lootbox",
        "materiais", "mercado", "missoes", "plantacoes", "farm/ilha", "itens",
        "plantios"
    )
}