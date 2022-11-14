package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.fazendas.init.model.Factories

class FactoryInitializer : Initializer<Factories> {
    override fun init(): Factories {
        return Factories()
    }
}