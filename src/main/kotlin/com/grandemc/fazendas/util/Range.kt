package com.grandemc.fazendas.util

class Range(
    private val from: Int,
    private val to: Int
) {
    fun from(): Int = from
    fun to(): Int = to
    fun isInside(value: Int): Boolean {
        return value in from..to
    }
}