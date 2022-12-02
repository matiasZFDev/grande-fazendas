package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.config.model.quest.resolve.QuestResolver
import com.grandemc.fazendas.config.model.quest.resolve.QuestRewardResolver
import com.grandemc.fazendas.config.model.quest.type.*
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.getByte
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
    private val cropsConfig: CropsConfig,
    private val materialsConfig: MaterialsConfig,
    private val fertilizingConfig: FertilizingConfig,
    private val lootBoxConfig: LootBoxConfig,
    private val rewardsConfig: RewardsChunk<Boolean?>
) : StateConfig<QuestsConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        private val dailyQuestsLimit: Byte,
        private val dailyQuestsReset: Int,
        private val dailyQuests: List<DailyQuest>,
        private val questHistory: QuestHistory
    ) {
        private val quests: Map<Byte, Quest> = dailyQuests.associate {
            it.id to it.quest
        } + questHistory.quests().associate { it.id to it.quest }

        fun dailyQuestsLimit(): Byte = dailyQuestsLimit
        fun dailyQuestsReset(): Int = dailyQuestsReset
        fun dailyQuests(): List<DailyQuest> {
            return dailyQuests
        }
        fun getDailyQuest(id: Byte): DailyQuest {
            return dailyQuests.first { it.id == id }
        }
        fun getHistoryQuest(position: Byte): HistoryQuest {
            return questHistory.getQuest(position)
        }
        fun getQuest(id: Byte): Quest {
            return quests[id] ?: throw Error(
                "Missão #$id não existe."
            )
        }

        fun history(): QuestsConfig.QuestHistory {
            return questHistory
        }
    }
    inner class DailyQuest(
        val id: Byte,
        val islandLevel: Byte,
        val chance: Double,
        val quest: Quest
    )
    inner class QuestHistory(private val quests: List<HistoryQuest>) {
        fun getQuest(position: Byte): HistoryQuest {
            return quests[position.toInt()]
        }
        fun quests(): List<HistoryQuest> = quests
    }
    inner class HistoryQuest(
        val id: Byte,
        val position: Byte,
        val islandLevel: Byte,
        val quest: Quest
    )
    inner class RewardsFormat(
        val xp: String,
        val fertilizing: String,
        val lootBox: String,
        val booster: String,
        val item: String,
        val anyCrop: String,
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
                getString("plantar_qualquer"),
                ProgressFormatFetcher.fromSection(section("progresso"))
            )
        }
        val questRewardResolver = QuestRewardResolver(
            farmItemManager, fertilizingConfig, lootBoxConfig, rewardsConfig, formats
        )
        val questResolver = QuestResolver(
            questRewardResolver, formats, cropsConfig, materialsConfig
        )
        val dailyQuests = config.section("diarias").mappedSection {
            DailyQuest(
                getByte("id"),
                getByte("nivel_ilha"),
                getDouble("chance"),
                questResolver.resolve(this)
            )
        }
        val questHistory = QuestHistory(
            config.section("historia").mappedSection {
                HistoryQuest(
                    getByte("id"),
                    getByte("posicao"),
                    getByte("nivel_ilha"),
                    questResolver.resolve(this)
                )
            }
        )
        return Config(
            config.getByte("missoes_diarias"),
            config.section("diarias_reset").run {
                (getInt("hora") * 3600) + (getInt("minuto") * 60)
            },
            dailyQuests,
            questHistory
        )
    }
}