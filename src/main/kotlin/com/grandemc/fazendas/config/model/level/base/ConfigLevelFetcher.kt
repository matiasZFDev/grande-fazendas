package com.grandemc.fazendas.config.model.level.base

import com.grandemc.fazendas.config.model.level.LevelContainer
import com.grandemc.fazendas.config.model.level.base.pattern.EveryAfterPattern
import com.grandemc.fazendas.config.model.level.base.pattern.EveryPattern
import com.grandemc.fazendas.config.model.level.base.pattern.LevelPattern
import com.grandemc.fazendas.config.model.level.base.pattern.WhenPattern
import com.grandemc.post.external.lib.global.bukkit.keys
import com.grandemc.post.external.lib.global.bukkit.mappedKeys
import com.grandemc.post.external.lib.global.bukkit.section
import org.bukkit.configuration.ConfigurationSection

abstract class ConfigLevelFetcher<T, R, U>(
    private val section: ConfigurationSection
) : LevelFetcher<T> {
    abstract fun fetchRequirement(
        pattern: LevelPattern, section: ConfigurationSection
    ): LevelRequirements<R>
    abstract fun fetchUpgrade(
        pattern: LevelPattern, section: ConfigurationSection
    ): LevelUpgrades<U>
    abstract fun buildLevels(
        requirements: List<LevelRequirements<R>>, upgrades: List<LevelUpgrades<U>>
    ): LevelContainer<T>

    override fun fetch(): LevelContainer<T> {
        val requirements = mutableListOf<LevelRequirements<R>>()
        val upgrades = mutableListOf<LevelUpgrades<U>>()
        section.section("requisitos").mappedKeys().forEach { (key, section) ->
            patternResolve(key) {
                requirements.add(fetchRequirement(it, section))
            }
        }
        section.section("melhorias").mappedKeys().forEach { (key, section) ->
            patternResolve(key) {
                upgrades.add(fetchUpgrade(it, section))
            }
        }
        return buildLevels(requirements, upgrades)
    }

    private fun patternResolve(key: String, consumer: (LevelPattern) -> Unit) {
        if (key.startsWith("cada")) {
            val level = key.split("_")[1].toInt()
            consumer(EveryPattern(level))
        }

        else if (key.startsWith("em")) {
            val level = key.split("_")[1].toInt()
            consumer(WhenPattern(level))
        }

        else if (key.startsWith("depois")) {
            val patternFormat = key.split("_")
            val afterLevel = patternFormat[1].toInt()
            val everyLevel = patternFormat[3].toInt()
            consumer(EveryAfterPattern(afterLevel, everyLevel))
        }
    }
}