package net.refractored.hordes.commands

import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.bloodmoonreloaded.exceptions.CommandErrorException
import net.refractored.bloodmoonreloaded.util.MessageUtil.miniToComponent
import net.refractored.hordes.HordesExtension
import net.refractored.hordes.hordes.HordeRegistry
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class SpawnHordeCommand {
    @CommandPermission("bloodmoon.admin.horde.spawn")
    @Description("Spawns a horde on a player.")
    @Command("bloodmoon spawn horde")
    fun execute(
        actor: BukkitCommandActor,
        player: Player,
        @Optional announce: Boolean = true,
    ) {
        HordesExtension.instance.logger.info("Spawning horde on ${player.name}.")
        HordeRegistry.getHordeConfig(player.world)?.spawnHorde(player, announce) ?: throw CommandErrorException(
            BloodmoonPlugin.instance.langYml
                .getString("messages.NoHordeConfigFound")
                .miniToComponent(),
        )
        actor.reply(
            BloodmoonPlugin.instance.langYml
                .getString("messages.SpawnedHordeOnPlayer")
                .replace("%player%", player.name),
        )
        HordesExtension.instance.logger.info("Spawned horde on ${player.name}.")
    }
}
