package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.global.getUUID
import com.grandemc.fazendas.global.setUUID
import com.grandemc.fazendas.storage.player.model.FarmIndustry
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.fazendas.storage.player.model.FarmRecipe
import com.grandemc.post.external.lib.database.base.FixedTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class IndustryTable(
    tableName: String
) : FixedTable<FarmPlayer, UUID, FarmIndustry>(tableName) {
    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("player_id", "BINARY(16) NOT NULL")
            addColumn("current_recipe_id", "TINYINT NOT NULL", true)
            addColumn("current_time_left", "INT NOT NULL", true)
            primaryKey("player_id")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: FarmPlayer): Boolean {
        if (value.farm() == null)
            return false

        value.farm()!!.industry().currentRecipe().let {
            if (it == null)
                return false
            statement.setUUID(1, value.id())
            statement.setByte(2, it.id())
            statement.setInt(3, it.timeLeft())
            statement.setByte(4, it.id())
            statement.setInt(5, it.timeLeft())
        }
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, FarmIndustry>, resultSet: ResultSet) {
        data[resultSet.getUUID("player_id")] = FarmIndustry(
            FarmRecipe(
                resultSet.getByte("current_recipe_id"),
                resultSet.getInt("current_time_left")
            )
        )
    }
}