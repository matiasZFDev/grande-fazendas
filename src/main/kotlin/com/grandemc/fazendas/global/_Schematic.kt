package com.grandemc.fazendas.global

import com.boydti.fawe.`object`.schematic.Schematic
import com.grandemc.fazendas.util.cuboid.Cuboid
import com.grandemc.fazendas.util.cuboid.FixedCuboid
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.CuboidClipboard
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat
import com.sk89q.worldedit.regions.Region
import com.sk89q.worldedit.schematic.SchematicFormat
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.util.LinkedList

fun File.createIfNotExists(): File {
    if (!exists()) {
        Files.createDirectories(parentFile.toPath())
        return Files.createFile(this.toPath()).toFile()
    }
    return this
}

fun File.asSchematic(world: World): Clipboard {
    val weWorld = BukkitWorld(world)
    val format = ClipboardFormat.findByFile(this)!!
    val reader = format.getReader(FileInputStream(this))
    return reader.read(weWorld.worldData)
}

fun File.asSchematic(worldName: String): Clipboard {
    return asSchematic(Bukkit.getWorld(worldName))
}

fun Vector.toLocation(world: World): Location {
    return Location(world, x, y, z)
}

fun Clipboard.toCuboid(origin: Vector, world: World): Cuboid {
    return FixedCuboid(
        origin.add(region.minimumPoint).toLocation(world),
        origin.add(region.maximumPoint).toLocation(world),
    )
}

fun Region.min(): Vector {
    return BlockVector(
        minOf(minimumPoint.x, maximumPoint.x),
        minOf(minimumPoint.y, maximumPoint.y),
        minOf(minimumPoint.z, maximumPoint.z)
    )
}