package com.grandemc.fazendas.storage.player

import com.grandemc.fazendas.storage.player.entity.*
import com.grandemc.fazendas.storage.player.model.FarmIndustry
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.fazendas.storage.player.model.ItemStorage
import com.grandemc.fazendas.storage.player.model.PrivateFarm
import com.grandemc.post.external.lib.database.base.TableManager
import java.sql.Connection

class FarmPlayerTableManager : TableManager<FarmPlayer> {
    private val playerTable = PlayerTable("grandefazendas_player")
    private val hoeTable = HoeTable("grandefazendas_hoe")
    private val itemStorageTable = ItemStorageTable("grandefazendas_item_storage")
    private val farmTable = FarmTable("grandefazendas_farm")
    private val farmLandTable = FarmLandTable("grandefazendas_farm_land")
    private val questTable = QuestTable("grandefazendas_quest")
    private val industryTable = IndustryTable("grandefazendas_industry")

    override fun insertAll(connection: Connection, values: Collection<FarmPlayer>) {
        val storageItems = values.map { player ->
            player.storage().items().map {
                ItemStorageTable.StorageInput(player.id(), it)
            }
        }.flatten()
        val farmLands = values.filter { it.farm() != null }.map { player ->
            player.farm()!!.lands().map {
                FarmLandTable.LandInput(player.id(), it)
            }
        }.flatten()
        playerTable.insertAll(connection, values)
        hoeTable.insertAll(connection, values)
        itemStorageTable.insertAll(connection, storageItems)
        farmTable.insertAll(connection, values)
        farmLandTable.insertAll(connection, farmLands)
        questTable.insertAll(connection, values)
        industryTable.insertAll(connection, values)
    }

    override fun fetchAll(connection: Connection): Collection<FarmPlayer> {
        playerTable.createTable(connection)
        hoeTable.createTable(connection)
        itemStorageTable.createTable(connection)
        farmTable.createTable(connection)
        farmLandTable.createTable(connection)
        questTable.createTable(connection)
        industryTable.createTable(connection)
        val playersData = playerTable.fetchAll(connection)
        val farmHoes = hoeTable.fetchAll(connection)
        val itemStorages = itemStorageTable.fetchAll(connection)
        val farms = farmTable.fetchAll(connection)
        val farmLands = farmLandTable.fetchAll(connection)
        val quests = questTable.fetchAll(connection)
        val industries = industryTable.fetchAll(connection)
        return playersData.map { (playerId, playerData) ->
            FarmPlayer(
                playerId,
                farms[playerId]?.run {
                    PrivateFarm(
                        id, location, level, xp, farmLands[playerId] ?: mutableListOf(),
                        quests[playerId]!!, industries[playerId] ?: FarmIndustry()
                    )
                },
                itemStorages[playerId] ?: ItemStorage(),
                farmHoes[playerId]!!,
                playerData.gold
            )
        }
    }
}