package net.refractored.hordes.listeners

import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.bloodmoonreloaded.events.BloodmoonStartEvent
import net.refractored.bloodmoonreloaded.registry.BloodmoonRegistry
import net.refractored.bloodmoonreloaded.types.BloodmoonWorld
import net.refractored.hordes.hordes.HordeConfig
import net.refractored.hordes.hordes.HordeRegistry
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnBloodmoonStart : Listener {
    @EventHandler
    fun onBloodmoonStart(event: BloodmoonStartEvent) {
        val hordeConfig = HordeRegistry.getHordeConfig(event.World) ?: return
        scheduleBloodmoonTask(event, hordeConfig)
    }

    private fun scheduleBloodmoonTask(
        event: BloodmoonStartEvent,
        hordeConfig: HordeConfig,
    ) {
        BloodmoonPlugin.instance.scheduler.runLater(
            (hordeConfig.minTickTime..hordeConfig.maxTickTime).random(),
        ) {
            bloodmoonTask(event, hordeConfig)
        }
    }

    private fun bloodmoonTask(
        event: BloodmoonStartEvent,
        hordeConfig: HordeConfig,
    ) {
        val bloodmoonWorld = BloodmoonRegistry.getWorld(event.World.name) ?: return

        if (bloodmoonWorld.status != BloodmoonWorld.BloodmoonStatus.ACTIVE) {
            return
        }

        val player = event.World.getEligiblePlayers().randomOrNull()

        if (player != null) {
            hordeConfig.spawnHorde(player, true)
        }

        scheduleBloodmoonTask(event, hordeConfig)
    }

    private fun World.getEligiblePlayers(): List<Player> =
        this.players.filter {
            it.gameMode == GameMode.SURVIVAL && !it.isVanished()
        }

    private fun Player.isVanished(): Boolean {
        for (meta in this.getMetadata("vanished")) {
            if (meta.asBoolean()) return true
        }
        return false
    }
}
