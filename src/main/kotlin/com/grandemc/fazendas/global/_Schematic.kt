package com.grandemc.fazendas.global

import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files

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

fun String.findWorld(): World {
    return Bukkit.getWorld(this)
}