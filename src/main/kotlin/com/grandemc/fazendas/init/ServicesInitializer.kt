package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.fazendas.init.model.ServicesData

class ServicesInitializer : Initializer<ServicesData> {
    override fun init(): ServicesData {
        return ServicesData()
    }
}