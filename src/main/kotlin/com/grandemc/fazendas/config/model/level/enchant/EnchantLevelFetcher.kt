package com.grandemc.fazendas.config.model.level.enchant

import com.grandemc.fazendas.config.model.level.LevelContainer
import com.grandemc.fazendas.config.model.level.base.ConfigLevelFetcher
import com.grandemc.fazendas.config.model.level.base.LevelRequirements
import com.grandemc.fazendas.config.model.level.base.LevelUpgrades
import com.grandemc.fazendas.config.model.level.base.pattern.LevelPattern
import com.grandemc.post.external.lib.global.bukkit.formatNumber
import org.bukkit.configuration.ConfigurationSection

class EnchantLevelFetcher(
    section: ConfigurationSection
) : ConfigLevelFetcher<EnchantLevel, Double, Double>(section) {
    override fun fetchRequirement(
        pattern: LevelPattern, section: ConfigurationSection
    ): LevelRequirements<Double> {
        return EnchantLevelRequirements(pattern, section.formatNumber("ouro"))
    }

    override fun fetchUpgrade(
        pattern: LevelPattern, section: ConfigurationSection
    ): LevelUpgrades<Double> {
        return EnchantLevelUpgrades(pattern, section.getDouble("valor"))
    }

    override fun buildLevels(
        requirements: List<LevelRequirements<Double>>,
        upgrades: List<LevelUpgrades<Double>>
    ): LevelContainer<EnchantLevel> {
        return EnchantLevels(requirements, upgrades)
    }
}