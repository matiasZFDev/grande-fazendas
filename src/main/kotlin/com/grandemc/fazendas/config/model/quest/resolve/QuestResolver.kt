package com.grandemc.fazendas.config.model.quest.resolve

import com.grandemc.fazendas.config.*
import com.grandemc.fazendas.config.model.quest.reward.QuestReward
import com.grandemc.fazendas.config.model.quest.type.*
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.bukkit.getByte
import com.grandemc.post.external.lib.global.bukkit.getShort
import com.grandemc.post.external.lib.global.bukkit.section
import com.grandemc.post.external.lib.global.color
import org.bukkit.configuration.ConfigurationSection

class QuestResolver(
    private val rewardResolver: QuestRewardResolver,
    private val formats: QuestsConfig.RewardsFormat,
    private val cropsConfig: CropsConfig,
    private val materialsConfig: MaterialsConfig
) {
    fun resolve(section: ConfigurationSection): Quest {
        val questType = QuestType.fromConfigName(section.getString("missao.tipo"))
        val resolveReward: () -> QuestReward = {
            rewardResolver.resolve(section.section("recompensas"))
        }
        val section = section.section("missao")

         return when (questType) {
            QuestType.CROP_COLLECT -> {
                val cropConfig = cropsConfig.get().getCropByNameId(
                    section.getString("plantacao")
                )
                CollectQuest(
                    section.getString("nome").color(),
                    resolveReward(),
                    cropConfig.id,
                    section.getInt("quantia"),
                    formats.progressFormat
                )
            }
            QuestType.CRAFT -> {
                val materialConfig = materialsConfig.get().getByNameId(
                    section.getString("material")
                )
                CraftQuest(
                    section.getString("nome").color(),
                    resolveReward(),
                    materialConfig.id,
                    formats.progressFormat
                )
            }
            QuestType.XP_EARN -> XpEarnQuest(
                section.getString("nome").color(),
                resolveReward(),
                section.getInt("xp"),
                formats.progressFormat
            )
            QuestType.PLANT -> {
                val cropId: Byte?
                val cropName: String
                if (section.getString("plantacao").equals("qualquer", true))  {
                    cropId = null
                    cropName = formats.anyCrop
                }

                else {
                    val cropConfig = cropsConfig.get().getCropByNameId(
                        section.getString("plantacao")
                    )
                    cropId = cropConfig.id
                    cropName = cropConfig.name
                }
                    section.getByte("plantacao")
                PlantQuest(
                    section.getString("nome").color(),
                    resolveReward(),
                    cropId,
                    section.getInt("veces"),
                    formats.progressFormat
                )
            }
            QuestType.HAND_OVER -> {
                val materialConfig = materialsConfig.get().getByNameId(
                    section.getString("material")
                )
                HandOverQuest(
                    section.getString("nome").color(),
                    resolveReward(),
                    materialConfig.id,
                    section.getShort("quantia"),
                    formats.progressFormat
                )
            }
            QuestType.MARKET_SELL -> MarketSellQuest(
                section.getString("nome").color(),
                resolveReward(),
                section.getInt("vendas"),
                formats.progressFormat
            )
            QuestType.MARKET_BUY -> MarketBuyQuest(
                section.getString("nome").color(),
                resolveReward(),
                section.getInt("compras"),
                formats.progressFormat
            )
            QuestType.MARKET_POST -> MarketPostQuest(
                section.getString("nome").color(),
                resolveReward(),
                section.getInt("postagens"),
                formats.progressFormat
            )
        }
    }
}