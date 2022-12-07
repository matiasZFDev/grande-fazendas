package com.grandemc.fazendas.placeholder

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.post.external.lib.global.dottedFormat
import com.grandemc.post.external.lib.global.toFormat
import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class FarmExpansion(
    private val playerManager: PlayerManager,
    private val landManager: LandManager,
    private val goldBank: GoldBank,
    private val islandConfig: IslandConfig,
    private val farmsConfig: FarmsConfig,
    private val cropsConfig: CropsConfig
) : PlaceholderExpansion() {
    override fun getAuthor(): String {
        return "oPosty"
    }

    override fun getVersion(): String {
        return "1.0"
    }

    override fun getIdentifier(): String {
        return "gfazendas"
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String? {
        if (player == null)
            return null

        if (
            !playerManager.hasPlayer(player.uniqueId) ||
            playerManager.player(player.uniqueId).farm() == null
        )
            return null

        if (params == "ouro") {
            return goldBank.gold(player.uniqueId).toFormat()
        }

        if (params == "nivel") {
            return playerManager.player(player.uniqueId).farm()!!.level().toString()
        }

        if (params == "nivel_maximo") {
            return islandConfig.get().evolution.maxLevel.toString()
        }

        if (params == "xp") {
            return playerManager.player(player.uniqueId).farm()!!.xp().dottedFormat()
        }

        if (params == "xp_evolucao") {
            val farmLevel = playerManager.player(
                player.uniqueId
            ).farm()!!.level().toInt()
            return if (farmLevel == islandConfig.get().evolution.maxLevel)
                islandConfig.get().evolution.xpMaxValue
            else
                islandConfig.get().evolution.levels.level(
                    farmLevel
                ).requirements.xp.dottedFormat()
        }

        farmsConfig.get().farms.forEach {
            if (params == "plantio_${it.config.id}_crop") {
                return if (!landManager.hasLand(player.uniqueId, it.config.id))
                    islandConfig.get().landFormats.blocked
                else {
                    val land = landManager.land(player.uniqueId, it.config.id)

                    if (land.isResetting() && land.cropId() != null)
                        cropsConfig.get().getCrop(
                            land.cropId()!!
                        ).name
                    else
                        islandConfig.get().landFormats.inactive
                }
            }

            if (params == "plantio_${it.config.id}_xp")
                return if (!landManager.hasLand(player.uniqueId, it.config.id))
                    "0"
                else
                    landManager.land(player.uniqueId, it.config.id).xp().dottedFormat()
        }

        return null
    }
}