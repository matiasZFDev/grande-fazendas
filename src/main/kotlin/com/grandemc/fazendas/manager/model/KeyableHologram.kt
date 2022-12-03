package com.grandemc.fazendas.manager.model

import com.grandemc.fazendas.global.Hologram

class KeyableHologram(
    private val key: Byte,
    private val hologram: Hologram
) : Keyable<Byte, Hologram> {
    override fun key(): Byte {
        return key
    }

    override fun value(): Hologram {
        return hologram
    }
}