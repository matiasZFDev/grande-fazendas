package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.config.model.quest.resolve.QuestResolver
import com.grandemc.fazendas.config.model.quest.resolve.QuestRewardResolver
import com.grandemc.fazendas.config.model.quest.type.*
import com.grandemc.fazendas.global.getRange
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.fazendas.manager.StatsManager
import com.grandemc.fazendas.util.Range
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.getShort
import com.grandemc.post.external.lib.global.bukkit.mappedSection
import com.grandemc.post.external.lib.global.bukkit.section
import com.grandemc.post.external.lib.util.CustomConfig
import com.grandemc.post.external.lib.util.state.MutableState
import com.grandemc.post.external.util.ProgressFormat
import com.grandemc.post.external.util.ProgressFormatFetcher
import com.grandemc.post.external.util.reward.base.config.chunk.RewardsChunk
import org.bukkit.configuration.file.FileConfiguration

class QuestsConfig(
    customConfig: CustomConfig,
    private val farmItemManager: MutableState<FarmItemManager>,
    private val statsManager: MutableState<StatsManager>,
    private val cropsConfig: CropsConfig,
    private val materialsConfig: MaterialsConfig,
    private val fertilizingConfig: FertilizingConfig,
    private val lootBoxConfig: LootBoxConfig,
    private val rewardsConfig: RewardsChunk<Boolean?>
) : StateConfig<QuestsConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        private val xp: Int,
        private val dailyQuestsReset: Int,
        private val dailyQuests: List<DailyQuest>,
        private val questHistory: QuestHistory
    ) {
        private val quests: Map<Short, Quest> = dailyQuests.associate {
            it.id to it.quest
        } + questHistory.quests().associate { it.id to it.quest }

        fun xp(): Int = xp

        fun dailyQuestsReset(): Int = dailyQuestsReset
        fun dailyQuests(): List<DailyQuest> {
            return dailyQuests
        }
        fun getQuest(id: Short): Quest {
            return quests[id] ?: throw Error(
                "Missão #$id não existe."
            )
        }

        fun history(): QuestsConfig.QuestHistory {
            return questHistory
        }
    }
    inner class DailyQuest(
        val id: Short,
        val islandLevelRange: Range,
        val chance: Double,
        val quest: Quest
    )
    inner class QuestHistory(private val quests: List<HistoryQuest>) {
        fun getQuest(position: Short): HistoryQuest {
            return quests[position.toInt()]
        }
        fun quests(): List<HistoryQuest> = quests
    }
    inner class HistoryQuest(
        val id: Short,
        val position: Short,
        val islandLevel: Short,
        val quest: Quest
    )
    inner class RewardsFormat(
        val xp: String,
        val fertilizing: String,
        val lootBox: String,
        val booster: String,
        val item: String,
        val progressFormat: ProgressFormat
    )

    override fun fetch(config: FileConfiguration): QuestsConfig.Config {
        val formats = config.section("formatos").run {
            RewardsFormat(
                getString("xp"),
                getString("fertilizante"),
                getString("lootbox"),
                getString("booster"),
                getString("iten"),
                ProgressFormatFetcher.fromSection(section("progresso"))
            )
        }
        val questRewardResolver = QuestRewardResolver(
            farmItemManager, fertilizingConfig, lootBoxConfig, rewardsConfig, formats,
            statsManager
        )
        val questResolver = QuestResolver(
            questRewardResolver, formats, cropsConfig, materialsConfig
        )
        val dailyQuests = config.section("diarias").mappedSection {
            DailyQuest(
                getShort("id"),
                getRange("nivel_ilha"),
                getDouble("chance"),
                questResolver.resolve(this)
            )
        }
        val questHistory = QuestHistory(
            config.section("historia").mappedSection {
                HistoryQuest(
                    getShort("id"),
                    getShort("posicao"),
                    getShort("nivel_ilha"),
                    questResolver.resolve(this)
                )
            }
        )
        return Config(
            config.getInt("xp"),
            config.section("diarias_reset").run {
                (getInt("hora") * 3600) + (getInt("minuto") * 60)
            },
            dailyQuests,
            questHistory
        )
    }
}