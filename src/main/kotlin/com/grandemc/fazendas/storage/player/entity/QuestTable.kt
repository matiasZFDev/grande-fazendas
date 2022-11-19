package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.global.getUUID
import com.grandemc.fazendas.global.setUUID
import com.grandemc.fazendas.storage.player.model.Quest
import com.grandemc.fazendas.storage.player.model.QuestMaster
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.database.base.FixedTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class QuestTable(
    tableName: String
) : FixedTable<FarmPlayer, UUID, QuestMaster>(tableName) {
    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("player_id", "BINARY(16) NOT NULL")
            addColumn("current_id", "TINYINT NOT NULL", true)
            addColumn("progress", "INT NOT NULL", true)
            addColumn("done", "BIT NOT NULL", true)
            addColumn("daily_quests_done", "TINYINT NOT NULL", true)
            addColumn("quests_done", "varbinary", true)
            primaryKey("player_id")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: FarmPlayer): Boolean {
        val questMaster = value.farm().questMaster()
        statement.setUUID(1, value.id())
        statement.setByte(2, questMaster.current().id())
        statement.setInt(3, questMaster.current().progress())
        statement.setBoolean(4, questMaster.current().isDone())
        statement.setByte(5, questMaster.dailyQuestsDone())
        statement.setBytes(6, questMaster.questsDone().toByteArray())
        statement.setByte(7, questMaster.current().id())
        statement.setInt(8, questMaster.current().progress())
        statement.setBoolean(9, questMaster.current().isDone())
        statement.setByte(10, questMaster.dailyQuestsDone())
        statement.setBytes(11, questMaster.questsDone().toByteArray())
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, QuestMaster>, resultSet: ResultSet) {
        data[resultSet.getUUID("player_id")] = QuestMaster(
            Quest(
                resultSet.getByte("current_id"),
                QuestType.CROP_COLLECT, // MODIFY
                resultSet.getInt("progress"),
                resultSet.getBoolean("done")
            ),
            resultSet.getByte("daily_quests_done"),
            resultSet.getBytes("quests_done").toMutableList()
        )
    }
}