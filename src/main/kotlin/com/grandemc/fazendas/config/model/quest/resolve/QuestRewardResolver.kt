package com.grandemc.fazendas.config.model.quest.resolve

import com.grandemc.fazendas.config.*
import com.grandemc.fazendas.config.model.quest.reward.*
import com.grandemc.fazendas.global.getFloat
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.post.external.lib.global.bukkit.getByte
import com.grandemc.post.external.lib.global.bukkit.getShort
import com.grandemc.post.external.lib.global.bukkit.mappedSection
import com.grandemc.post.external.lib.util.state.MutableState
import com.grandemc.post.external.util.reward.base.config.chunk.RewardsChunk
import org.bukkit.configuration.ConfigurationSection

class QuestRewardResolver(
    private val farmItemManager: MutableState<FarmItemManager>,
    private val fertilizingConfig: FertilizingConfig,
    private val lootBoxConfig: LootBoxConfig,
    private val rewardsConfig: RewardsChunk<Boolean?>,
    private val formats: QuestsConfig.RewardsFormat
) {
    fun resolve(section: ConfigurationSection): QuestReward {
        if (!section.contains("tipo")) {
            return QuestRewards(section.mappedSection {
                resolveSinglyReward(this)
            })
        }

        return resolveSinglyReward(section)
    }

    private fun resolveSinglyReward(section: ConfigurationSection): QuestReward {
        return when (section.getString("tipo")) {
            "xp" -> XpReward(
                section.getInt("quantia"),
                formats.xp
            )
            "lootbox" -> LootBoxReward(
                lootBoxConfig.get().getLootBox(section.getByte("id"))!!,
                section.getShort("quantia"),
                farmItemManager,
                formats.lootBox
            )
            "booster" -> BoosterReward(
                LootBoxConfig.Booster(
                    section.getFloat("boost"),
                    section.getShort("duracao")
                ),
                section.getShort("amount"),
                farmItemManager,
                formats.booster
            )
            "fertilizante" -> FertilizingReward(
                fertilizingConfig.get().getById(section.getByte("id"))!!,
                section.getShort("quantia"),
                farmItemManager,
                formats.fertilizing
            )
            "iten" -> ItemReward(
                section.getInt("slot"),
                rewardsConfig,
                formats.fertilizing
            )
            else -> throw Error(
                "O tipo ${section.getString("tipo")} não existe."
            )
        }
    }
}