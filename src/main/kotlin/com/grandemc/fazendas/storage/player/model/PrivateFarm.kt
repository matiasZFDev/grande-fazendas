package com.grandemc.fazendas.storage.player.model

class PrivateFarm(
    private val id: Int,
    private var level: Short,
    private var xp: Int,
    private val lands: MutableList<FarmLand>,
    private val questMaster: QuestMaster,
    private val industry: FarmIndustry
) {
    fun id(): Int = id
    fun xp(): Int = xp
    fun addXp(xp: Int) {
        this.xp += xp
    }
    fun setXp(xp: Int) {
        this.xp = xp
    }
    fun level(): Short = level
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