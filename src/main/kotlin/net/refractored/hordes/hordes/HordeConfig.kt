package net.refractored.hordes.hordes

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.EntityType

data class HordeConfig(
    val configSection: ConfigurationSection,
) {
    val worlds: List<World>

    val mobs: List<EntityType>

    val maxMobs
        get() = configSection.getInt("HordeMaxSize")

    val minMobs
        get() = configSection.getInt("HordeMinSize")

    init {
        worlds = configSection.getStringList("worlds").mapNotNull { Bukkit.getWorld(it) }

        if (worlds.isEmpty()) {
            throw IllegalArgumentException("No valid worlds found in ${configSection.name}")
        }

        mobs = configSection.getStringList("mobs").mapNotNull { EntityType.valueOf(it) }
    }
}
