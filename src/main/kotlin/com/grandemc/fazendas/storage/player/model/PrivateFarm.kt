package com.grandemc.fazendas.storage.player.model

import com.grandemc.fazendas.util.Cuboid

class PrivateFarm(
    private val location: Cuboid,
    private var level: Byte,
    private val lands: MutableList<FarmLand>,
    private val questMaster: QuestMaster,
    private val industry: FarmIndustry
) {
    fun location(): Cuboid = location
    fun level(): Byte = level
    fun levelUp() {
        level++
    }
    fun lands(): List<FarmLand> = lands
    fun hasLand(typeId: Byte): Boolean {
        return lands.any { it.typeId() == typeId }
    }
    fun addLand(land: FarmLand) {
        lands.add(land)
    }
    fun land(typeId: Byte): FarmLand? {
        return lands.find { typeId == it.typeId() }
    }
    fun questMaster(): QuestMaster = questMaster
    fun industry(): FarmIndustry = industry
}