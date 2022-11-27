package com.grandemc.fazendas.config.model.level.base

import com.grandemc.fazendas.config.model.level.LevelContainer

interface LevelFetcher<T> {
    fun fetch(): LevelContainer<T>
}