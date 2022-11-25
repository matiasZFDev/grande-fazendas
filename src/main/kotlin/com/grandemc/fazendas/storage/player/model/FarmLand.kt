package com.grandemc.fazendas.storage.player.model

import com.grandemc.fazendas.util.cuboid.Cuboid
import com.grandemc.post.external.lib.global.ApplyType
import com.grandemc.post.external.lib.global.applyPercentage

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
    fun setXp(xp: Int) {
        this.xp = xp
    }
    fun level(): Byte = level
    fun levelUp() {
        level++
    }
    fun resetCountdown(): Int = resetCountdown
    fun isResetting(): Boolean = resetCountdown != 1
    fun setCountdown(countdown: Int) {
        resetCountdown = countdown
        canBoost = true
    }
    fun canBoost(): Boolean = canBoost
    fun setCanBoost(canBoost: Boolean) {
        this.canBoost = canBoost
    }
    fun boostId(): Byte? = boostId
    fun boost(id: Byte, percentage: Double) {
        if (!canBoost)
            return

        boostId = id
        resetCountdown = resetCountdown.toDouble().applyPercentage(
            percentage, ApplyType.DECREMENT
        ).toInt()
    }
}