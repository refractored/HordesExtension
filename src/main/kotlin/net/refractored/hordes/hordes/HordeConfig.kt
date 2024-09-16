package net.refractored.hordes.hordes

import com.willfp.eco.core.entities.Entities
import com.willfp.eco.core.entities.TestableEntity
import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.bloodmoonreloaded.util.MessageUtil.getStringPrefixed
import net.refractored.bloodmoonreloaded.util.MessageUtil.miniToComponent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player

data class HordeConfig(
    val configSection: ConfigurationSection,
) {
    val worlds: List<World> = configSection.getStringList("worlds").mapNotNull { Bukkit.getWorld(it) }

    val mobs: List<TestableEntity>

    val maxMobs
        get() = configSection.getInt("HordeMaxSize")

    val minMobs
        get() = configSection.getInt("HordeMinSize")

    val minTickTime
        get() = configSection.getLong("HordeSpawnRateTicksMin")

    val maxTickTime
        get() = configSection.getLong("HordeSpawnRateTicksMax")

    val spawnDistance
        get() = configSection.getInt("HordeSpawnDistance")

    val hordeBroadcastPrefixed
        get() = configSection.getBoolean("HordeSpawnedOnPlayerPrefixed")

    init {

        if (worlds.isEmpty()) {
            throw IllegalArgumentException("No valid worlds found in ${configSection.name}")
        }

        mobs = configSection.getStringList("mobs").mapNotNull { Entities.lookup(it) }
    }

    /**
     * Spawn a horde at a player's location
     */
    fun spawnHorde(
        player: Player,
        announce: Boolean = true,
    ) {
        val targetLocation: Location = player.location.clone()

        val spawnAmount = (minMobs..maxMobs).random()

        for (i in 0 until spawnAmount) {
            val mob = mobs.random()

            var mobLocation: Location = targetLocation.clone()

            targetLocation.x += (-(spawnDistance)..spawnDistance).random()

            targetLocation.z += (-(spawnDistance)..spawnDistance).random()

            mobLocation = player.world.getHighestBlockAt(mobLocation).location

            mob.spawn(mobLocation)

            player.world.strikeLightningEffect(mobLocation)
        }

        if (!announce) return

        if (hordeBroadcastPrefixed) {
            Bukkit.broadcast(
                BloodmoonPlugin.instance.langYml
                    .getStringPrefixed("messages.HordeSpawnedOnPlayer")
                    .replace("%player%", player.name)
                    .miniToComponent(),
            )
        } else {
            Bukkit.broadcast(
                BloodmoonPlugin.instance.langYml
                    .getString("messages.HordeSpawnedOnPlayer")
                    .replace("%player%", player.name)
                    .miniToComponent(),
            )
        }
    }
}
