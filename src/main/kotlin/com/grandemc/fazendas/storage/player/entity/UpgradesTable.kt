package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.global.getUUID
import com.grandemc.fazendas.global.setUUID
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.fazendas.storage.player.model.FarmUpgrade
import com.grandemc.fazendas.storage.player.model.FarmUpgradeType
import com.grandemc.fazendas.storage.player.model.FarmUpgrades
import com.grandemc.post.external.lib.database.base.FixedTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class UpgradesTable(
    tableName: String
) : FixedTable<FarmPlayer, UUID, FarmUpgrades>(tableName) {
    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("player_id", "BINARY(16) NOT NULL")
            addColumn("xp_boost", "TINYINT NOT NULL", true)
            addColumn("daily_quests", "TINYINT NOT NULL", true)
            addColumn("craft_time_reduction", "TINYINT NOT NULL", true)
            addColumn("local_sell_bonus", "TINYINT NOT NULL", true)
            primaryKey("player_id")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: FarmPlayer): Boolean {
        statement.setUUID(1, value.id())
        statement.setByte(2, value.upgrades().level(FarmUpgradeType.XP_BOOST))
        statement.setByte(3, value.upgrades().level(FarmUpgradeType.DAILY_QUESTS))
        statement.setByte(4, value.upgrades().level(FarmUpgradeType.CRAFT_TIME_REDUCTION))
        statement.setByte(5, value.upgrades().level(FarmUpgradeType.LOCAL_SELL_BONUS))
        statement.setByte(6, value.upgrades().level(FarmUpgradeType.XP_BOOST))
        statement.setByte(7, value.upgrades().level(FarmUpgradeType.DAILY_QUESTS))
        statement.setByte(8, value.upgrades().level(FarmUpgradeType.CRAFT_TIME_REDUCTION))
        statement.setByte(9, value.upgrades().level(FarmUpgradeType.LOCAL_SELL_BONUS))
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, FarmUpgrades>, resultSet: ResultSet) {
        data[resultSet.getUUID("player_id")] = FarmUpgrades(
            FarmUpgradeType.values.map {
                FarmUpgrade(it, resultSet.getByte(it.databaseName()))
            }
        )
    }
}