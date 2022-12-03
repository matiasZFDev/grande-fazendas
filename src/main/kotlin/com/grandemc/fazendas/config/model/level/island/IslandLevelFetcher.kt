package com.grandemc.fazendas.config.model.level.island

import com.grandemc.fazendas.config.model.level.LevelContainer
import com.grandemc.fazendas.config.model.level.base.ConfigLevelFetcher
import com.grandemc.fazendas.config.model.level.base.LevelRequirements
import com.grandemc.fazendas.config.model.level.base.LevelUpgrades
import com.grandemc.fazendas.config.model.level.base.pattern.LevelPattern
import com.grandemc.post.external.lib.global.bukkit.formatNumber
import org.bukkit.configuration.ConfigurationSection

class IslandLevelFetcher(
    section: ConfigurationSection
) : ConfigLevelFetcher<IslandLevel, IslandRequirements, IslandUpgrades>(section) {
    override fun fetchRequirement(
        pattern: LevelPattern,
        section: ConfigurationSection
    ): LevelRequirements<IslandRequirements> {
        return IslandLevelRequirements(
            pattern,
            IslandRequirements(section.formatNumber("xp").toInt())
        )
    }

    override fun fetchUpgrade(
        pattern: LevelPattern,
        section: ConfigurationSection
    ): LevelUpgrades<IslandUpgrades> {
        return IslandLevelUpgrades(
            pattern,
            IslandUpgrades(section.getInt("missoes_diarias"))
        )
    }

    override fun buildLevels(
        requirements: List<LevelRequirements<IslandRequirements>>,
        upgrades: List<LevelUpgrades<IslandUpgrades>>
    ): LevelContainer<IslandLevel> {
        return IslandLevels(requirements, upgrades)
    }
}