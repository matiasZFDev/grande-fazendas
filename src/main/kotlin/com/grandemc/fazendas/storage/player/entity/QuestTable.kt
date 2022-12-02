package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.global.getUUID
import com.grandemc.fazendas.global.setUUID
import com.grandemc.fazendas.storage.player.model.FarmQuest
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
            addColumn("quest_type", "TINYINT NOT NULL", true)
            addColumn("progress", "INT NOT NULL", true)
            addColumn("done", "BIT NOT NULL", true)
            addColumn("daily_quests_done", "TINYINT NOT NULL", true)
            addColumn("history_progress", "TINYINT NOT NULL", true)
            addColumn("quests_done", "SMALLINT NOT NULL", true)
            primaryKey("player_id")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: FarmPlayer): Boolean {
        if (value.farm() == null)
            return false

        val questMaster = value.farm()!!.questMaster()
        statement.setUUID(1, value.id())
        statement.setByte(2, questMaster.current()?.id() ?: -1)
        statement.setByte(3, questMaster.current()?.type()?.id() ?: -1)
        statement.setInt(4, questMaster.current()?.progress() ?: -1)
        statement.setBoolean(5, questMaster.current()?.isDone() ?: false)
        statement.setByte(6, questMaster.dailyQuestsDone())
        statement.setByte(7, questMaster.questHistoryProgress())
        statement.setShort(8, questMaster.questsDone())
        statement.setByte(9, questMaster.current()?.id() ?: -1)
        statement.setByte(10, questMaster.current()?.type()?.id() ?: -1)
        statement.setInt(11, questMaster.current()?.progress() ?: -1)
        statement.setBoolean(12, questMaster.current()?.isDone() ?: false)
        statement.setByte(13, questMaster.dailyQuestsDone())
        statement.setByte(14, questMaster.questHistoryProgress())
        statement.setShort(15, questMaster.questsDone())
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, QuestMaster>, resultSet: ResultSet) {
        data[resultSet.getUUID("player_id")] = QuestMaster(
            if (resultSet.getByte("current_id") == (-1).toByte())
                null
            else
                FarmQuest(
                    resultSet.getByte("current_id"),
                    QuestType.fromId(resultSet.getByte("quest_type")),
                    resultSet.getInt("progress"),
                    resultSet.getBoolean("done")
                ),
            resultSet.getByte("daily_quests_done"),
            resultSet.getByte("history_progress"),
            resultSet.getShort("quests_done")
        )
    }
}