package com.grandemc.fazendas.config.model.crop.process

import com.grandemc.fazendas.config.model.block.SimpleBlocks
import com.grandemc.fazendas.global.VectorArea
import com.sk89q.worldedit.blocks.BaseBlock
import org.bukkit.Material

class FarmCropProcess(cropVectorArea: VectorArea) : SoilCropProcess(
    cropVectorArea,
    SimpleBlocks(BaseBlock(Material.SOIL.id, 7))
)