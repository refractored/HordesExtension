package net.refractored.hordes.listeners

import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.bloodmoonreloaded.events.BloodmoonStartEvent
import net.refractored.bloodmoonreloaded.worlds.BloodmoonRegistry
import net.refractored.hordes.hordes.HordeRegistry
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnBloodmoonStart : Listener {
    @EventHandler
    fun onBloodmoonStart(event: BloodmoonStartEvent) {
        val hordeConfig = HordeRegistry.getHordeConfig(event.World)!!
        HordeRegistry.getHordeConfig(event.World)!!.maxMobs
        BloodmoonPlugin.instance.scheduler.runLater(
            (hordeConfig.minTickTime..hordeConfig.maxTickTime).random(),
        ) {
        }
    }

    fun bloodmoonTask(event: BloodmoonStartEvent) {
        val hordeConfig = HordeRegistry.getHordeConfig(event.World)!!
        HordeRegistry.getHordeConfig(event.World)!!.maxMobs
        BloodmoonPlugin.instance.scheduler.runLater(
            (hordeConfig.minTickTime..hordeConfig.maxTickTime).random(),
        ) {
            hordeConfig.spawnHorde(event.World.getEligiblePlayers().random())

            val bloodmoonWorld = BloodmoonRegistry.getWorld(event.World.name) ?: return@runLater

            bloodmoonWorld.active ?: return@runLater

            bloodmoonTask(event)
        }
    }

    /**
     * Gets eligible players.
     *
     * @return the eligible players
     */
    private fun World.getEligiblePlayers(): List<Player> {
        val eligiblePlayers: MutableList<Player> = ArrayList()
        for (player in this.players) {
            if (!player.isVanished() && player.gameMode == GameMode.SURVIVAL) {
                eligiblePlayers.add(player)
            }
        }
        return eligiblePlayers
    }

    private fun Player.isVanished(): Boolean {
        for (meta in this.getMetadata("vanished")) {
            if (meta.asBoolean()) return true
        }
        return false
    }
}
