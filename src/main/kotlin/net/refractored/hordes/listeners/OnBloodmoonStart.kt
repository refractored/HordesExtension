package net.refractored.hordes.listeners

import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.bloodmoonreloaded.events.BloodmoonStartEvent
import net.refractored.bloodmoonreloaded.types.BloodmoonWorld
import net.refractored.hordes.hordes.HordeConfig
import net.refractored.hordes.hordes.HordeRegistry
import net.refractored.hordes.util.EligibleUtil.getEligiblePlayers
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
        if (event.BloodmoonWorld.status != BloodmoonWorld.Status.ACTIVE) {
            return
        }

        val player = event.World.getEligiblePlayers().randomOrNull()

        if (player != null) {
            hordeConfig.spawnHorde(player, true)
        }

        scheduleBloodmoonTask(event, hordeConfig)
    }
}
