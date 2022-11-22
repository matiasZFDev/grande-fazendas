package com.grandemc.fazendas.config.model.level

interface LevelContainer<T> {
    fun level(level: Int): T
}