package com.grandemc.fazendas.storage.player.model

import com.grandemc.post.external.lib.database.base.Identifiable
import java.util.UUID

class FarmPlayer(
    private val playerId: UUID,
    private var farm: PrivateFarm?,
    private val storage: ItemStorage,
    private val hoe: FarmHoe,
    private var gold: Double,
    private var booster: FarmBooster?,
    private val upgrades: FarmUpgrades
) : Identifiable<UUID> {
    override fun id(): UUID = playerId
    fun farm(): PrivateFarm? = farm
    fun setFarm(farm: PrivateFarm) {
        this.farm = farm
    }
    fun storage(): ItemStorage = storage
    fun hoe(): FarmHoe = hoe
    fun gold(): Double = gold
    fun depositGold(gold: Double) {
        this.gold += gold
    }
    fun withdrawGold(gold: Double) {
        this.gold =- gold
    }
    fun booster(): FarmBooster? = booster
    fun setBooster(booster: FarmBooster?) {
        this.booster = booster
    }
    fun upgrades(): FarmUpgrades = upgrades
}