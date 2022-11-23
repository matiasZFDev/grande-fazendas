package com.grandemc.fazendas.util

class ViewVector(
    private val x: Double,
    private val y: Double,
    private val z: Double,
    private val yaw: Float,
    private val pitch: Float
) {
    fun x(): Double = x
    fun y(): Double = y
    fun z(): Double = z
    fun yaw(): Float = yaw
    fun pitch(): Float = pitch
}