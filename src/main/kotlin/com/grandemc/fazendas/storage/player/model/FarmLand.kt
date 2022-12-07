package com.grandemc.fazendas.storage.player.model

class FarmLand(
    private val typeId: Byte,
    private var cropId: Byte?,
    private var level: Byte,
    private var xp: Int,
    private var resetCountdown: Int,
    private var boostId: Byte?,
    private var canBoost: Boolean
) {
    fun typeId(): Byte = typeId
    fun cropId(): Byte? = cropId
    fun setCrop(cropId: Byte?) {
        this.cropId = cropId
    }
    fun xp(): Int = xp
    fun addXp(xp: Int) {
        this.xp += xp
    }
    fun level(): Byte = level
    fun levelUp() {
        level++
    }
    fun resetCountdown(): Int = resetCountdown
    fun reduceCountdown() {
        resetCountdown--
    }
    fun isResetting(): Boolean = resetCountdown >= 0
    fun setCountdown(countdown: Int) {
        resetCountdown = countdown
        canBoost = true
    }
    fun canBoost(): Boolean = canBoost
    fun resetCanBoost() {
        canBoost = true
        boostId = null
    }
    fun boostId(): Byte? = boostId
    fun boost(id: Byte, reduction: Int) {
        if (!canBoost)
            return

        boostId = id
        resetCountdown -= reduction
        canBoost = false
    }

    fun levelSet(level: Byte) {
        this.level = level
    }
}